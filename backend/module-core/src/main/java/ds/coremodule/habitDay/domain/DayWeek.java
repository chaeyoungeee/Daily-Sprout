package ds.coremodule.habitDay.domain;

import static ds.coremodule.exception.ErrorCode.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import ds.coremodule.habitDay.exception.CustomHabitDayException;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;



@RequiredArgsConstructor
public enum DayWeek {
    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일"),
    FRIDAY("금요일"),
    SATURDAY("토요일"),
    SUNDAY("일요일");

    private final String name;
    private static final Map<String, DayWeek> NAME_TO_ENUM_MAP = new HashMap<>();

    static {
        for (DayWeek dayWeek : DayWeek.values()) {
            NAME_TO_ENUM_MAP.put(dayWeek.name, dayWeek);
        }
    }

    @JsonValue
    public String getName() { return name; }

    @JsonCreator
    public static DayWeek fromName(String name) {
        DayWeek dayWeek = NAME_TO_ENUM_MAP.get(name);
        if (dayWeek == null) throw new CustomHabitDayException(DAY_NOT_FOUND);

        return dayWeek;
    }
}
