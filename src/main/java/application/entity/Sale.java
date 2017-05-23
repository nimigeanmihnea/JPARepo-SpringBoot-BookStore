package application.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Mihnea on 22/05/2017.
 */

@Entity
public class Sale {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany
    private List<Book> books;

    @OneToOne
    private User saledBy;

    @OneToOne
    private User saledTo;

    public Sale(Date date, List<Book> books, User saledBy, User saledTo) {
        this.date = date;
        this.books = books;
        this.saledBy = saledBy;
        this.saledTo = saledTo;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public User getSaledBy() {
        return saledBy;
    }
    public void setSaledBy(User saledBy) {
        this.saledBy = saledBy;
    }
    public User getSaledTo() {
        return saledTo;
    }
    public void setSaledTo(User saledTo) {
        this.saledTo = saledTo;
    }
}
