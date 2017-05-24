package application.repository;

import application.entity.Tea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mihnea on 24/05/2017.
 */

@Repository
public interface TeaRepository extends JpaRepository<Tea, Long> {

    Tea findByBarcode(String barcode);
    Tea findByName(String name);
}
