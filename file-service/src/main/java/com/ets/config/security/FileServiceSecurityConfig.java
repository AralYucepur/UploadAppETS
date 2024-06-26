package com.ets.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FileServiceSecurityConfig {

    @Bean
    JwtTokenFilter getJwtTokenFilter(){
        return new JwtTokenFilter();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{

        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/v3/api-docs/**","/swagger-ui/**","/v1/dev/file/upload","/v1/dev/file/list"
                        ,"/v1/dev/file/find_file","/v1/dev/file/delete_file","/v1/dev/file/find_byte_file")
                .permitAll()
                .anyRequest()
                .authenticated();

        httpSecurity.addFilterBefore(getJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();

    }
}
