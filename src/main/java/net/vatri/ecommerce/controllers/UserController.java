package net.vatri.ecommerce.controllers;

import net.vatri.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final UserLogin login){
        UserDetails user = userDetailService.loadUserByUsername(login.email);


        System.out.println("Checking email: " + login.email);


        if(user != null && user.getPassword() == login.password){
            System.out.println("Logged in!");
            return new LoginResponse(Jwts.builder().setSubject(login.email)
                    .claim("roles", "admin").setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, "secretkey").compact());
        } else {
            System.out.println("NOT logged in!");
            return new LoginResponse("NONE");
        }
    }

    @SuppressWarnings("unused")
    private static class UserLogin {
        public String email;
        public String password;
    }

    @SuppressWarnings("unused")
    private static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
            this.token = token;
        }
    }



}