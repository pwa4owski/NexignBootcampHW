package nexign.bootcamp.hrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Call {
    CallType callType;
    LocalDateTime started;
    LocalDateTime ended;
}
