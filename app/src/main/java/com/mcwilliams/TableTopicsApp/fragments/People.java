package com.mcwilliams.TableTopicsApp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.TableTopicsApplication;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.arrayadapters.TopicsRvAdapter;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
public class People extends Fragment {
    private static final String TAG = "People";
    public static RecyclerView peopleRV;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private ItemTouchHelper itemTouchHelper;
    private static List<Member> memberList = new ArrayList<>();
    private static PeopleRVAdapter peopleRVAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.md_manage_topics, container, false);
        peopleRV = (RecyclerView) view.findViewById(R.id.rvContent);
        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        memberList = TableTopicsApplication.db.getAllMembers();
        peopleRVAdapter = new PeopleRVAdapter(memberList);
        peopleRV.setAdapter(peopleRVAdapter);
    }

    public void setupRecyclerView(View view) {
        peopleRV.setHasFixedSize(true);
        peopleRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final Member member = memberList.get(position);
                memberList.remove(member);
                refresh();
                Snackbar.make(getActivity().findViewById(R.id.main_content), member.get_name() + " deleted", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        memberList.add(member);
                        refresh();
                        Log.d(TAG, "onClick: Added back to UI");
                    }
                }).setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        Log.d(TAG, "onDismissed: Remove from DB");
                        TableTopicsApplication.db.deleteMember(member);
                    }
                }).show();
            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(peopleRV);
    }

    public static void reloadData() {
        Log.d("", "reloaded");
        memberList = TableTopicsApplication.db.getAllMembers();
        refresh();
    }

    public void hideKeyboard(boolean showKeyboard, View view) {
        if (showKeyboard) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } else {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

    public static void refresh() {
        peopleRVAdapter.addAllMembers(memberList);
        peopleRVAdapter.notifyDataSetChanged();
    }


}
