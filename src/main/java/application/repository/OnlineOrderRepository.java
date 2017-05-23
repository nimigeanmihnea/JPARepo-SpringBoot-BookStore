package application.repository;

import application.entity.OnlineOrder;
import application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mihnea on 23/05/2017.
 */

@Repository
public interface OnlineOrderRepository extends JpaRepository<OnlineOrder, Long>{

    List<OnlineOrder> findByUser(User user);
}
