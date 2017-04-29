package net.vatri.ecommerce.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

//    @Value("${jwt.cookie}")
//    private String TOKEN_COOKIE;

//    @Value("${app.user_cookie}")
//    private String USER_COOKIE;

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication ) throws IOException, ServletException {
        clearAuthenticationAttributes(request);
        User user = (User)authentication.getPrincipal();
        String jws = tokenHelper.generateToken( user.getUsername() );

        // Create token auth Cookie
//        Cookie authCookie = new Cookie( TOKEN_COOKIE, ( jws ) );
//        authCookie.setPath( "/" );
//        authCookie.setHttpOnly( true );
//        authCookie.setMaxAge( EXPIRES_IN );
//        // Create flag Cookie
//        Cookie userCookie = new Cookie( USER_COOKIE, ( user.getName() ) );
//        userCookie.setPath( "/" );
//        userCookie.setMaxAge( EXPIRES_IN );
//        // Add cookie to response
//        response.addCookie( authCookie );
//        response.addCookie( userCookie );
        // JWT is also in the response
        UserTokenState userTokenState = new UserTokenState(jws, EXPIRES_IN);
        String jwtResponse = objectMapper.writeValueAsString( userTokenState );
        response.setContentType("application/json");
        response.getWriter().write( jwtResponse );
    }

    private class UserTokenState{
        private String jws;
        private int expires;

        public UserTokenState(String jws, int expires){
            this.jws = jws;
            this.expires = expires;
        }

        public String getJws() {
            return jws;
        }

        public void setJws(String jws) {
            this.jws = jws;
        }

        public int getExpires() {
            return expires;
        }

        public void setExpires(int expire) {
            this.expires = expire;
        }
    }
}
