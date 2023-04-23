package nexign.bootcamp.crm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tariff_time_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TariffTimeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Integer id;

    @Column(name = "incoming_minutes_limit", nullable = false)
    private Integer incomingMinutesLimit;

    @Column(name = "outcoing_minutes_limit", nullable = false)
    private Integer outcomingMinutesLimit;

    /**True, если по тарифу предполагается единый "таймер"
     * как на входящие, так и на исходящие звонки
    */
    @Column(name = "common_minute_limit", nullable = false)
    private Boolean commonMinutesLimit;

    @Column(name = "abonent_fee", nullable = false)
    private Integer abonentFee;

    @Column(name = "incoming_minute_cost", nullable = false)
    private Float incomingMinuteCost;

    @Column(name = "outcoming_minute_cost", nullable = false)
    private Float outcomingMinuteCost;
}
