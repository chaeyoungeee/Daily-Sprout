package ds.apimodule.habit.application;



import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ds.apimodule.habit.dto.CreateHabitRequest;
import ds.apimodule.habit.dto.HabitInfoResponse;
import ds.apimodule.habit.mapper.HabitMapper;
import ds.apimodule.user.application.UserService;
import ds.coremodule.achievement.domain.Achievement;
import ds.coremodule.achievement.repository.AchievementRepository;
import ds.coremodule.exception.ErrorCode;
import ds.coremodule.habit.domain.Habit;
import ds.coremodule.habit.domain.HabitStatus;
import ds.coremodule.habit.exception.CustomHabitException;
import ds.coremodule.habit.repository.HabitRepository;
import ds.coremodule.habitDay.domain.DayWeek;
import ds.coremodule.user.domain.User;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HabitService {
    private final HabitRepository habitRepository;
    private final AchievementRepository achievementRepository;

    private final UserService userService;

    private final HabitMapper habitMapper;

    public List<HabitInfoResponse> findAllByUserAndStatus(String email) {
        User user = userService.findByEmail(email);
        List<Habit> habits = habitRepository.findAllByUserAndStatus(user, HabitStatus.ACTIVE);

        return habits.stream().map(habitMapper::toHabitInfoResponse).toList();
    }

    public void deactivateHabit(Long habitId, String email) {
        User user = userService.findByEmail(email);
        Habit habit = habitRepository.findByHabitIdAndUser(habitId, user).orElseThrow(() -> new CustomHabitException(
            ErrorCode.HABIT_NOT_FOUND));

        habit.deactivate();
    }


    public Habit createHabit(CreateHabitRequest createHabitRequest, String email) {
        User user = userService.findByEmail(email);

        // 습관 저장
        Habit habit = habitMapper.toEntity(createHabitRequest, user);
        habitRepository.save(habit);

        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        if (createHabitRequest.dayWeeks().contains(DayWeek.valueOf(dayOfWeek.toString()))) {
            Achievement achievement = Achievement.builder()
                    .habit(habit)
                    .habitDate(LocalDate.now())
                    .build();
            achievementRepository.save(achievement);
        }

        return habit;
    }
}


