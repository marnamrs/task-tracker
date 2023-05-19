package todo.project.todotracker.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.ALWAYS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //TODO check if autowired can be safely deleted
    //interface to retrieve user information
//    @Autowired
//    private UserDetailsService userDetailsService;

//    //instance of AuthenticationManagerBuilder
    @Autowired
    private AuthenticationManagerBuilder authManagerBuilder;


    //Definition of PasswordEncoder
    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Bean definition for AuthenticationManager
     * @param authenticationConfiguration instance of AuthenticationConfiguration
     * @return instance of the AuthenticationManager
     * @throws Exception if there is an issue getting the instance of the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    //TODO check if custom AuthenticationFilter and AuthorizationFilter are needed

    /**
     * Bean definition for SecurityFilterChain
     * @param http the instance of HttpSecurity
     * @return an instance of the SecurityFilterChain
     * @throws Exception if there is an issue building the SecurityFilterChain
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         //CustomAuthenFilter instance created
        //CustomAuthFilter customAuthenticationFilter = new CustomAuthFilter(authManagerBuilder.getOrBuild());
        // set the URL that the filter should process
        //customAuthenticationFilter.setFilterProcessesUrl("/**");
        // disable Cross Site Request Forgery  protection
        http.csrf().disable();
        // set the session creation policy to stateful
        http.sessionManagement().sessionCreationPolicy(ALWAYS);
        // TODO check is stateless
        // set up authorization for different request matchers and user roles
        http.authorizeHttpRequests((requests) -> requests
                        //.requestMatchers("/**").permitAll()
                        //.requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                        //.requestMatchers("/api/external/**").permitAll()
                // TODO set up auth for rest of paths
                        .anyRequest().permitAll()
        );
        // add the custom authentication filter to the http security object
        //http.addFilter(customAuthenticationFilter);
        // Add the custom authorization filter before the standard authentication filter.
        //http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        // Build the security filter chain to be returned.
        return http.build();
    }


}
