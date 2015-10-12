package com.mcwilliams.TableTopicsApp.model;

/**
 * Created by m439047 on 5/29/13.
 */
public class Topic {

    int _id;
    String _topic;

    public Topic() {
    }
    public Topic(String topic) {
        this._topic = topic;
    }

    public Topic(int id, String topic) {
        this._topic = topic;
        this._id = id;
    }

    public String get_topic() {
        return _topic;
    }

    public void set_topic(String _topic) {
        this._topic = _topic;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
