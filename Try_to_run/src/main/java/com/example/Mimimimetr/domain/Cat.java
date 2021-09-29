package com.example.Mimimimetr.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Cat implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String filename;
    private int likeAmount;

    public Cat(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }

    public Cat() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getFilename() {return filename;}
    public void setFilename(String filename) {this.filename = filename;}

    public int getLikeAmount() {return likeAmount;}
    public void increaseLikeAmount(int likeAmount) {
        this.likeAmount += likeAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(name, cat.name) && Objects.equals(filename, cat.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, filename);
    }

    @Override
    public int compareTo(Object o) {
        return 1;
    }
}
