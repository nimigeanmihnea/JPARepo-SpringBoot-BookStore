package application.repository;

import application.entity.Sale;
import application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Mihnea on 22/05/2017.
 */

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByDate(Date date);
    List<Sale> findBySaledBy(User user);
    List<Sale> findBySaledTo(User user);

}
