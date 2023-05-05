package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbonentTarrificationResponse {
    @JsonProperty("numberPhone")
    private String numberPhone;
    @JsonProperty("balance")
    private Double balance;
}
