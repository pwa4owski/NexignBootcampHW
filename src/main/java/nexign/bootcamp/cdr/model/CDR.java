package nexign.bootcamp.cdr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CDR {

    private final String typeOfCall;
    private final String number;
    private final String startCall;
    private final String endCall;

    @Override
    public String toString() {
        return  typeOfCall + ", " +
                number + ", " +
                startCall + ", " +
                endCall;
    }

}
