package ds.apimodule.habit.mapper;

import ds.apimodule.habit.dto.CreateHabitRequest;
import ds.apimodule.habit.dto.HabitInfoResponse;
import ds.coremodule.habit.domain.Habit;
import ds.coremodule.user.domain.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-08T03:02:42+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class HabitMapperImpl implements HabitMapper {

    @Override
    public Habit toEntity(CreateHabitRequest request, User user) {
        if ( request == null && user == null ) {
            return null;
        }

        Habit.HabitBuilder habit = Habit.builder();

        if ( request != null ) {
            habit.content( request.content() );
        }
        habit.user( user );

        return habit.build();
    }

    @Override
    public HabitInfoResponse toHabitInfoResponse(Habit habit) {
        if ( habit == null ) {
            return null;
        }

        Long habitId = null;
        String content = null;

        habitId = habit.getHabitId();
        content = habit.getContent();

        HabitInfoResponse habitInfoResponse = new HabitInfoResponse( habitId, content );

        return habitInfoResponse;
    }
}
