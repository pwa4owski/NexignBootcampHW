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

    public Customer(String number){
        this.number = number;
    }

    public void addCall(Call call){
        calls.add(call);
    }
}
