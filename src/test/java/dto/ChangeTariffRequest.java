package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTariffRequest {
    @Pattern(regexp = "\\d{11}", message = "Номер некорректен")
    @NotBlank
    @JsonProperty("numberPhone")
    private String numberPhone;
    @NotNull
    @JsonProperty("tariffId")
    private Integer tariffId;
}
