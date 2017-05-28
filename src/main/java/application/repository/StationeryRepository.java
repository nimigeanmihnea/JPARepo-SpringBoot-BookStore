package application.repository;

import application.entity.Stationery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mihnea on 24/05/2017.
 */

@Repository
public interface StationeryRepository extends JpaRepository<Stationery, Long> {

    List<Stationery> findByBarcodeOrNameOrType(String barcode, String name, String type);
    Stationery findByName(String name);
}
