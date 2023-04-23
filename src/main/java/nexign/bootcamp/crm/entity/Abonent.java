package nexign.bootcamp.crm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "abonent")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Abonent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;


    private Double balance;

    @OneToMany(mappedBy = "abonent", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}, orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    List<CallDetails> calls = new ArrayList<>();

    public void addCall(CallDetails callDetails){
        calls.add(callDetails);
        callDetails.setAbonent(this);
    }

}
