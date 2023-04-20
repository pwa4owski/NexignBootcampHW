package nexign.bootcamp.crm.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PaymentRequest {
    @Pattern(regexp = "\\d{10}", message = "Номер некорректен")
    @NotBlank
    private String numberPhone;
    @Min(value = 1, message = "Сумма пополнения должна быть положительной")
    @NotNull
    private Integer money;
}
