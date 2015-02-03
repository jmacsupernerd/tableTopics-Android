package com.mcwilliams.TableTopicsApp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.ads.AdView;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.arrayadapters.TopicArrayAdapter;
import com.mcwilliams.TableTopicsApp.utils.Utils;
import com.mcwilliams.TableTopicsApp.model.Category;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuamcwilliams on 5/29/13.
 */
public class ManageTopics extends BaseActivity implements SwipeMenuListView.OnMenuItemClickListener {

    private SwipeMenuListView lv;
    final DatabaseHandler db = new DatabaseHandler(this);
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.manage_data, null, false);
        Drawer.addView(contentView, 0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");

        TextView title = (TextView) findViewById(R.id.manageTitle);
        title.setText("Delete Topics");
        title.setVisibility(View.GONE);

        TextView rowToEdit = (TextView) findViewById(R.id.rowToEditText);
        rowToEdit.setText("Swipe left on row to view options");

        lv = (SwipeMenuListView) findViewById(R.id.nameListView);
        lv.setMenuCreator(creator);
        lv.setOnMenuItemClickListener(this);
        lv.setAdapter(getTopicsForList(db));
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "Swipe left on row", Toast.LENGTH_SHORT).show();
            }
        });

        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        addTopic();
        return super.onOptionsItemSelected(item);
    }

    public void deleteAllTopics() {
        db.deleteAllTopics();
        Toast.makeText(getBaseContext(), "All Topics Deleted", Toast.LENGTH_SHORT).show();
        Intent goHome = new Intent(getBaseContext(), Home.class);
        startActivity(goHome);
    }

    public void addTopic() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add Topic");

        final View inputView = getLayoutInflater().inflate(R.layout.input_dialog, null);

        alert.setView(inputView);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                EditText getInput = (EditText) inputView.findViewById(R.id.inputText);
                String topic = getInput.getText().toString();
                Log.d("Starting", "String write to DB");
                db.addTopic(new Topic(topic));
                lv.setAdapter(getTopicsForList(db));
                lv.setTextFilterEnabled(true);
            }
        });
        alert.setNeutralButton("Predefined Topics", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.show();
                db.deleteAllCategories();
                Parse.initialize(getBaseContext(), "zAPsYqzpEh7oWXrlJl6nsHXD8eLVuQwZbRZllEOq", "WYSk2gKvyf8W0DRYgStWhg95LOspC7rhpJSrcECQ");
                ParseAnalytics.trackAppOpened(getIntent());
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Categories");
                query.whereNotEqualTo("categoryName", "");
                query.findInBackground(new FindCallback<ParseObject>() {

                    public void done(List<ParseObject> catList, ParseException e) {
                        if (e == null) {
                            progressDialog.dismiss();
                            Log.d("Categories", "Retrieved " + catList.get(0).toString() + " ---");
                            System.out.print("Retrieved " + catList.size() + " ---");
                            for (ParseObject category : catList) {
                                String catName = category.getString("categoryName");
                                db.addCategory(new Category(catName));
                                Log.d("Single Category!!", catName);
                            }
                            List<Category> categories = db.getAllCategories();
                            List<String> firstColumn = new ArrayList<String>();
                            for (int i = 0; i < categories.size(); i++) {
                                firstColumn.add(categories.get(i).get_category());
                            }
                            final CharSequence[] items = firstColumn.toArray(new CharSequence[firstColumn.size()]);
                            Log.d("CHAR SEQUENCE", items.toString());
                            AlertDialog.Builder pop = new AlertDialog.Builder(
                                    ManageTopics.this);
                            pop.setTitle("Categories");
                            pop.setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String pos = Integer.toString(which);
                                    Intent PreTopic = new Intent(getBaseContext(), PredefinedTopic.class);
                                    PreTopic.putExtra("category", pos);
                                    startActivity(PreTopic);
                                }
                            });
                            pop.show();
                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }
                    }
                });
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public TopicArrayAdapter getTopicsForList(DatabaseHandler db) {
        List<Topic> topics = db.getAllTopics();
        return new TopicArrayAdapter(this, R.layout.lv_row_list_item, topics);
    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem openItem = new SwipeMenuItem(
                    getBaseContext());
            openItem.setBackground(R.color.blue);
            openItem.setWidth(Utils.dpToPx(90, getBaseContext()));
            openItem.setIcon(android.R.drawable.ic_menu_edit);
            menu.addMenuItem(openItem);

            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getBaseContext());
            deleteItem.setBackground(R.color.blue);
            deleteItem.setWidth(Utils.dpToPx(90, getBaseContext()));
            deleteItem.setIcon(android.R.drawable.ic_menu_delete);
            menu.addMenuItem(deleteItem);
        }
    };

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        View view;
        switch (index) {
            case 0:
                // edit
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Edit Topic");
                View inputView = getLayoutInflater().inflate(R.layout.input_dialog, null);
                final EditText getInput = (EditText) inputView.findViewById(R.id.inputText);

                final Topic editTopic = (Topic) lv.getItemAtPosition(position);
                getInput.setText(editTopic.get_topic());
                alert.setView(inputView);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        editTopic.set_topic(getInput.getText().toString());
                        db.updateTopic(editTopic);
                        lv.setAdapter(getTopicsForList(db));
                        lv.setTextFilterEnabled(true);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
                break;
            case 1:
                // delete
                Topic topic = (Topic) lv.getItemAtPosition(position);
                db.deleteTopic(topic);
                lv.setAdapter(getTopicsForList(db));
                lv.setTextFilterEnabled(true);
                break;
        }
        return false;
    }
}
