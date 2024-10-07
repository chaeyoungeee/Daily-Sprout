package ds.apimodule.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "카카오 로그인 요청")
public record KakaoLoginRequest(
        @NotBlank(message = "code 값을 입력해주세요.")
        @Schema(description = "code", example = "adsfadssER5DoV5g1Vq2bkT1bRh")
        String code
) {
}