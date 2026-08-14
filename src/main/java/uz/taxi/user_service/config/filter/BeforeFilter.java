package uz.taxi.user_service.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.taxi.user_service.base.HeaderKeys;
import uz.taxi.user_service.common.GenericResponse;
import uz.taxi.user_service.common.GlobalVar;
import uz.taxi.user_service.config.helper.ConfigUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class BeforeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        GlobalVar.clearContext();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final String X_IP = request.getHeader(HeaderKeys.IP);
        final String X_USER_AGENT = request.getHeader(HeaderKeys.USER_AGENT);

        final String X_APP_VERSION = request.getHeader(HeaderKeys.APP_VERSION);
        final String X_DEBUG_VERSION = request.getHeader(HeaderKeys.DEBUG_VERSION);
        final String X_DEVICE_TYPE = request.getHeader(HeaderKeys.DEVICE_TYPE);
        final String X_DEVICE_MODEL = request.getHeader(HeaderKeys.DEVICE_MODEL);
        final String X_DEVICE_ID = request.getHeader(HeaderKeys.DEVICE_ID);
        final String X_LANG = request.getHeader(HeaderKeys.LANG);

        final String USER_ID = request.getHeader(HeaderKeys.USER_ID);
        // final String X_USER_ROLE = request.getHeader(HeaderKeys.X_USER_ROLE);
        final String LOG_ID = request.getHeader(HeaderKeys.LOG_ID);
        

        GlobalVar.setIpAddress(Optional.ofNullable(X_IP).orElse(""));
        GlobalVar.setUserAgent(Optional.ofNullable(X_USER_AGENT).orElse(""));

        GlobalVar.setAppVersion(X_APP_VERSION);
        GlobalVar.setDebugVersion("1".equals(X_DEBUG_VERSION) ? Boolean.TRUE : Boolean.FALSE);
        GlobalVar.setDeviceModel(Optional.ofNullable(X_DEVICE_MODEL).orElse("unknown device"));
        GlobalVar.setDeviceId(Optional.ofNullable(X_DEVICE_ID).orElse(UUID.randomUUID().toString()));

        GlobalVar.setUserId(USER_ID);
        GlobalVar.setLogId(Optional.ofNullable(LOG_ID).orElse(UUID.randomUUID().toString()));

//        if (CoreUtils.isEmpty(X_DEVICE_TYPE)) {
//           this.exception(response, "Unknown device type", ActionTypeEnum.FORBIDDEN);
//           return;
//        }

        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(
                    ConfigUtils.wrapRequest(request),
                    ConfigUtils.wrapResponse(response)
            );
        }
    }

    private void exception(HttpServletResponse response, String message) throws IOException {
        SecurityContextHolder.clearContext();
        GlobalVar.clearContext();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, GenericResponse.error("Unknown error"));
        out.flush();
    }
}
