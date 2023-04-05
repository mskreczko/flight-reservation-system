package pl.mskreczko.api.tokens;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mskreczko.api.domain.exceptions.NoSuchEntityException;
import pl.mskreczko.api.domain.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenService {

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final Integer EXPIRATION = 3;

    public String createToken(User user) {
        String token = UUID.randomUUID().toString();

        emailVerificationTokenRepository.save(new EmailVerificationToken(
                UUID.randomUUID(), user, LocalDateTime.now().plusMinutes(EXPIRATION), token
        ));

        return token;
    }

    @Transactional
    public boolean verifyToken(String token) throws NoSuchEntityException {
        final var verificationToken = emailVerificationTokenRepository.findByToken(token).orElseThrow(NoSuchEntityException::new);
        if (LocalDateTime.now().isAfter(verificationToken.getExpirationDate())) {
            emailVerificationTokenRepository.deleteById(verificationToken.getId());
            return false;
        }
        emailVerificationTokenRepository.deleteById(verificationToken.getId());
        verificationToken.getUser().setEnabled(true);
        return true;
    }
}
