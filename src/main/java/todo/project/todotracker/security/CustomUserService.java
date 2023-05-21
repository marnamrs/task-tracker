package todo.project.todotracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import todo.project.todotracker.repositories.UserRepository;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            MyUserPrincipal u = new MyUserPrincipal(userRepository.findByUsername(username).get());
            UserDetails user = User.withUsername(u.getUsername()).password(u.getPassword()).authorities("USER").build();
            return user;
        } catch(Exception e) {
            throw new UsernameNotFoundException(username);
        }
    }
}
