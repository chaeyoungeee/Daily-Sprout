package limchaeyoung.daily_sprout.auth.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import limchaeyoung.daily_sprout.common.response.StandardResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpResponse {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static void setDefaultResponse(HttpServletResponse response, HttpStatus httpStatus) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatus.value());
		response.setCharacterEncoding("UTF-8");
	}

	public static void sendSuccessResponse(HttpServletResponse response, HttpStatus httpStatus, Object body)
			throws IOException {
		setDefaultResponse(response, httpStatus);
		String responseBody = objectMapper.writeValueAsString(StandardResponse.success(body));
		response.getWriter().write(responseBody);
	}

	public static void sendErrorResponse(HttpServletResponse response, HttpStatus httpStatus, Object body)
			throws IOException {
		setDefaultResponse(response, httpStatus);
		String responseBody = objectMapper.writeValueAsString(body);
		response.getWriter().write(responseBody);
	}
}