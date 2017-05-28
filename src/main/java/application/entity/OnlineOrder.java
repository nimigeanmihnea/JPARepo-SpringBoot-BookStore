package application.entity;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToOne
    private Book book;

    @OneToOne
    private Stationery stationery;

    @OneToOne
    private Tea tea;

    public OnlineOrder(){}

    public OnlineOrder(Date date, User user, Book book, Stationery stationery, Tea tea) {
        this.date = date;
        this.user = user;
        this.book = book;
        this.stationery = stationery;
        this.tea = tea;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Stationery getStationery() {
        return stationery;
    }

    public void setStationery(Stationery stationery) {
        this.stationery = stationery;
    }

    public Tea getTea() {
        return tea;
    }

    public void setTea(Tea tea) {
        this.tea = tea;
    }
}
