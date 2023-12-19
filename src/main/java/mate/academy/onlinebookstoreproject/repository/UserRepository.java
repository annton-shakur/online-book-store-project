package mate.academy.onlinebookstoreproject.repository;

import mate.academy.onlinebookstoreproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
