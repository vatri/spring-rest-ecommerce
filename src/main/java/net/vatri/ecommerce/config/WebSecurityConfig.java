package net.vatri.ecommerce.config;

import net.vatri.ecommerce.security.AuthenticationFailureHandler;
import net.vatri.ecommerce.security.AuthenticationSuccessHandler;
import net.vatri.ecommerce.security.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter jwtAuthenticationTokenFilter() throws Exception {
        return new TokenAuthenticationFilter();
    }

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
//                .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint ).and()
//                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class)

                .authorizeRequests()
//                .antMatchers("public/**").permitAll() << For public JSP pages...
//                .anyRequest().hasRole("admin")
//               .httpBasic().disable();

                .anyRequest().authenticated()
//                .antMatchers("/user/**").permitAll()
                .and().formLogin().successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)

                // From https://github.com/bfwg/springboot-jwt-starter




//                .anyRequest().fullyAuthenticated()
//                .and().httpBasic()
                .and().csrf().disable();

    }
}
