package ds.apimodule.habit.mapper;

import org.mapstruct.Mapper;

import ds.apimodule.habit.dto.CreateHabitRequest;
import ds.apimodule.habit.dto.HabitInfoResponse;
import ds.coremodule.habit.domain.Habit;
import ds.coremodule.user.domain.User;

@Mapper(componentModel = "spring")
public interface HabitMapper {

  Habit toEntity(CreateHabitRequest request, User user);

  HabitInfoResponse toHabitInfoResponse(Habit habit);
}
