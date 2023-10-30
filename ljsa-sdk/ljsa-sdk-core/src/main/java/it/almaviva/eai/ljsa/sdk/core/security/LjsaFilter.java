package it.almaviva.eai.ljsa.sdk.core.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.eai.ljsa.sdk.core.model.LjsaUserDTO;
import it.almaviva.eai.ljsa.sdk.core.throwable.JWTHeaderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public abstract class LjsaFilter extends OncePerRequestFilter {

    private final Set<String> skipUrls = new HashSet<>(Arrays.asList("/csrf","/favicon.ico","/v2/api-docs","/swagger-resources/**", "/swagger-ui.html**","/webjars/**","/actuator/**", "/"));
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private ILjsaAuthenticationFilter iLjsaAuthenticationFilter;

    @Value("#{'${ljsa.skip-urls:}'.split(',')}")
    private List<String> ljsaSkipUrls;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

            try {
                LjsaUserDTO ljsaUserDTO = iLjsaAuthenticationFilter.doAuthentication(httpServletRequest, httpServletResponse);
                log.debug("LjsaUserDTO > " + ljsaUserDTO);
                AbstractAuthenticationToken authenticationToken = giveMePrincipal(httpServletRequest, httpServletResponse, ljsaUserDTO);//others filters
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(httpServletRequest, httpServletResponse);

            }catch (JWTHeaderException e){
                FilterError filterError = new FilterError(HttpStatus.UNAUTHORIZED, e.getMessage(), httpServletRequest.getRequestURI(), e);
                httpServletResponse.getWriter().write(convertObjectToJson(filterError));
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpServletResponse.setContentType("application/json");
                log.error("an exception was thrown", e);
            }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        Optional.ofNullable(ljsaSkipUrls).ifPresent(skipUrls::addAll);
        return   skipUrls.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

    protected abstract AbstractAuthenticationToken giveMePrincipal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, LjsaUserDTO ljsaUserDTO);
}
