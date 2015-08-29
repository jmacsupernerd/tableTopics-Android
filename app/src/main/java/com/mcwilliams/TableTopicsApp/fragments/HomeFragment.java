package com.mcwilliams.TableTopicsApp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.TableTopicsApplication;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by joshuamcwilliams on 7/2/15.
 */
public class HomeFragment extends Fragment {
    @Bind(R.id.memberName) TextView memberName;
    @Bind(R.id.topicText) TextView topicName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.generate)
    public void onClick() {
        generateRandomTopicsMembers(TableTopicsApplication.db, memberName, topicName);
    }

    public void generateRandomTopicsMembers(DatabaseHandler db, TextView memberName, TextView topicName) {
        if (db.getAllMembers().size() > 0 && db.getAllTopics().size() > 0) {
            if (db.getAllMembers().size() > 1) {
                memberName.setText(db.getAllMembers().get(Utils.randInt(db.getAllMembers().size())).get_name());
            } else {
                memberName.setText(db.getAllMembers().get(0).get_name());
            }

            if (db.getAllTopics().size() > 1) {
                topicName.setText(db.getAllTopics().get(Utils.randInt(db.getAllTopics().size())).get_topic());
            } else {
                topicName.setText(db.getAllTopics().get(0).get_topic());
            }
        } else {
            AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
            adb.setTitle("Error").setMessage("Please enter some people and topics").setPositiveButton("Ok", null);
            adb.show();
        }
    }
}
