package limchaeyoung.dailySprout.habitDay.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import limchaeyoung.dailySprout.habitDay.exception.CustomHabitDayException;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;
import static limchaeyoung.dailySprout.common.exception.ErrorCode.DAY_NOT_EXIST;

@RequiredArgsConstructor
public enum Day {
    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일"),
    FRIDAY("금요일"),
    SATURDAY("토요일"),
    SUNDAY("일요일");

    private final String name;
    private static final Map<String, Day> NAME_TO_ENUM_MAP = new HashMap<>();

    static {
        for (Day day : Day.values()) {
            NAME_TO_ENUM_MAP.put(day.name, day);
        }
    }

    @JsonValue
    public String getName() { return name; }

    @JsonCreator
    public static Day fromName(String name) {
        Day day = NAME_TO_ENUM_MAP.get(name);
        if (day == null) throw new CustomHabitDayException(DAY_NOT_EXIST);

        return day;
    }
}
