package application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Mihnea on 22/05/2017.
 */

@Entity
public class Book {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private long id;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "format", nullable = false)
    private String format;

    @Column(name = "pages", nullable = false)
    private int pages;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "image")
    private String image;

    public Book(String isbn, String title, String author, String genre, String format, int pages, String publisher, int stock, float price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.format = format;
        this.pages = pages;
        this.publisher = publisher;
        this.stock = stock;
        this.price = price;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public int getPages() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }


    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", format='" + format + '\'' +
                ", pages=" + pages +
                ", publisher='" + publisher + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
