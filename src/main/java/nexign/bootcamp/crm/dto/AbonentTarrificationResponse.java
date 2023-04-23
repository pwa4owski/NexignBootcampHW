package nexign.bootcamp.crm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbonentTarrificationResponse {
    private String numberPhone;
    private Double balance;
}
