package server.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<FirebaseAuthFilter> firebaseAuthFilter() {
        FilterRegistrationBean<FirebaseAuthFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new FirebaseAuthFilter());
        reg.addUrlPatterns("/*"); // תופס הכל
        reg.setOrder(1);
        return reg;
    }
}
