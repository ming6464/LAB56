package com.fpoly.mob403_lab56.DTO;

public class Picture {
    private String _id;
    private String name;
    private String url;

    @Override
    public String toString() {
        return "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url;
    }

    public Picture(String _id, String name, String url) {
        this._id = _id;
        this.name = name;
        this.url = url;
    }

    public Picture() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
