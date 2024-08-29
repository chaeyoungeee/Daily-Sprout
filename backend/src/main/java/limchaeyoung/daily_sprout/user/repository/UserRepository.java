package limchaeyoung.daily_sprout.user.repository;


import limchaeyoung.daily_sprout.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
