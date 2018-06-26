package com.example.sonia.sonia.model;

public class VideoGame {

    private String name;

    private String price;

    private String href;

    private String pictureHref;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPictureHref() {
        return pictureHref;
    }

    public void setPictureHref(String pictureHref) {
        this.pictureHref = pictureHref;
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

        if (name != null ? !name.equals(videoGame.name) : videoGame.name != null) {
            return false;
        }
        if (price != null ? !price.equals(videoGame.price) : videoGame.price != null) {
            return false;
        }
        if (href != null ? !href.equals(videoGame.href) : videoGame.href != null) {
            return false;
        }
        return pictureHref != null ? pictureHref.equals(videoGame.pictureHref) : videoGame.pictureHref == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (href != null ? href.hashCode() : 0);
        result = 31 * result + (pictureHref != null ? pictureHref.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VideoGame{");
        sb.append("name='").append(name).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", href='").append(href).append('\'');
        sb.append(", pictureHref='").append(pictureHref).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
