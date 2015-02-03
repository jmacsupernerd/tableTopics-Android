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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.utils.ShakeEventListener;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.mcwilliams.TableTopicsApp.utils.Utils;
import com.squareup.seismic.ShakeDetector;

import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Home extends BaseActivity implements ShakeDetector.Listener {

    @InjectView(R.id.memberName)
    TextView memberName;
    @InjectView(R.id.topicText)
    TextView topicName;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.home, null, false);
        Drawer.addView(contentView, 0);

        ButterKnife.inject(this);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);

        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
    }

    public void onGenerateClicked(View view) {
        generateRandomTopicsMembers(db, memberName, topicName);
    }

    public void hearShake() {
        generateRandomTopicsMembers(db, memberName, topicName);
    }

    public void generateRandomTopicsMembers(DatabaseHandler db, TextView memberName, TextView topicName) {
        if (db.getAllMembers().size() > 0 && db.getAllTopics().size() > 0) {
            List<Member> members = db.getAllMembers();
            if (members.size() > 1) {
                String memberPicked = members.get(Utils.randInt(members.size())).get_name();
                YoYo.with(Techniques.FlipInX).duration(700).playOn(findViewById(R.id.memberName));
                memberName.setText(memberPicked);
            } else {
                memberName.setText(members.get(0).get_name());
            }
            List<Topic> topics = db.getAllTopics();
            if (topics.size() > 1) {
                String topicPicked = topics.get(Utils.randInt(topics.size())).get_topic();
                YoYo.with(Techniques.FlipInX).duration(700).playOn(findViewById(R.id.topicText));
                topicName.setText(topicPicked);
            } else {
                topicName.setText(topics.get(0).get_topic());
            }
        } else {
            AlertDialog.Builder adb = new AlertDialog.Builder(Home.this);
            adb.setTitle("Error").setMessage("Please enter some people and topics").setPositiveButton("Ok", null);
            adb.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onStop();
    }

}
