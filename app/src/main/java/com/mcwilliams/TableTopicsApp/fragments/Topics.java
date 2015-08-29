package com.mcwilliams.TableTopicsApp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.TableTopicsApplication;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.arrayadapters.TopicsRvAdapter;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
public class Topics extends Fragment{
    public static RecyclerView rvTopics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.md_manage_topics, container, false);
        rvTopics = (RecyclerView) view.findViewById(R.id.rvContent);
        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvTopics.setAdapter(new TopicsRvAdapter(TableTopicsApplication.db.getAllTopics()));
    }

    public void setupRecyclerView(View view) {
        rvTopics.setHasFixedSize(true);
        rvTopics.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static void reloadData() {
        rvTopics.setAdapter(new TopicsRvAdapter(TableTopicsApplication.db.getAllTopics()));
    }
}
