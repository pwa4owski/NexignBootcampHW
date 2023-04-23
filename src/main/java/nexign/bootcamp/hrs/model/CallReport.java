package nexign.bootcamp.hrs.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class CallReport extends Call{

    private LocalTime duration;
    private Double cost;

    public CallReport(CallType callType, LocalDateTime started, LocalDateTime ended) {
        super(callType, started, ended);
        long secondsDuration = Duration.between(started, ended).getSeconds();
        this.duration = LocalTime.ofSecondOfDay(secondsDuration);
    }

    public long calcMinutesDuration(){
        long secondsDuration = Duration.between(started, ended).getSeconds();
        return (secondsDuration % 60 == 0 ? secondsDuration / 60 : secondsDuration / 60 + 1);
    }



}
