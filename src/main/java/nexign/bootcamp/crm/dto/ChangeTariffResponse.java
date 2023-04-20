package nexign.bootcamp.crm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeTariffResponse {
    private Integer id;
    private String numberPhone;
    private Integer tariffId;
}
