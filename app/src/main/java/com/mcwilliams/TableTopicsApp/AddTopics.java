package com.mcwilliams.TableTopicsApp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mcwilliams.TableTopicsApp.activities.BaseActivity;
import com.mcwilliams.TableTopicsApp.activities.Home;
import com.mcwilliams.TableTopicsApp.activities.PredefinedTopic;
import com.mcwilliams.TableTopicsApp.model.Category;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.utils.ScrapeTwitter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m439047 on 5/29/13.
 */
public class AddTopics extends BaseActivity {

    JSONObject twitterResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(SampleList.THEME); //Used for theme switching in samples
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.add_member_form, null, false);
        Drawer.addView(contentView, 0);

        TextView inputType = (TextView) findViewById(R.id.input_type);
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button submit = (Button) findViewById(R.id.submit);
        final DatabaseHandler db = new DatabaseHandler(this);
        db.deleteAllCategories();
        new RetreiveTwitter().execute();
        // Log.d("Twitter", twitterResults.toString());
        inputType.setText("Topic");
        submit.setText("Add Topic");
        final AlertDialog.Builder adb = new AlertDialog.Builder(
                AddTopics.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topic = editText.getText().toString();
                Log.d("Starting", "String write to DB");
                db.addTopic(new Topic(topic));
                Toast.makeText(AddTopics.this, topic + " Added", Toast.LENGTH_SHORT).show();
                // AlertDialog.Builder adb = new AlertDialog.Builder(
                //       AddTopics.this);
                adb.setTitle("Add Another Topic?");
                //adb.setMessage("Would you like to add another Topic?\n" );
                //  adb.setNegativeButton("Edit",null);
                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editText.setText("");
                    }
                });
                adb.setNeutralButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent goHome = new Intent(AddTopics.this, Home.class);
                        startActivity(goHome);
                    }
                });
                adb.show();
                Toast.makeText(AddTopics.this, topic + " Added", Toast.LENGTH_SHORT).show();
            }
        });
        Button preDefined = (Button) findViewById(R.id.preDefined);
        preDefined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAllCategories();
                Parse.initialize(getBaseContext(), "zAPsYqzpEh7oWXrlJl6nsHXD8eLVuQwZbRZllEOq", "WYSk2gKvyf8W0DRYgStWhg95LOspC7rhpJSrcECQ");
                ParseAnalytics.trackAppOpened(getIntent());
                final List<String> categories = new ArrayList<String>();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Categories");
                query.whereNotEqualTo("categoryName", "");
                query.findInBackground(new FindCallback<ParseObject>() {

                    public void done(List<ParseObject> catList, ParseException e) {
                        if (e == null) {
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
                                    AddTopics.this);
                            pop.setTitle("Categories");
                            pop.setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item
                                    String pos = Integer.toString(which);
                                    //Toast.makeText(AddTopics.this, pos ,3).show();
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
    }

    class RetreiveTwitter extends AsyncTask<String, Void, JSONObject> {

        private Exception exception;

        protected JSONObject doInBackground(String... urls) {
            try {
                JSONObject results;
                ScrapeTwitter scrapeTwitter = new ScrapeTwitter();
                results = scrapeTwitter.searchTweets("topic");
                Log.d("Results", results.toString());
                return results;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(JSONObject results) {
            // TODO: check this.exception
            // TODO: do something with the feed
            twitterResults = results;
        }
    }
}



