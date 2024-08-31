package limchaeyoung.dailySprout.habit.mapper;

import limchaeyoung.dailySprout.habit.domain.Habit;
import limchaeyoung.dailySprout.habit.dto.CreateHabitRequest;
import limchaeyoung.dailySprout.habit.dto.HabitInfoResponse;
import limchaeyoung.dailySprout.user.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HabitMapper {

  Habit toEntity(CreateHabitRequest request, User user);

  HabitInfoResponse toHabitInfoResponse(Habit habit);
}
