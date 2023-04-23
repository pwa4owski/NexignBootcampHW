package nexign.bootcamp.crm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "call_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CallDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    private String callType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalTime duration;

    private double cost;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "abonent_id")
    private Abonent abonent;

}
