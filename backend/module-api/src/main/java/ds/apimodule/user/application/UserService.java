package ds.apimodule.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ds.coremodule.exception.ErrorCode;
import ds.coremodule.user.domain.User;
import ds.coremodule.user.exception.CustomUserException;
import ds.coremodule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomUserException(ErrorCode.USER_NOT_FOUND));
    }
}
