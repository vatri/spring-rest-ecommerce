package net.vatri.ecommerce.repositories;

import net.vatri.ecommerce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
//    @Query("SELECT o FROM Order o JOIN FETCH o.items WHERE o.id = (:id)")
//    public Order findOneWithItems(@Param("id") Long id);
}
