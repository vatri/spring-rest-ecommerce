package net.vatri.ecommerce.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vatri.ecommerce.cache.Cache;
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
    private Cache cache;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserFromCache(username);
        if( user == null){
            user = userRepository.findByEmail(username);
            cache.setItem("user/"+username, user);
        } else {
            System.out.println("Getting user from cache...");
        }

        if( user == null){
            throw new UsernameNotFoundException("No user found. Username tried: " + username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("admin"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
    /*
    * Get user from cache (Redis)
    * */
    private User getUserFromCache(String username){
        User user = (User) cache.getItem("user/"+username, User.class);
        return user;
    }
}
