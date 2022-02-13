package wk.pl.zadanie29.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wk.pl.zadanie29.user.User;
import wk.pl.zadanie29.user.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Set<SimpleGrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()))
                    .collect(Collectors.toSet());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
        } else {
            throw new UsernameNotFoundException("Użytkownik" + username + "nie znaleziony.");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
