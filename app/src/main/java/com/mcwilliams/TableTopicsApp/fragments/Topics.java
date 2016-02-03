package com.mcwilliams.TableTopicsApp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
    private static final String TAG = "Topics";
    public static RecyclerView rvTopics;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private ItemTouchHelper itemTouchHelper;
    private static TopicsRvAdapter topicsRvAdapter;

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
        topicsRvAdapter = new TopicsRvAdapter(TableTopicsApplication.db.getAllTopics());
        rvTopics.setAdapter(topicsRvAdapter);
    }

    public void setupRecyclerView(View view) {
        rvTopics.setHasFixedSize(true);
        rvTopics.setLayoutManager(new LinearLayoutManager(getActivity()));
        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final Topic topic = topicsRvAdapter.getTopic(position);
                TableTopicsApplication.db.deleteTopic(topic);
                refresh();
                Snackbar.make(getActivity().findViewById(R.id.main_content), "Topic deleted", Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.blue)).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TableTopicsApplication.db.addTopic(topic);
                        refresh();
                        Log.d(TAG, "onClick: Added back to UI");
                    }
                }).setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        Log.d(TAG, "onDismissed: Remove from DB");
                    }
                }).show();
            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvTopics);
    }

    public static void reloadData() {
        refresh();

    }

    public static void refresh() {
        topicsRvAdapter.addAllTopics(TableTopicsApplication.db.getAllTopics());
        topicsRvAdapter.notifyDataSetChanged();
    }
}
