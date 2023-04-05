package pl.mskreczko.api.tokens;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mskreczko.api.domain.user.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users_email_verification_tokens")
public class EmailVerificationToken {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private LocalDateTime expirationDate;

    private String token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailVerificationToken that = (EmailVerificationToken) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(expirationDate, that.expirationDate) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, expirationDate, token);
    }
}
