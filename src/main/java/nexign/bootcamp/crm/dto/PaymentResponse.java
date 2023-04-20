package nexign.bootcamp.crm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private Integer id;
    private String numberPhone;
    private Integer money;
}
