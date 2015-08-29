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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.TableTopicsApplication;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.arrayadapters.TopicsRvAdapter;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
public class People extends Fragment {
    public static RecyclerView peopleRV;

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
        peopleRV.setAdapter(new PeopleRVAdapter(TableTopicsApplication.db.getAllMembers()));
    }

    public void setupRecyclerView(View view) {
        peopleRV.setHasFixedSize(true);
        peopleRV.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static void reloadData(){
        Log.d("","reloaded");
        peopleRV.setAdapter(new PeopleRVAdapter(TableTopicsApplication.db.getAllMembers()));
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
