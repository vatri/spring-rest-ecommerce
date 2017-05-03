package net.vatri.ecommerce.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${jwt.header}")
    private String AUTH_HEADER;

//    @Value("${jwt.cookie}")
//    private String AUTH_COOKIE;

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    UserDetailsService userDetailServiceImpl;

    private String getToken( HttpServletRequest request ) {

//        Cookie authCookie = getCookieValueByName( request, AUTH_COOKIE );
//        if ( authCookie != null ) {
//            return authCookie.getValue();
//        }

        String authHeader = request.getHeader(AUTH_HEADER);
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

//    protected Cookie getCookieValueByName(HttpServletRequest request, String name) {
//        if (request.getCookies() == null) {
//            return null;
//        }
//        for (int i = 0; i < request.getCookies().length; i++) {
//            if (request.getCookies()[i].getName().equals(name)) {
//                return request.getCookies()[i];
//            }
//        }
//        return null;
//    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String error = "";
        String authToken = getToken( request );
        if (authToken != null) {
            // get username from token
            String username = tokenHelper.getUsernameFromToken( authToken );
            if ( username != null ) {
                // get user
                UserDetails userDetails = userDetailServiceImpl.loadUserByUsername( username );
                // create authentication
                TokenBasedAuthentication authentication = new TokenBasedAuthentication( userDetails );
                authentication.setToken( authToken );
                SecurityContextHolder.getContext().setAuthentication( authentication );
                chain.doFilter(request, response);
            } else {
                error = "Username from token can't be found in DB.";
            }
        } else {
            error = "No Bearer token provided.";
        }
        if(! error.equals("")){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authentication failed");
            System.out.println("ERROR: " + error);
            SecurityContextHolder.getContext().setAuthentication( new AnonAuthentication() );//prevent show login form...
        }
    }

}
