package wk.pl.zadanie29.userrole;

import org.springframework.stereotype.Service;
import wk.pl.zadanie29.user.User;
import wk.pl.zadanie29.user.UserRepository;

import java.util.Optional;

@Service
public class UserRoleService {
    
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public UserRoleService(UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    public void addAdmin(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        userOptional.ifPresent(user -> userRoleRepository.save(new UserRole(user, Role.ROLE_ADMIN)));
    }

    public boolean removeAdminRole(Long id) {
        long adminsCount = userRoleRepository.countByRole(Role.ROLE_ADMIN);
        if (adminsCount > 1) {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                Optional<UserRole> userRoleOptional = userRoleRepository.findAllByUserAndRole(optionalUser.get(), Role.ROLE_ADMIN);
                userRoleOptional.ifPresent(userRoleRepository::delete);
            }
            return true;
        }
        return false;
    }
}
