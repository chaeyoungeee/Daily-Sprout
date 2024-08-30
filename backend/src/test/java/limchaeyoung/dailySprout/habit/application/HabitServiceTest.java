package limchaeyoung.dailySprout.habit.application;

import jakarta.transaction.Transactional;
import limchaeyoung.dailySprout.achievement.repository.AchievementRepository;
import limchaeyoung.dailySprout.habit.domain.Habit;
import limchaeyoung.dailySprout.habit.dto.CreateHabitRequest;
import limchaeyoung.dailySprout.habit.repository.HabitRepository;
import limchaeyoung.dailySprout.user.domain.User;
import limchaeyoung.dailySprout.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static limchaeyoung.dailySprout.habitDay.domain.DayWeek.FRIDAY;
import static limchaeyoung.dailySprout.habitDay.domain.DayWeek.MONDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@Slf4j
class HabitServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HabitService habitService;
    @Autowired
    HabitRepository habitRepository;
    @Autowired
    AchievementRepository achievementRepository;

    @Nested
    @DisplayName("습관 생성 테스트")
    class createHabitTest {
        @Test
        void 습관_생성_성공() {
        	// given
            User user1 = User.builder()
                    .email("test@test.ac.kr")
                    .nickname("채영")
                    .build();

            userRepository.save(user1);

            CreateHabitRequest request1 = new CreateHabitRequest("물 마시기", Set.of(MONDAY, FRIDAY));

        	// when
            Habit habit = habitService.createHabit(request1, user1.getEmail());
            Habit findHabit = habitRepository.findById(habit.getHabitId()).get();

            // then
            assertAll(
                    () -> assertThat(findHabit.getContent()).isEqualTo(habit.getContent()),
                    () -> assertThat(findHabit.getHabitDays().size()).isEqualTo(2)
            );
        }
    }

}