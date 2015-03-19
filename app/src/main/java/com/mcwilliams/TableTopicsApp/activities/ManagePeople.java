package com.mcwilliams.TableTopicsApp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.ads.AdView;
import com.mcwilliams.TableTopicsApp.arrayadapters.PersonArrayAdapter;
import com.mcwilliams.TableTopicsApp.arrayadapters.TopicArrayAdapter;
import com.mcwilliams.TableTopicsApp.model.Topic;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuamcwilliams on 5/29/13.
 */
public class ManagePeople extends BaseActivity implements SwipeMenuListView.OnMenuItemClickListener {

    private SwipeMenuListView lv;
    final DatabaseHandler db = new DatabaseHandler(this);
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.manage_data, null, false);
        Drawer.addView(contentView, 0);

        TextView title = (TextView) findViewById(R.id.manageTitle);
        title.setText("Delete People");
        title.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");

        TextView rowToEdit = (TextView) findViewById(R.id.rowToEditText);
        rowToEdit.setText("Swipe left on row to view options");

        lv = (SwipeMenuListView) findViewById(R.id.nameListView);
        lv.setAdapter(getMembersForList(db));
        lv.setMenuCreator(creator);
        lv.setOnMenuItemClickListener(this);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "Swipe left on row", Toast.LENGTH_SHORT).show();
            }
        });

        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        addPerson();
        return super.onOptionsItemSelected(item);
    }

    public void addPerson(){
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
                lv.setAdapter(getMembersForList(db));
                lv.setTextFilterEnabled(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
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

    public PersonArrayAdapter getMembersForList(DatabaseHandler db) {
        List<Member> members = db.getAllMembers();
        return new PersonArrayAdapter(this, R.layout.lv_row_list_item, members);
    }

    public void deleteAllPeople() {
        db.deleteAllPeople();
        Toast.makeText(getBaseContext(), "All People Deleted", Toast.LENGTH_SHORT).show();
        Intent goHome = new Intent(getBaseContext(), Home.class);
        startActivity(goHome);
    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem openItem = new SwipeMenuItem(
                    getBaseContext());
            openItem.setBackground(R.color.blue);
            openItem.setWidth(Utils.dpToPx(90, getBaseContext()));
            openItem.setIcon(android.R.drawable.ic_menu_edit);
            menu.addMenuItem(openItem);

            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getBaseContext());
            deleteItem.setBackground(R.color.blue);
            deleteItem.setWidth(Utils.dpToPx(90, getBaseContext()));
            deleteItem.setIcon(android.R.drawable.ic_menu_delete);
            menu.addMenuItem(deleteItem);
        }
    };

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        View view;
        switch (index) {
            case 0:
                // edit
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Edit Member");
                View inputView = getLayoutInflater().inflate(R.layout.input_dialog, null);
                final EditText getInput = (EditText) inputView.findViewById(R.id.inputText);

                final Member editMember = (Member) lv.getItemAtPosition(position);
                getInput.setText(editMember.get_name());
                alert.setView(inputView);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        editMember.set_name(getInput.getText().toString());
                        db.updateMember(editMember);
                        lv.setAdapter(getMembersForList(db));
                        lv.setTextFilterEnabled(true);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
                break;
            case 1:
                // delete
                Member member = (Member) lv.getItemAtPosition(position);
                db.deleteMember(member);
                lv.setAdapter(getMembersForList(db));
                lv.setTextFilterEnabled(true);
                break;
        }
        return false;
    }
}
