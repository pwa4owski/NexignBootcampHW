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
public class PaymentResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("numberPhone")
    private String numberPhone;
    @JsonProperty("money")
    private Double money;
}
