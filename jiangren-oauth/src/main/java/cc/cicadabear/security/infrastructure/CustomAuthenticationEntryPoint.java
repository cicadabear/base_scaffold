package cc.cicadabear.security.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException ) throws IOException {
		response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
		logger.info("CustomAuthenticationEntryPoint","commence");

	}

}