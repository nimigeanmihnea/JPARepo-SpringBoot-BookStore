package application.repository;

import application.entity.Tea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mihnea on 24/05/2017.
 */

@Repository
public interface TeaRepository extends JpaRepository<Tea, Long> {

    List<Tea> findByBarcodeOrNameOrType(String barcode, String name, String type);
    Tea findByName(String name);
}
