package nexign.bootcamp.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangeTariffRequest {
    @Pattern(regexp = "\\d{10}", message = "Номер некорректен")
    @NotBlank
    private String numberPhone;
    @NotNull
    private Integer tariffId;
}
