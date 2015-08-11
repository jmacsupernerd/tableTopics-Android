package com.mcwilliams.TableTopicsApp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;

import java.util.List;

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
public class People extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PeopleRVAdapter peopleRVAdapter;
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
        peopleRVAdapter = new PeopleRVAdapter(getMembersForList(db));
        mRecyclerView.setAdapter(peopleRVAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
    }

    public void setupRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvContent);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    public List<Member> getMembersForList(DatabaseHandler db) {
        return db.getAllMembers();
    }

    @Override
    public void onClick(View v) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Add Person");
        View inputView = getActivity().getLayoutInflater().inflate(R.layout.input_dialog, null);
        final EditText getInput = (EditText) inputView.findViewById(R.id.inputText);
        getInput.requestFocus();
        alert.setView(inputView);
        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.addMember(new Member(getInput.getText().toString()));
                mRecyclerView.setAdapter(new PeopleRVAdapter(getMembersForList(db)));
                hideKeyboard(false, null);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hideKeyboard(false, null);
            }
        });

        AlertDialog dialog = alert.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                hideKeyboard(true, getInput);
            }
        });

        hideKeyboard(true, getInput);
        dialog.show();
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


}
