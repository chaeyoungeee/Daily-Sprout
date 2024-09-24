package limchaeyoung.dailySprout.habit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import limchaeyoung.dailySprout.habitDay.domain.DayWeek;

import java.util.Set;

@Schema(description = "습관 생성 요청 DTO")
public record CreateHabitRequest(@NotBlank(message = "생성할 습관을 입력해주세요.")
                              @Schema(description = "생성할 습관", example = "물 마시기")
                              String content,
                                 @NotEmpty(message = "생성할 요일을 선택해주세요.")
                              @Schema(description = "생성할 요일", example = "[\"월요일\", \"화요일\"]")
                              Set<DayWeek> dayWeeks) { }