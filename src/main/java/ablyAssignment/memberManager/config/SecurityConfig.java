package ablyAssignment.memberManager.config;

import ablyAssignment.memberManager.jwt.JwtAuthenticationFilter;
import ablyAssignment.memberManager.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .mvcMatchers("/h2-console/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/login/**","/api/sign-up").permitAll()
                .mvcMatchers(HttpMethod.GET,"/api/password/token","/api/sign-up/token","/api/token/reissued").permitAll()
                .mvcMatchers(HttpMethod.PUT,"/api/password").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .disable()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/h2-console/**");
    }
}
