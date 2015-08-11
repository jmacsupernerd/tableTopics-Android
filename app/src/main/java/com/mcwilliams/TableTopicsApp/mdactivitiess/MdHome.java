package com.mcwilliams.TableTopicsApp.mdactivitiess;

import android.app.AlertDialog;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.utils.Utils;
import com.squareup.seismic.ShakeDetector;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by joshuamcwilliams on 6/26/15.
 */
public class MdHome extends AppCompatActivity implements ShakeDetector.Listener {

    @InjectView(R.id.memberName)
    TextView memberName;
    @InjectView(R.id.topicText)
    TextView topicName;
    DatabaseHandler db = new DatabaseHandler(this);
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_home);

        Utils.setupToolbar(this);
        mDrawerLayout = Utils.setupDrawer(this, mDrawerLayout);

        ButterKnife.inject(this);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);

        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
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
                YoYo.with(Techniques.Wobble).duration(700).playOn(findViewById(R.id.memberName));
                memberName.setText(memberPicked);
            } else {
                memberName.setText(members.get(0).get_name());
            }
            List<Topic> topics = db.getAllTopics();
            if (topics.size() > 1) {
                String topicPicked = topics.get(Utils.randInt(topics.size())).get_topic();
                YoYo.with(Techniques.Wobble).duration(700).playOn(findViewById(R.id.topicText));
                topicName.setText(topicPicked);
            } else {
                topicName.setText(topics.get(0).get_topic());
            }
        } else {
            AlertDialog.Builder adb = new AlertDialog.Builder(MdHome.this);
            adb.setTitle("Error").setMessage("Please enter some people and topics").setPositiveButton("Ok", null);
            adb.show();
        }
    }

}
