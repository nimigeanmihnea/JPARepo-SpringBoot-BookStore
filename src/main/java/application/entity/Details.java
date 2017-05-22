package application.entity;

import javax.persistence.*;

/**
 * Created by Mihnea on 22/05/2017.
 */

@Entity
public class Details {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private long id;

    @Column(name = "pnc", unique = true, nullable = false)
    private String pnc;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToOne
    private User user;

    public Details(String pnc, String name, String email, String phone, String address, User user) {
        this.pnc = pnc;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.user = user;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getPnc() {
        return pnc;
    }
    public void setPnc(String pnc) {
        this.pnc = pnc;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Details{" +
                "pnc='" + pnc + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
