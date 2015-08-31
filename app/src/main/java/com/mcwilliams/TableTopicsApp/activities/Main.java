package com.mcwilliams.TableTopicsApp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mcwilliams.TableTopicsApp.FragmentAdapter;
import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.TableTopicsApplication;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.fragments.HomeFragment;
import com.mcwilliams.TableTopicsApp.fragments.People;
import com.mcwilliams.TableTopicsApp.fragments.Topics;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
public class Main extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        fab.setVisibility(View.GONE);

        viewPager.addOnPageChangeListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new Topics(), "Topics");
        adapter.addFragment(new People(), "People");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }
    @Override
    public void onPageSelected(int i) {
        if(i == 0){
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @OnClick(R.id.fab)
    public void fabClicked() {
        showDialog(viewPager.getAdapter().getPageTitle(viewPager.getCurrentItem()));
    }

    public void showDialog(CharSequence pageTitle) {
        Log.d("", (String) pageTitle);
        String title = (String) pageTitle;
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        if(title.equals("Home")){

        } else if(title.equals("Topics")){
            alert.setTitle("Add Topic");
            View inputView = getLayoutInflater().inflate(R.layout.input_dialog, null);
            final EditText getInput = (EditText) inputView.findViewById(R.id.inputText);
            getInput.requestFocus();
            alert.setView(inputView);
            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TableTopicsApplication.db.addTopic(new Topic(getInput.getText().toString()));
                    Topics.reloadData();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        } else {
            alert.setTitle("Add Person");
            View inputView = getLayoutInflater().inflate(R.layout.input_dialog, null);
            final EditText getInput = (EditText) inputView.findViewById(R.id.inputText);
            getInput.requestFocus();
            alert.setView(inputView);
            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TableTopicsApplication.db.addMember(new Member(getInput.getText().toString()));
                    People.reloadData();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        alert.show();
    }
}
