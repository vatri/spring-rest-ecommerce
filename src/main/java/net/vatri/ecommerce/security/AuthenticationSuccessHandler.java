package net.vatri.ecommerce.security;

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

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ){

        clearAuthenticationAttributes(request);
        User user = (User)authentication.getPrincipal();
        String jwt = tokenHelper.generateToken( user.getUsername() );

        UserTokenState userTokenState = new UserTokenState(jwt, EXPIRES_IN);
        try {
            String jwtResponse = objectMapper.writeValueAsString(userTokenState);
            response.setContentType("application/json");
            response.getWriter().write(jwtResponse);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
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
