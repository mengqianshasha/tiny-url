package edu.northeastern.tinyurl.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 2411953888026600258L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long UserId;
    private String userName;
    private String email;
    private String passwordHash;

    @OneToMany(mappedBy="user")
    private List<UrlMapping> mappings;

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<UrlMapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<UrlMapping> mappings) {
        this.mappings = mappings;
    }
}
