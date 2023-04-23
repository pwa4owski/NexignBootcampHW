package nexign.bootcamp.crm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tariff")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Tariff {
    @Id
    private Integer id;

    @Column(name = "tariff_name", nullable = false)
    private String tariffName;

    @Column(name = "incoming_minute_cost", nullable = false)
    private Double incomingMinuteCost;

    @Column(name = "outcoming_minute_cost", nullable = false)
    private Double outcomingMinuteCost;

    /**
     *
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tariff_time_details_id")
    private TariffTimeDetails timeDetails;



}
