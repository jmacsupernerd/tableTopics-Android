package com.mcwilliams.TableTopicsApp.mdactivitiess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.activities.PredefinedTopic;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.arrayadapters.TopicArrayAdapter;
import com.mcwilliams.TableTopicsApp.model.Category;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.utils.Utils;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuamcwilliams on 6/27/15.
 */
public class ManageTopics extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PeopleRVAdapter peopleRVAdapter;
    final DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_manage_data);

        Utils.setupToolbar(this);
        mDrawerLayout = Utils.setupDrawer(this, mDrawerLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvContent);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
//        peopleRVAdapter = new PeopleRVAdapter(getTopicsForList(db), "topic");
        mRecyclerView.setAdapter(peopleRVAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Topic> getTopicsForList(DatabaseHandler db) {
        List<Topic> topics = db.getAllTopics();
        return topics;
    }

//    public void addTopic() {
//        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Add Topic");
//
//        final View inputView = getLayoutInflater().inflate(R.layout.input_dialog, null);
//        final EditText getInput = (EditText) inputView.findViewById(R.id.inputText);
//        alert.setView(inputView);
//        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//
//                String topic = getInput.getText().toString();
//                Log.d("Starting", "String write to DB");
//                db.addTopic(new Topic(topic));
//                lv.setAdapter(getTopicsForList(db));
//                lv.setTextFilterEnabled(true);
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
//            }
//        });
//        alert.setNeutralButton("Predefined Topics", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                progressDialog.show();
//                db.deleteAllCategories();
//                Parse.initialize(getBaseContext(), "zAPsYqzpEh7oWXrlJl6nsHXD8eLVuQwZbRZllEOq", "WYSk2gKvyf8W0DRYgStWhg95LOspC7rhpJSrcECQ");
//                ParseAnalytics.trackAppOpened(getIntent());
//                ParseQuery<ParseObject> query = ParseQuery.getQuery("Categories");
//                query.whereNotEqualTo("categoryName", "");
//                query.findInBackground(new FindCallback<ParseObject>() {
//
//                    public void done(List<ParseObject> catList, ParseException e) {
//                        if (e == null) {
//                            progressDialog.dismiss();
//                            Log.d("Categories", "Retrieved " + catList.get(0).toString() + " ---");
//                            System.out.print("Retrieved " + catList.size() + " ---");
//                            for (ParseObject category : catList) {
//                                String catName = category.getString("categoryName");
//                                db.addCategory(new Category(catName));
//                                Log.d("Single Category!!", catName);
//                            }
//                            List<Category> categories = db.getAllCategories();
//                            List<String> firstColumn = new ArrayList<String>();
//                            for (int i = 0; i < categories.size(); i++) {
//                                firstColumn.add(categories.get(i).get_category());
//                            }
//                            final CharSequence[] items = firstColumn.toArray(new CharSequence[firstColumn.size()]);
//                            Log.d("CHAR SEQUENCE", items.toString());
//                            AlertDialog.Builder pop = new AlertDialog.Builder(
//                                    ManageTopics.this);
//                            pop.setTitle("Categories");
//                            pop.setItems(items, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    String pos = Integer.toString(which);
//                                    Intent PreTopic = new Intent(getBaseContext(), PredefinedTopic.class);
//                                    PreTopic.putExtra("category", pos);
//                                    startActivity(PreTopic);
//                                }
//                            });
//                            pop.show();
//                        } else {
//                            Log.d("score", "Error: " + e.getMessage());
//                        }
//                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
//                    }
//                });
//            }
//        });
//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                // Canceled.
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
//            }
//        });
//
//        AlertDialog dialog = alert.create();
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//
//            @Override
//            public void onShow(DialogInterface dialog) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(getInput, InputMethodManager.SHOW_FORCED);
//            }
//        });
//
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
//
//        dialog.show();
//    }

}
