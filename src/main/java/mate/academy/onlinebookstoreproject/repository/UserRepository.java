package mate.academy.onlinebookstoreproject.repository;

import java.util.Optional;
import mate.academy.onlinebookstoreproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
