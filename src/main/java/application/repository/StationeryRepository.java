package application.repository;

import application.entity.Stationery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mihnea on 24/05/2017.
 */

@Repository
public interface StationeryRepository extends JpaRepository<Stationery, Long> {

    Stationery findByBarcode(String barcode);
    Stationery findByName(String name);
}
