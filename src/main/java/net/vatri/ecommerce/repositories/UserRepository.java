package net.vatri.ecommerce.repositories;

import net.vatri.ecommerce.models.Product;
import net.vatri.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

