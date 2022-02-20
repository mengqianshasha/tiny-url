package edu.northeastern.tinyurl.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "urlmappingId")
public class UrlMappingId implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public UrlMappingId(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
