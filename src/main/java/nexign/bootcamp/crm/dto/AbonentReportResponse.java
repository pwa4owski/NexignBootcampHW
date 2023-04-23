package nexign.bootcamp.crm.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AbonentReportResponse {
    private Integer id;
    private String numberPhone;
    private Integer tariffIndex;
    private List<CallReportDTO> payload;
    private Double totalCost;
}
