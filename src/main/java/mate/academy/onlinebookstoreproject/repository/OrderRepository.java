package mate.academy.onlinebookstoreproject.repository;

import java.util.List;
import mate.academy.onlinebookstoreproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
    select o
    from Order o
    join fetch o.user
    join fetch o.orderItems
            """)
    List<Order> findAllByUserId(Long id);
}
