package in.kb.main.entitys;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String refreshToken;

    private LocalDateTime expire;

    @OneToOne
    private User user;
}
