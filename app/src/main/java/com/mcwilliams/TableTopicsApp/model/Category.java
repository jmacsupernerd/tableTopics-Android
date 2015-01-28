package com.mcwilliams.TableTopicsApp.model;

/**
 * Created by m439047 on 5/29/13.
 */
public class Category {

    int _id;
    String _category;

    public Category() {
    }

    public Category(String category) {
        this._category = category;
    }

    public Category(int id, String category) {
        this._category = category;
        this._id = id;
    }

    public String get_category() {
        return _category;
    }

    public void set_category(String _category) {
        this._category = _category;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
