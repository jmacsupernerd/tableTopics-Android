package com.mcwilliams.TableTopicsApp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.utils.Utils;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView memberName;
    TextView topicName;
    DatabaseHandler db;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHandler(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button generate = (Button) view.findViewById(R.id.generate);
        generate.setOnClickListener(this);
        memberName = (TextView) view.findViewById(R.id.memberName);
        topicName = (TextView) view.findViewById(R.id.topicText);

    }

    @Override
    public void onResume() {
        super.onResume();


        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        generateRandomTopicsMembers(db, memberName, topicName);
    }

    public void generateRandomTopicsMembers(DatabaseHandler db, TextView memberName, TextView topicName) {
        if (db.getAllMembers().size() > 0 && db.getAllTopics().size() > 0) {
            List<Member> members = db.getAllMembers();
            if (members.size() > 1) {
                String memberPicked = members.get(Utils.randInt(members.size())).get_name();
                YoYo.with(Techniques.Wobble).duration(700).playOn(memberName);
                memberName.setText(memberPicked);
            } else {
                memberName.setText(members.get(0).get_name());
            }
            List<Topic> topics = db.getAllTopics();
            if (topics.size() > 1) {
                String topicPicked = topics.get(Utils.randInt(topics.size())).get_topic();
                YoYo.with(Techniques.Wobble).duration(700).playOn(topicName);
                topicName.setText(topicPicked);
            } else {
                topicName.setText(topics.get(0).get_topic());
            }
        } else {
            AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
            adb.setTitle("Error").setMessage("Please enter some people and topics").setPositiveButton("Ok", null);
            adb.show();
        }
    }
}
