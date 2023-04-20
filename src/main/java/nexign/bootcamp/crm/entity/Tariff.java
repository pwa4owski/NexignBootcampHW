package nexign.bootcamp.crm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tariff")
@NoArgsConstructor
@Getter
public class Tariff {
    @Id
    private Integer id;

    @Column(name = "tariff_name", nullable = false)
    private String tariffName;
}
