package nexign.bootcamp.hrs.service;

import lombok.extern.slf4j.Slf4j;
import nexign.bootcamp.brt.model.AbonentTarifficationRes;
import nexign.bootcamp.crm.entity.Tariff;
import nexign.bootcamp.crm.repository.AbonentRepo;
import nexign.bootcamp.hrs.model.Call;
import nexign.bootcamp.hrs.model.CallReport;
import nexign.bootcamp.hrs.model.CallType;
import nexign.bootcamp.hrs.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class HrsService {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private Map<String, Customer> customers = new HashMap<>();

    @Value("${cdr+.path}")
    private String pathToCdrPlus;

    private final AbonentRepo abonentRepo;

    public HrsService(AbonentRepo abonentRepo) {
        this.abonentRepo = abonentRepo;
    }

    private void parseCdrPlus(String line) {
        String[] arg = line.split(", ");
        if(arg.length != 5){
            log.atWarn().log("HRS: строка cdr+ некорректна. Пропуск");
            return;
        }
        Customer customer = customers.get(arg[1]);
        if(customer == null){
            customer = new Customer(arg[1]);
        }
        try {
            LocalDateTime start = LocalDateTime.parse(arg[2], dtf);
            LocalDateTime end = LocalDateTime.parse(arg[3], dtf);
            if(! (arg[0].equals("01") || arg[0].equals("02") ) ){
                throw new IllegalAccessException("No such type of call");
            }
            CallType type = (arg[0].equals("01") ? CallType.OUTCOMING : CallType.INCOMING);
            customer.addCall(new Call(type, start, end));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        customers.put(arg[1], customer);
    }


    public List<AbonentTarifficationRes> getTarrificationResults() {
        try (FileInputStream inputStream = new FileInputStream(pathToCdrPlus);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader) ) {
            while (bufferedReader.ready()) {
                parseCdrPlus(bufferedReader.readLine());
            }
        }
        catch (IOException e){
            log.atError().log(e.getMessage());
        }
        return tarifficateAbonents();
    }


    private List<AbonentTarifficationRes> tarifficateAbonents(){
        List<AbonentTarifficationRes> tariffications = new ArrayList<>();
        for(Customer customer : customers.values()){
            customer.getCalls().sort(Comparator.comparing(Call::getEnded));
            var abonent = abonentRepo.findByPhoneNumber(customer.getNumber())
                    .orElseThrow(() -> new RuntimeException("cdr+ невалиден!"));
            Tariff tariff = abonent.getTariff();
            if(tariff == null){
                throw new IllegalStateException("У абонента не назначен тариф");
            }
            var tarrificationRes = (tariff.getTimeDetails() == null ?
                                    rateCallsByDefaultTariff(customer.getCalls(), tariff) :
                                    rateCallsByTimeDependentTariff(customer.getCalls(), tariff) );
            tarrificationRes.setNumberPhone(customer.getNumber());
            tariffications.add(tarrificationRes);
        }
        return tariffications;
    }

    private AbonentTarifficationRes rateCallsByDefaultTariff(List<Call> calls, Tariff tariff) {
        double sum = 0d;
        List<CallReport> callReports = new ArrayList<>();
        for (Call call : calls) {
            double callCost = 0;
            var callReport = new CallReport(call.getCallType(),
                    call.getStarted(),
                    call.getEnded());
            if (call.getCallType().equals(CallType.INCOMING)) {
                callCost = callReport.calcMinutesDuration() * tariff.getIncomingMinuteCost();
            } else {
                callCost = callReport.calcMinutesDuration() * tariff.getOutcomingMinuteCost();
            }
            sum += callCost;
            callReport.setCost(callCost);
            callReports.add(callReport);
        }
        return AbonentTarifficationRes.builder()
                .sum(sum)
                .callReports(callReports)
                .build();
    }

    private AbonentTarifficationRes rateCallsByTimeDependentTariff(List<Call> calls, Tariff tariff){
        var tariffTimeDetails = tariff.getTimeDetails();
        List<CallReport> callReports = new ArrayList<>();
        double sum = tariffTimeDetails.getAbonentFee();
        int incomingMinutesLeft = tariffTimeDetails.getIncomingMinutesLimit();
        int outcomingMinutesLeft = tariffTimeDetails.getOutcomingMinutesLimit();

        for (Call call : calls) {
            var callReport = new CallReport(call.getCallType(),
                    call.getStarted(),
                    call.getEnded());
            double callCost = 0;
            var minutesDuration = callReport.calcMinutesDuration();
            if (call.getCallType().equals(CallType.INCOMING)) {
                if (incomingMinutesLeft >= minutesDuration) {
                    incomingMinutesLeft -= minutesDuration;
                    callCost += minutesDuration * tariffTimeDetails.getIncomingMinuteCost();
                } else {
                    if (incomingMinutesLeft > 0) {
                        callCost += incomingMinutesLeft * tariffTimeDetails.getIncomingMinuteCost();
                        minutesDuration -= incomingMinutesLeft;
                        incomingMinutesLeft = 0;
                    }
                    callCost += minutesDuration * tariff.getIncomingMinuteCost();
                }
                if(tariff.getTimeDetails().getCommonMinutesLimit()){
                    outcomingMinutesLeft = incomingMinutesLeft;
                }
            }
            if (call.getCallType().equals(CallType.OUTCOMING)) {
                if (outcomingMinutesLeft >= minutesDuration) {
                    outcomingMinutesLeft -= minutesDuration;
                    callCost += minutesDuration * tariffTimeDetails.getOutcomingMinuteCost();
                }
                else {
                    if (outcomingMinutesLeft > 0) {
                        callCost += outcomingMinutesLeft * tariffTimeDetails.getOutcomingMinuteCost();
                        minutesDuration -= outcomingMinutesLeft;
                        outcomingMinutesLeft = 0;
                    }
                    callCost += minutesDuration * tariff.getOutcomingMinuteCost();
                }
                if(tariff.getTimeDetails().getCommonMinutesLimit()){
                    incomingMinutesLeft = outcomingMinutesLeft;
                }
            }
            callReport.setCost(callCost);
            callReports.add(callReport);
            sum += callCost;
        }
        return AbonentTarifficationRes.builder()
                .sum(sum)
                .callReports(callReports)
                .build();
    }
}
