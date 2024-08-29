package limchaeyoung.daily_sprout.user.application;

import jakarta.transaction.Transactional;
import limchaeyoung.daily_sprout.user.domain.User;
import limchaeyoung.daily_sprout.common.exception.ErrorCode;
import limchaeyoung.daily_sprout.user.exception.CustomUserException;
import limchaeyoung.daily_sprout.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomUserException(ErrorCode.USER_NOT_FOUND));
    }
}
