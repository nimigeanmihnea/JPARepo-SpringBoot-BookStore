package application.validator;

import application.entity.Book;

/**
 * Created by Mihnea on 28/05/2017.
 */

public class BookValidator {

    public BookValidator(){}

    public boolean validate(Book book, int quantity){
        if(book.getStock() - quantity >= 0){
            return true;
        }else return false;
    }
}
