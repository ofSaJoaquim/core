package br.com.branetlogistica.core.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JPAUserDetailsService implements UserDetailsService {

  

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
       // final var simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_");
        //ContextData context = Context.getContextData();
        return new User("","",null);
    }

}