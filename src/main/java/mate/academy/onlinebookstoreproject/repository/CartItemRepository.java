package mate.academy.onlinebookstoreproject.repository;

import mate.academy.onlinebookstoreproject.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
