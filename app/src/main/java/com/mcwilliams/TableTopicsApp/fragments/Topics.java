package com.mcwilliams.TableTopicsApp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.arrayadapters.TopicsRvAdapter;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;

import java.util.List;

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
public class Topics extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TopicsRvAdapter topicsRvAdapter;
    DatabaseHandler db;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.md_manage_topics, container, false);
        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = new DatabaseHandler(getActivity());
        topicsRvAdapter = new TopicsRvAdapter(getTopicsForList(db));
        mRecyclerView.setAdapter(topicsRvAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
    }

    public void setupRecyclerView(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvContent);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


    }

    public List<Topic> getTopicsForList(DatabaseHandler db) {
        return db.getAllTopics();
    }
}
