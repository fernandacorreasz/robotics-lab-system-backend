package robotic.system.infra.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/roles/create").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/components").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/subcategories").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/tests").permitAll()
                        //.requestMatchers("/api/categories/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/categories/all**").permitAll()
                        .requestMatchers("/api/v1/components/all**").permitAll()
                        .requestMatchers("/api/v1/components/bulk-upload**").permitAll()
                        .requestMatchers("/api/v1/components/bulk-delete**").permitAll()
                        .requestMatchers("/api/v1/components/filter**").permitAll()
                        .requestMatchers("/api/v1/components/**").permitAll()
                        .requestMatchers("/api/v1/tests**").permitAll()
                        .requestMatchers("/api/v1/categories/with-subcategories**").permitAll()
                        .requestMatchers("/api/v1/components/sub-category/**").permitAll()
                        .requestMatchers("/api/v1/categories/**").permitAll()
                        .requestMatchers("/api/v1/subcategories**").permitAll()
                        .requestMatchers("/api/v1/subcategories/**").permitAll()
                        .requestMatchers("/api/v1/activityphotos/**").permitAll()
                        .requestMatchers("/api/v1/subcategories/all**").permitAll()
                        .requestMatchers("/api/v1/activities**").permitAll()
                        .requestMatchers("/api/v1/activities/**").permitAll()
                        .requestMatchers("/api/v1/forum/**").permitAll()
                        .requestMatchers("/api/v1/comments/activity/**").permitAll()
                        .requestMatchers("/api/v1/loans/**").permitAll()
                        .requestMatchers("/api/v1/user/**", "/list/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui/index.html/").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
