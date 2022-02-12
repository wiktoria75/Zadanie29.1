package wk.pl.zadanie29.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wk.pl.zadanie29.userrole.Role;
import wk.pl.zadanie29.userrole.UserRole;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    static final String FIRSTNAME = "firstname";
    static final String LASTNAME = "lastname";
    static final String PASSWORD = "password";

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        HashSet<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, Role.ROLE_USER));
        user.setRoles(userRoles);

        userRepository.save(user);
    }

    public User findCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException() {
            //ignore
        };
    }

    public void updateUser(String change, String fromForm) {
        User currentUser = findCurrentUser();
        switch (change) {
            case FIRSTNAME :
                currentUser.setFirstName(fromForm);
                break;
            case LASTNAME :
                currentUser.setLastName(fromForm);
                break;
            case PASSWORD :
                currentUser.setPassword(passwordEncoder.encode(fromForm));
                break;
            default:
        }
        userRepository.save(currentUser);
    }

    public boolean checkIfCurrentUserIsAnAdmin() {
        List<? extends GrantedAuthority> collect = new ArrayList<>(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return collect.stream().map(GrantedAuthority::getAuthority).anyMatch(s -> s.equals(Role.ROLE_ADMIN.name()));
    }

    private List<User> getAllUsersButCurrent() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(currentUser.getName()))
                .collect(Collectors.toList());
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName() + " " + user.getLastName(),
                user.getRoles().stream().map(UserRole::getRole).anyMatch(r -> r.equals(Role.ROLE_ADMIN))
        );
    }

    public List<UserDto> getAllUsersButCurrentAndChangeToDto() {
        return getAllUsersButCurrent().stream().map(this::toDto).collect(Collectors.toList());
    }
}
