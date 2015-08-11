package com.mcwilliams.TableTopicsApp.mdactivitiess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.arrayadapters.MyAdapter;
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter;
import com.mcwilliams.TableTopicsApp.arrayadapters.PersonArrayAdapter;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.utils.Utils;
import com.squareup.seismic.ShakeDetector;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by joshuamcwilliams on 6/26/15.
 */
public class ManagePeople extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PeopleRVAdapter peopleRVAdapter;
    final DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_manage_data);

        Utils.setupToolbar(this);
        mDrawerLayout = Utils.setupDrawer(this, mDrawerLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvContent);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        peopleRVAdapter = new PeopleRVAdapter(getMembersForList(db));
        mRecyclerView.setAdapter(peopleRVAdapter);

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

    public List<Member> getMembersForList(DatabaseHandler db) {
        List<Member> members = db.getAllMembers();
        return members;
    }

    public void addPerson(View view){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add Person");
        View inputView = getLayoutInflater().inflate(R.layout.input_dialog, null);
        final EditText getInput = (EditText) inputView.findViewById(R.id.inputText);
        getInput.requestFocus();
        alert.setView(inputView);
        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.addMember(new Member(getInput.getText().toString()));
                mRecyclerView.setAdapter(new PeopleRVAdapter(getMembersForList(db)));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);

                Snackbar.make(mRecyclerView, getInput.getText().toString() + " added", Snackbar.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
            }
        });

        AlertDialog dialog = alert.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(getInput, InputMethodManager.SHOW_FORCED);
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        dialog.show();
    }

}
