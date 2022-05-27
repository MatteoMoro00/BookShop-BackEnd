package com.digitazon.bookShop.security;

import com.digitazon.bookShop.repositories.UserRepository;
import com.digitazon.bookShop.security.auth.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " doesn't exists")));
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(
                (request, response, ex) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                }
        ).and();

        http.cors().and().authorizeRequests()
                .antMatchers("/users/login", "/books/admin/**")
                .permitAll().antMatchers("/books/**", "/genres/**", "/authors/**", "/publishers/**", "/users", "/wishlist/**").permitAll().anyRequest().authenticated();
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        /*http.cors().and().authorizeRequests()
                .antMatchers("/books", "/books/search", "/genres/**", "/authors/**", "/publishers/**", "/users", "/users/login").permitAll();
        http.cors().and().authorizeRequests()
                .antMatchers("/wishlist").hasAnyAuthority("ROLE_USER");
        http.cors().and().authorizeRequests()
                .antMatchers(DELETE,"/books/admin").hasAnyAuthority("ROLE_ADMIN");
        http.cors().and().authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);*/

        /*http.cors().and().csrf().disable().authorizeRequests().antMatchers(GET, "/books/**").permitAll()
                                                              .antMatchers(GET, "/genres/**").permitAll()
                .antMatchers(GET, "/authors/**").permitAll()
                .antMatchers(GET, "/publishers/**").permitAll()
                .antMatchers(POST, "/users/login").permitAll()

                .antMatchers("/books/admin").hasRole(UserRole.ADMIN_ROLE)  //.hasAnyAuthority("ADMIN_ROLE")

                .anyRequest().authenticated();
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);*/
    }
}
