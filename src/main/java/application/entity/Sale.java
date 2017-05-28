package application.entity;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToOne
    private User saledBy;

    @OneToOne
    private User saledTo;

    @OneToOne
    private Book book;

    @OneToOne
    private Stationery stationery;

    @OneToOne
    private Tea tea;

    public Sale() {}

    public Sale(Date date, User saledBy, User saledTo, Book book, Stationery stationery, Tea tea) {
        this.date = date;
        this.saledBy = saledBy;
        this.saledTo = saledTo;
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
