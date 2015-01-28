package com.mcwilliams.TableTopicsApp.model;

/**
 * Created by m439047 on 5/29/13.
 */
public class Member {

    int _id;
    String _name;

    public Member() {
    }

    public Member(String name) {
        this._name = name;
    }

    public Member(int id, String name) {
        this._name = name;
        this._id = id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        this._name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
