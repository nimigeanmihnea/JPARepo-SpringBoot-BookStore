package application.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Mihnea on 23/05/2017.
 */

@Entity
public class OnlineOrder {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @OneToOne
    private User user;

    @OneToMany
    private List<Book> books;

    public OnlineOrder(Date date, User user, List<Book> books) {
        this.date = date;
        this.user = user;
        this.books = books;
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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
