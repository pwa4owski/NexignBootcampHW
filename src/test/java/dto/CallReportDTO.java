package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallReportDTO {
    @JsonProperty("callType")
    private String callType;
    @JsonProperty("startTime")
    private LocalDateTime startTime;
    @JsonProperty("endTime")
    private LocalDateTime endTime;
    @JsonProperty("duration")
    private LocalTime duration;
    @JsonProperty("cost")
    private Double cost;
}
