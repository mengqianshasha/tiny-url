package edu.northeastern.tinyurl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "urlmappings")
public class UrlMapping implements Serializable {
    private static final long serialVersionUID = 8370782340476794025L;

    public UrlMapping(){
    }

    public UrlMapping(String shortUrl, String originalUrl){
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
    }

    @Id
    private String shortUrl;
    private String originalUrl;

    private String domainName;

    private Date createDate;
    private Date expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getNormalizedShortUrl(){
        return String.join("/", this.domainName, this.shortUrl);
    }
}
