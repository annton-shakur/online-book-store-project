package mate.academy.onlinebookstoreproject.repository;

import java.util.Optional;
import mate.academy.onlinebookstoreproject.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @EntityGraph(attributePaths = {"cartItems", "cartItems.book"})
    Optional<ShoppingCart> findByUserId(Long userId);
}
