package ds.apimodule.habit.presentation;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ds.apimodule.auth.domain.CustomUserDetails;
import ds.apimodule.habit.application.HabitService;
import ds.apimodule.habit.dto.CreateHabitRequest;
import ds.apimodule.habit.dto.HabitInfoResponse;
import ds.commonmodule.config.SwaggerConfig;
import ds.commonmodule.response.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
@Tag(name = "습관", description = "습관 관련 API")
public class HabitController {
    private final HabitService habitService;

    @Transactional(readOnly = true)
    @GetMapping
    @Operation(summary = "습관 조회",
            security = @SecurityRequirement(name = SwaggerConfig.JWT_SECURITY_SCHEME))
    public StandardResponse<List<HabitInfoResponse>> getActiveHabits(@AuthenticationPrincipal CustomUserDetails user) {
        return StandardResponse.success(habitService.findAllByUserAndStatus(user.getEmail()));
    }

    @PostMapping("/{habitId}")
    @Operation(summary = "습관 삭제",
            security = @SecurityRequirement(name = SwaggerConfig.JWT_SECURITY_SCHEME)
    )
    public StandardResponse<String> deactivateHabit(
            @Parameter(required = true, description = "습관 아이디")
            @PathVariable("habitId") Long habitId,
            @AuthenticationPrincipal CustomUserDetails user) {
        habitService.deactivateHabit(habitId, user.getEmail());

        return StandardResponse.success("습관 삭제에 성공하였습니다.");
    }

    @PostMapping
    @Operation(summary = "습관 생성",
            security = @SecurityRequirement(name = SwaggerConfig.JWT_SECURITY_SCHEME))
    public StandardResponse<String> createHabit(@RequestBody @Valid CreateHabitRequest createHabitRequest, @AuthenticationPrincipal CustomUserDetails user) {
        habitService.createHabit(createHabitRequest, user.getEmail());

        return StandardResponse.success("습관 생성에 성공하였습니다.");
    }
}
