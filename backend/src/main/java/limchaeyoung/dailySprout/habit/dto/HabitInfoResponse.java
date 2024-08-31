package limchaeyoung.dailySprout.habit.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "습관 정보 조회 응답")
public record HabitInfoResponse(Long habitId,
                                String content) { }
