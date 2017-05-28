package application.validator;

import application.entity.Book;
import application.entity.Tea;

/**
 * Created by Mihnea on 28/05/2017.
 */
public class TeaValidator {

    public TeaValidator(){}

    public boolean validate(Tea tea, int quantity){
        if(tea.getStock() - quantity >= 0){
            return true;
        }else return false;
    }
}
