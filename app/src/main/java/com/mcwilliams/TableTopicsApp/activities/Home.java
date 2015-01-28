package com.mcwilliams.TableTopicsApp.activities;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.utils.ShakeEventListener;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;

import java.util.List;
import java.util.Random;
//import android.widget.TextView;

public class Home extends BaseActivity {

    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    private View topLevelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(SampleList.THEME); //Used for theme switching in samples
        super.onCreate(savedInstanceState);
        //Crashlytics.start(this);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.home, null, false);
        Drawer.addView(contentView, 0);

        //TextView homeTest = (TextView) findViewById(R.id.homeText);
        AdView adView = (AdView) this.findViewById(R.id.adView);
        //adRequest.addTestDevice("AEABB4FB3574D973F94737B34850B3F4");
        adView.loadAd(new AdRequest());
        TextView shakeText = (TextView) findViewById(R.id.shakeText);
        getVersion(shakeText);
        final TextView memberName = (TextView) findViewById(R.id.memberName);
        final TextView topicName = (TextView) findViewById(R.id.topicText);
        final DatabaseHandler db = new DatabaseHandler(this);
        final Button generate = (Button) findViewById(R.id.generate);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandomTopicsMemebers(db, memberName, topicName);
            }
        });
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();
        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                generateRandomTopicsMemebers(db, memberName, topicName);
            }
        });

        memberName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(memberName.equals("?")){
                    Toast.makeText(getBaseContext(), "No people exist! Enter some", Toast.LENGTH_SHORT).show();
                }
            }
        });

        topicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


    }

    public void getVersion(TextView shakeText) {
        String version = Integer.toString(Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT <= 16) {
            shakeText.setVisibility(View.INVISIBLE);
            Log.d("-------SDK version", version);
            //Put your logic here, which will be executed
            //only on devices running on ICS/4.0 or later
        } else {
            Log.d("--_SDK greater", version);
            shakeText.setVisibility(View.VISIBLE);
        }
    }

    public void generateRandomTopicsMemebers(DatabaseHandler db, TextView memberName, TextView topicName) {
        final Animation flipin = AnimationUtils.loadAnimation(this, R.anim.shrink_to_middle);
        if (db.getAllMembers().size() > 0 && db.getAllTopics().size() > 0) {
            Log.d("Reading", "Reading all contacts");
            List<Member> members = db.getAllMembers();
            Log.d("Nothing", "Length " + members.size());
            int min = 0;
            //List<Topic> topics = db.getAllTopics();
            if (members.size() > 1) {
                int num_mem = members.size();
                Random r1 = new Random();
                int randomNum1 = r1.nextInt(num_mem);
                String memberPicked = members.get(randomNum1).get_name();
                //String memberPicked = members.get(0).get_name();
                findViewById(R.id.memberName).startAnimation(flipin);
                //AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
                memberName.setText(memberPicked);
            } else {
                memberName.setText(members.get(0).get_name());
            }
            List<Topic> topics = db.getAllTopics();
            if (topics.size() > 1) {
                int num_top = topics.size();
                Random r2 = new Random();
                int randomNum2 = r2.nextInt(num_top) + min;
                String topicPicked = topics.get(randomNum2).get_topic();
                findViewById(R.id.topicText).startAnimation(flipin);
                topicName.setText(topicPicked);
            } else {
                topicName.setText(topics.get(0).get_topic());
            }
        } else {
            AlertDialog.Builder adb = new AlertDialog.Builder(
                    Home.this);
            adb.setTitle("Error");
            adb.setMessage("Please enter some people and topics by clicking the + button");
            adb.setPositiveButton("Ok", null);
            adb.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onStop();
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
            topLevelLayout.setVisibility(View.VISIBLE);
            topLevelLayout.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    topLevelLayout.setVisibility(View.INVISIBLE);
                    return false;
                }
            });
        }
        return ranBefore;
    }
}
