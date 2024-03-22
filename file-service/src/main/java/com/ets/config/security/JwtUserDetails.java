package com.ets.config.security;



import com.ets.service.FileService;
import com.ets.utility.JwtTokenManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JwtUserDetails implements UserDetailsService {


    FileService fileService;
    JwtTokenManager jwtTokenManager;


    public JwtUserDetails(@Lazy FileService fileService, JwtTokenManager jwtTokenManager) {

        this.fileService = fileService;
        this.jwtTokenManager = jwtTokenManager;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    public UserDetails getUserByToken(String token){


        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        Optional<String> stateString = jwtTokenManager.getStateFromToken(token);
        List<GrantedAuthority> authorities = role.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


        return User.builder()
                .username(jwtTokenManager.getByIdFromToken(token).toString())
                .password("")
                .accountLocked(false)
                .accountExpired(false)
                .authorities(authorities)
                .build();

    }


}
