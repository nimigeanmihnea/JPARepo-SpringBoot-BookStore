package application.validator;

import application.entity.Stationery;

/**
 * Created by Mihnea on 28/05/2017.
 */
public class StationeryValidator {

    public StationeryValidator(){}

    public boolean validate(Stationery stationery, int quantity){
        if(stationery.getStock() - quantity >= 0){
            return true;
        }else return false;
    }
}
