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
public class CreateAbonentResponse {
    @JsonProperty("numberPhone")
    private String numberPhone;
    @JsonProperty("tariffId")
    private Integer tariffId;
    @JsonProperty("balance")
    private Double balance;
}

