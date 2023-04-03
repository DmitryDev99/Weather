package com.example.weather.catApi.data.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class CatResponse {

    private String id;
    private String url;
    private int width;
    private int height;

    public CatResponse() {}

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + height;
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        CatResponse newObj = (CatResponse) obj;
        return this.width == newObj.width &&
                this.height == newObj.height &&
                Objects.equals(this.url, newObj.url) &&
                Objects.equals(this.id, newObj.id);
    }

    @NonNull
    @Override
    public String toString() {
        return "CatRequest(" +
                "id = " + id + ", " +
                "url = " + url + ", " +
                "height = " + height + ", " +
                "width = " + width + "" +
                ")";
    }
}

