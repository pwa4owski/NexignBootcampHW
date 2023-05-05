package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbonentReportResponse {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("numberPhone")
    private String numberPhone;
    @JsonProperty("tariffIndex")
    private Integer tariffIndex;
    @JsonProperty("payload")
    private List<CallReportDTO> payload;
    @JsonProperty("totalCost")
    private Double totalCost;
}
