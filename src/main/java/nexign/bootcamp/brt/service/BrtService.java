package nexign.bootcamp.brt.service;

import lombok.extern.slf4j.Slf4j;
import nexign.bootcamp.brt.exception.InvalidLineException;
import nexign.bootcamp.brt.exception.NoSuchAbonentException;
import nexign.bootcamp.brt.exception.NotPositiveBalanceException;
import nexign.bootcamp.brt.model.AbonentTarifficationRes;
import nexign.bootcamp.crm.dto.AbonentTarrificationResponse;
import nexign.bootcamp.crm.entity.CallDetails;
import nexign.bootcamp.crm.repository.AbonentRepo;
import nexign.bootcamp.hrs.model.CallReport;
import nexign.bootcamp.hrs.model.CallType;
import nexign.bootcamp.hrs.service.HrsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BrtService {

    private final HrsService hrsService;
    private final AbonentRepo abonentRepo;

    @Value("${cdr.path}")
    private String pathToCdr;

    @Value("${cdr+.path}")
    private String pathToCdrPlus;

    public BrtService(AbonentRepo abonentRepo, HrsService hrsService) {
        this.hrsService = hrsService;
        this.abonentRepo = abonentRepo;
    }

    private String cdrLineToCdrPlus(String cdrLine) {
        String[] arg = cdrLine.split(", ");
        if (arg.length != 4) {
            throw new InvalidLineException("строка некорректна - пропуск");
        }
        var abonent = abonentRepo.findByPhoneNumber(arg[1])
                .orElseThrow(() -> new NoSuchAbonentException(arg[1]));
        if(abonent.getBalance() < 0){
            throw new NotPositiveBalanceException(arg[1]);
        }
        return String.format("%s, %d\n", cdrLine, abonent.getTariff().getId());
    }


    public void generateCdrPlus() throws IOException {
        File reportFile = new File(pathToCdr);
        try (
                FileInputStream inputStream = new FileInputStream(reportFile);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                FileWriter writer = new FileWriter(pathToCdrPlus)
        ) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                try {
                    String cdrPlusLine = cdrLineToCdrPlus(line);
                    writer.write(cdrPlusLine);
                } catch (RuntimeException ex) {
                    log.atInfo().log(ex.getMessage());
                }
            }
        }
    }

    @Transactional
    public List<AbonentTarrificationResponse> processTariffication(){
        try {
            generateCdrPlus();
        }
        catch (IOException e){
            log.atError().log(e.getMessage());
        }

        // очистим в бд результаты прошлой тарификации
        abonentRepo.findAll().forEach(abonent -> abonent.getCalls().clear());

        var tarifficationResults = hrsService.getTarrificationResults();
        List<AbonentTarrificationResponse> responses = new ArrayList<>();
        for(AbonentTarifficationRes res : tarifficationResults){
            var abonent = abonentRepo.findByPhoneNumber(res.getNumberPhone())
                    .orElseThrow(() -> new NoSuchAbonentException(res.getNumberPhone()));
            for(CallReport callReport : res.getCallReports()){
                CallDetails callDetails = CallDetails.builder()
                        .callType((callReport.getCallType().equals(CallType.OUTCOMING) ? "01" : "02"))
                        .startTime(callReport.getStarted())
                        .endTime(callReport.getEnded())
                        .duration(callReport.getDuration())
                        .cost(callReport.getCost())
                        .build();
                abonent.addCall(callDetails);
            }
            abonent.setBalance(abonent.getBalance() - res.getSum());
            abonent = abonentRepo.save(abonent);
            var shortResult = AbonentTarrificationResponse.builder()
                    .numberPhone(abonent.getPhoneNumber())
                    .balance(abonent.getBalance())
                    .build();
            responses.add(shortResult);
        }
        return responses;
    }
}
