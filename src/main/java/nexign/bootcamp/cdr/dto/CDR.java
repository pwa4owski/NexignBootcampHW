package nexign.bootcamp.cdr.dto;

public class CDR {

    private final String typeOfCall;
    private final String number;
    private final String startCall;
    private final String endCall;

    public CDR(String typeOfCall, String number, String startCall, String endCall) {
        this.typeOfCall = typeOfCall;
        this.number = number;
        this.startCall = startCall;
        this.endCall = endCall;
    }

    @Override
    public String toString() {
        return  typeOfCall + ", " +
                number + ", " +
                startCall + ", " +
                endCall + ", ";
    }

}
