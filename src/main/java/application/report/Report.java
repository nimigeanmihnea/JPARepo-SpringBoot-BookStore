package application.report;

import application.repository.OnlineOrderRepository;
import application.repository.SaleRepository;
import application.repository.UserRepository;

/**
 * Created by Mihnea on 22/05/2017.
 */

public interface Report {
    void generate(String username, UserRepository userRepository, OnlineOrderRepository onlineOrderRepository, SaleRepository saleRepository);
}
