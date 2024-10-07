package ds.apimodule.habit.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "습관 정보 조회 응답 DTO")
public record HabitInfoResponse(Long habitId,
                                @Schema(description = "습관", example = "물 마시기")
                                String content) { }
