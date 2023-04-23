package nexign.bootcamp.crm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAbonentResponse {
    private String numberPhone;
    private Integer tariffId;
    private Double balance;
}

