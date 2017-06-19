package net.vatri.ecommerce.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vatri.ecommerce.models.User;
import net.vatri.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Jedis redis;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);

        if( user == null){
            throw new UsernameNotFoundException("No user found. Username tried: " + s);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("admin"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
    /*
    * Get user from Redis
    * */
    private User getUserFromCache(String username){

        String userJson = redis.get("user/"+username);
        User user = null;
        try{
            user = objectMapper.readValue(userJson, User.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return user;
    }
}
