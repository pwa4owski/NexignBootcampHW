package nexign.bootcamp.crm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class CallReportDTO {
    private String callType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalTime duration;

    private Double cost;
}
