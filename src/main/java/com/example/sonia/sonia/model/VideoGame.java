package com.example.sonia.sonia.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game")
public class VideoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "href")
    private String href;

    @Column(name = "img")
    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VideoGame videoGame = (VideoGame) o;
        return Objects.equals(name, videoGame.name) &&
               Objects.equals(price, videoGame.price) &&
               Objects.equals(img, videoGame.img);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, price, img);
    }

    @Override
    public String toString() {
        return "VideoGame{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", href='" + href + '\'' +
               ", img='" + img + '\'' +
               '}';
    }
}
