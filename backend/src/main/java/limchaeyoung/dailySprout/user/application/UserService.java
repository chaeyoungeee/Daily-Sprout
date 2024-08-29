package limchaeyoung.dailySprout.user.application;

import jakarta.transaction.Transactional;
import limchaeyoung.dailySprout.user.domain.User;
import limchaeyoung.dailySprout.common.exception.ErrorCode;
import limchaeyoung.dailySprout.user.exception.CustomUserException;
import limchaeyoung.dailySprout.user.repository.UserRepository;
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
