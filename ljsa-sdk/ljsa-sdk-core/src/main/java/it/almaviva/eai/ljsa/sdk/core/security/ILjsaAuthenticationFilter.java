package it.almaviva.eai.ljsa.sdk.core.security;

import it.almaviva.eai.ljsa.sdk.core.model.LjsaUserDTO;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface ILjsaAuthenticationFilter {

    LjsaUserDTO doAuthentication(ServletRequest httpServletRequest, ServletResponse httpServletResponse);

}
