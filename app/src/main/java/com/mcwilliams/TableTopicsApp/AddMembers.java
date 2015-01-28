package com.mcwilliams.TableTopicsApp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mcwilliams.TableTopicsApp.activities.BaseActivity;
import com.mcwilliams.TableTopicsApp.activities.Home;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;

/**
 * Created by m439047 on 5/29/13.
 */
public class AddMembers extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(SampleList.THEME); //Used for theme switching in samples
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.add_member_form, null, false);
        Drawer.addView(contentView, 0);

        TextView inputType = (TextView) findViewById(R.id.input_type);
        final EditText editText = (EditText) findViewById(R.id.editText);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        Button submit = (Button) findViewById(R.id.submit);
        Button preDefined = (Button) findViewById(R.id.preDefined);
        preDefined.setVisibility(View.INVISIBLE);
        final DatabaseHandler db = new DatabaseHandler(this);
        inputType.setText("Person's Name");
        submit.setText("Add Person");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String member = editText.getText().toString();
                Log.d("Starting", "String write to DB");
                db.addMember(new Member(member));
                Toast.makeText(AddMembers.this, member + " Added", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        AddMembers.this);
                adb.setTitle("Add Another Person?");
                //adb.setMessage("Would you like to add another Person?\n" );
                //  adb.setNegativeButton("Edit",null);
                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editText.setText("");
                    }
                });
                adb.setNeutralButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent goHome = new Intent(AddMembers.this, Home.class);
                        startActivity(goHome);
                    }
                });
                adb.show();
                Toast.makeText(AddMembers.this, member + " Added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
