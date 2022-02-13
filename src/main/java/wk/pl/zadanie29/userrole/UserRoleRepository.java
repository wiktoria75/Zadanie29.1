package wk.pl.zadanie29.userrole;

import org.springframework.data.jpa.repository.JpaRepository;
import wk.pl.zadanie29.user.User;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    long countByRole(Role role);

    Optional<UserRole> findAllByUserAndRole(User user, Role role);
}
