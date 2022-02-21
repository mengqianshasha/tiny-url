package edu.northeastern.tinyurl.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 2411953888026600258L;

    @Id
    private String email;
    private String password;
    private String userName;

    @OneToMany(mappedBy="user")
    private List<UrlMapping> mappings;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UrlMapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<UrlMapping> mappings) {
        this.mappings = mappings;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
