package nexign.bootcamp.brt.model;

import lombok.Builder;
import lombok.Data;
import nexign.bootcamp.hrs.model.CallReport;

import java.util.List;

@Data
@Builder
public class AbonentTarifficationRes{
    private String numberPhone;
    private double sum;
    private List<CallReport> callReports;
}