package limchaeyoung.dailySprout.habit.application;

import jakarta.transaction.Transactional;
import limchaeyoung.dailySprout.achievement.repository.AchievementRepository;
import limchaeyoung.dailySprout.habit.domain.Habit;
import limchaeyoung.dailySprout.habit.domain.HabitStatus;
import limchaeyoung.dailySprout.habit.dto.CreateHabitRequest;
import limchaeyoung.dailySprout.habit.dto.HabitInfoResponse;
import limchaeyoung.dailySprout.habit.exception.CustomHabitException;
import limchaeyoung.dailySprout.habit.repository.HabitRepository;
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
import static limchaeyoung.dailySprout.habitDay.domain.DayWeek.SUNDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @DisplayName("습관 조회 테스트")
    class getHabitTest {
        @Test
        void 활성화된_습관_정보_조회_성공() {
            // given
            User user1 = User.builder()
                    .email("test@test.ac.kr")
                    .nickname("채영")
                    .build();

            userRepository.save(user1);

            CreateHabitRequest request1 = new CreateHabitRequest("물 마시기", Set.of(MONDAY, FRIDAY));
            Habit habit1 = habitService.createHabit(request1, user1.getEmail());
            CreateHabitRequest request2 = new CreateHabitRequest("산책하기", Set.of(SATURDAY));
            Habit habit2 = habitService.createHabit(request2, user1.getEmail());

            habitService.deactivateHabit(habit2.getHabitId(), user1.getEmail());

            // when
            log.info("=====start=====");
            List<HabitInfoResponse> habitInfos = habitService.findAllByUserAndStatus(user1.getEmail());


            //then
            assertAll(
                    () -> assertThat(habitInfos.size()).isEqualTo(1),
                    () -> assertThat(habitInfos.get(0).content()).isEqualTo("물 마시기")
            );

        }
    }

    @Nested
    @DisplayName("습관 삭제 테스트")
    class deactivateHabitTest {
        @Test
        void 습관_삭제_성공() {
            // given
            User user1 = User.builder()
                    .email("test@test.ac.kr")
                    .nickname("채영")
                    .build();

            userRepository.save(user1);

            CreateHabitRequest request1 = new CreateHabitRequest("물 마시기", Set.of(MONDAY, FRIDAY));
            Habit habit1 = habitService.createHabit(request1, user1.getEmail());

            // when
            habitService.deactivateHabit(habit1.getHabitId(), user1.getEmail());

            //then
            assertThat(habit1.getStatus()).isEqualTo(HabitStatus.INACTIVE);
        }

        @Test
        void 습관을_생성한_유저가_아니면_예외_발생() {
            // given
            User user1 = User.builder()
                    .email("test@test.ac.kr")
                    .nickname("채영")
                    .build();
            userRepository.save(user1);

            User user2 = User.builder()
                    .email("abc@1234")
                    .nickname("임채영")
                    .build();
            userRepository.save(user2);

            CreateHabitRequest request1 = new CreateHabitRequest("물 마시기", Set.of(MONDAY, FRIDAY));
            Habit habit1 = habitService.createHabit(request1, user1.getEmail());

            // when & then
            assertThatThrownBy(() -> habitService.deactivateHabit(habit1.getHabitId(), "abc@1234"))
                    .isInstanceOf(CustomHabitException.class)
                    .hasMessageContaining("습관이 존재하지 않습니다.");
        }
    }

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