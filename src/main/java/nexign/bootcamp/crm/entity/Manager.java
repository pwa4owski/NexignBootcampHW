package nexign.bootcamp.crm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "manager")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "pswrd", nullable = false)
    private String password;
}
