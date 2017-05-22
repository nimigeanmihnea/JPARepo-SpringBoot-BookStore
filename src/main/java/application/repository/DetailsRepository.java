package application.repository;

import application.entity.Details;
import application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mihnea on 22/05/2017.
 */

@Repository
public interface DetailsRepository extends JpaRepository<Details, Long>{

    Details findByPnc(String pnc);
    Details findByUser(User user);
    List<Details> findByName(String name);
    Details findByEmail(String email);
    Details findByPhone(String phone);
}
