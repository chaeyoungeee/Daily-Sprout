package limchaeyoung.dailySprout.achievement.application;

import jakarta.transaction.Transactional;
import limchaeyoung.dailySprout.achievement.domain.Achievement;
import limchaeyoung.dailySprout.achievement.repository.AchievementRepository;
import limchaeyoung.dailySprout.habit.application.HabitService;
import limchaeyoung.dailySprout.habit.domain.Habit;
import limchaeyoung.dailySprout.habit.dto.CreateHabitRequest;
import limchaeyoung.dailySprout.user.domain.User;
import limchaeyoung.dailySprout.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static limchaeyoung.dailySprout.habitDay.domain.DayWeek.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@Slf4j
class AchievementServiceTest {

    @Nested
    @DisplayName("달성 생성 테스트")
    class createAchievementTest {

        @Autowired
        UserRepository userRepository;
        @Autowired
        HabitService habitService;
        @Autowired
        AchievementService achievementService;
        @Autowired
        AchievementRepository achievementRepository;

        @Test
        @Rollback(value = false)
        void 한달_모든_유저_모든_달성_추가_성공() {
            // given
            User user1 = User.builder()
                    .email("test1@test.ac.kr")
                    .nickname("채영")
                    .build();
            userRepository.save(user1);
            User user2 = User.builder()
                    .email("test2@test.ac.kr")
                    .nickname("채영2")
                    .build();
            userRepository.save(user2);

            CreateHabitRequest request1 = new CreateHabitRequest("물 마시기", Set.of(MONDAY, FRIDAY));
            Habit habit1 = habitService.createHabit(request1, user1.getEmail());
            CreateHabitRequest request2 = new CreateHabitRequest("산책하기", Set.of(SATURDAY));
            Habit habit2 = habitService.createHabit(request2, user1.getEmail());
            CreateHabitRequest request3 = new CreateHabitRequest("물 마시기", Set.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY));
            Habit habit3 = habitService.createHabit(request3, user2.getEmail());

            // when
            log.info("====== start ======");
            achievementService.createAllAchievementInMonth(2024, 9);

            List<Achievement> achievementByHabit1 =
                    achievementRepository.findAllByHabitAndHabitDateYearAndHabitDateMonth(habit1, 2024, 9);
            List<Achievement> achievementByHabit2 =
                    achievementRepository.findAllByHabitAndHabitDateYearAndHabitDateMonth(habit2, 2024, 9);
            List<Achievement> achievementByHabit3 =
                    achievementRepository.findAllByHabitAndHabitDateYearAndHabitDateMonth(habit3, 2024, 9);

            // then
            assertAll(
                    () -> assertThat(achievementByHabit1.size()).isEqualTo(9),
                    () -> assertThat(achievementByHabit2.size()).isEqualTo(4),
                    () -> assertThat(achievementByHabit3.size()).isEqualTo(30)
            );
        }
    }
}