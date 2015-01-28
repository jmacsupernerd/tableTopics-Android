package com.mcwilliams.TableTopicsApp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mcwilliams.TableTopicsApp.model.Category;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m439047 on 5/29/13.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TableTopics";

    // Contacts table name
    private static final String TABLE_MEMBERS = "members";
    private static final String TABLE_TOPICS = "topics";
    private static final String TABLE_CATEGORIES = "categories";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TOPIC = "topic";
    private static final String KEY_CATEGORY = "category";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + TABLE_MEMBERS + " ( " + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT);";
        Log.d("SQL String", CREATE_MEMBERS_TABLE);
        String CREATE_TOPICS_TABLE = "CREATE TABLE " + TABLE_TOPICS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TOPIC + " TEXT)";
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY + " TEXT)";
        db.execSQL(CREATE_MEMBERS_TABLE);
        db.execSQL(CREATE_TOPICS_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    // Adding new contact
    public void addMember(Member member) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, member.get_name()); // Contact Name
        System.out.print("Adding Member" + member.get_name());
        // Inserting Row
        db.insert(TABLE_MEMBERS, null, values);
        db.close(); // Closing database connection
    }

    public void addTopic(Topic topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TOPIC, topic.get_topic()); // Contact Name
        System.out.print("Adding Topic" + topic.get_topic());
        // Inserting Row
        db.insert(TABLE_TOPICS, null, values);
        db.close(); // Closing database connection
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, category.get_category()); // Contact Name
        System.out.print("Adding Category" + category.get_category());
        // Inserting Row
        db.insert(TABLE_CATEGORIES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Member getMember(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEMBERS, new String[]{KEY_ID,
                KEY_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Member member = new Member(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return member;
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIES, new String[]{KEY_ID,
                KEY_CATEGORY}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Category category = new Category(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return category;
    }

    // Getting All Contacts
    public List<Member> getAllMembers() {
        List<Member> membersList = new ArrayList<Member>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEMBERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Member member = new Member();
                member.set_id(Integer.parseInt(cursor.getString(0)));
                member.set_name(cursor.getString(1));
                membersList.add(member);
            } while (cursor.moveToNext());
        }
        // return contact list
        return membersList;
    }

    public List<Topic> getAllTopics() {
        List<Topic> topicsList = new ArrayList<Topic>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TOPICS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Topic topic = new Topic();
                topic.set_id(Integer.parseInt(cursor.getString(0)));
                topic.set_topic(cursor.getString(1));
                //contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                topicsList.add(topic);
            } while (cursor.moveToNext());
        }
        // return contact list
        return topicsList;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.set_id(Integer.parseInt(cursor.getString(0)));
                category.set_category(cursor.getString(1));
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        // return contact list
        return categoryList;
    }

    // Updating single contact
    public int updateTopic(Topic topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TOPIC, topic.get_topic());
        return db.update(TABLE_TOPICS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(topic.get_id()) });
    }

    public int updateMember(Member member) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, member.get_name());
        return db.update(TABLE_MEMBERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(member.get_id()) });
    }

    // Deleting single contact
    public void deleteMember(Member member) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMBERS, KEY_NAME + " = ?",
                new String[]{String.valueOf(member.get_name())});
        db.close();
    }

    public void deleteTopic(Topic topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOPICS, KEY_TOPIC + " = ?",
                new String[]{String.valueOf(topic.get_topic())});
        db.close();
    }

    public void deleteAllTopics() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOPICS, null, null);
        db.close();
    }

    public void deleteAllPeople() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMBERS, null, null);
        db.close();
    }

    public void deleteAllCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIES, null, null);
        db.close();
    }


   /* // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }*/
}
