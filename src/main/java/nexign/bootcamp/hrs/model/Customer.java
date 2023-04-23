package nexign.bootcamp.hrs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class Customer {
    private String number;
    private List<Call> calls = new ArrayList<>();

    private int tariffCode;

    public Customer(String number, int tariffCode) throws IllegalAccessException {
        this.number = number;
        this.tariffCode = tariffCode;
    }

    public void addCall(Call call){
        calls.add(call);
    }
}
