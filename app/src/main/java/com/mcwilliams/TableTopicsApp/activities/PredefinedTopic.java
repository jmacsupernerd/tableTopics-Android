package com.mcwilliams.TableTopicsApp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.R;
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
 * Created by joshuamcwilliams on 6/13/13.
 */
public class PredefinedTopic extends BaseActivity {

    private SwipeMenuListView lv;
    final ArrayList<String> topics = new ArrayList<String>();
    final DatabaseHandler db = new DatabaseHandler(this);
    private ProgressDialog progressDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        saveTopics(topics);
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(SampleList.THEME); //Used for theme switching in samples
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.manage_data, null, false);
        Drawer.addView(contentView, 0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        Bundle extras = getIntent().getExtras();
        TextView title = (TextView) findViewById(R.id.manageTitle);
        TextView rowToEdit = (TextView) findViewById(R.id.rowToEditText);
        rowToEdit.setVisibility(View.INVISIBLE);

        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());

        lv = (SwipeMenuListView) findViewById(R.id.nameListView);
        if (extras != null) {
            String value = extras.getString("category");
            int category = Integer.parseInt(value);
            Log.d("Int VALUE", value);
            Category cat = db.getCategory(category + 1);
            title.setText(cat.get_category());
        }
        Parse.initialize(this, "zAPsYqzpEh7oWXrlJl6nsHXD8eLVuQwZbRZllEOq", "WYSk2gKvyf8W0DRYgStWhg95LOspC7rhpJSrcECQ");
        ParseAnalytics.trackAppOpened(getIntent());
        ParseQuery<ParseObject> query = ParseQuery.getQuery(title.getText().toString());
        query.whereNotEqualTo("topic", "");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> topicList, ParseException e) {
                if (e == null) {
                    progressDialog.dismiss();
                    Log.d("TOPICS!!", "Retrieved " + topicList.get(0).toString() + " ---");
                    for (ParseObject topic : topicList) {
                        String topicName = topic.getString("topic");
                        Log.d("Single TOPIC", topicName);
                        topics.add(topicName);
                    }
                    Log.d("Topics Array", topics.toString());
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, topics);
                    lv.setAdapter(arrayAdapter);
                    lv.setTextFilterEnabled(true);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getBaseContext(), "To Import topics click import in top right", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void saveTopics(ArrayList<String> topics) {
        for (String topic: topics) {
            db.addTopic(new Topic(topic));
        }
        db.deleteAllCategories();
        Intent goHome = new Intent(getBaseContext(), Home.class);
        startActivity(goHome);
    }
}
