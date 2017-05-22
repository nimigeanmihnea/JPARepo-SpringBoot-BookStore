package application.repository;

import application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mihnea on 22/05/2017.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);
    List<User> findByRole(String role);
}

