package org.helloworld.scribejavawebapp;

import javax.servlet.*;
import java.io.IOException;

/**
 * User: hal
 * Date: 12.06.2011
 * Time: 12:53:57
 */
public class EncodingFilter
        implements Filter {

    public void init(FilterConfig filterConfig)
            throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

}