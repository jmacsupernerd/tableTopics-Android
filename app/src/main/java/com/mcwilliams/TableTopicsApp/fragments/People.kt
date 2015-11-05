package com.mcwilliams.TableTopicsApp.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

import com.mcwilliams.TableTopicsApp.R
import com.mcwilliams.TableTopicsApp.TableTopicsApplication
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter
import com.mcwilliams.TableTopicsApp.arrayadapters.TopicsRvAdapter
import com.mcwilliams.TableTopicsApp.model.Member
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler

import butterknife.Bind
import butterknife.ButterKnife
import butterknife.bindView

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
class People : Fragment() {
    val peopleRV: RecyclerView by bindView(R.id.rvContent)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.md_manage_topics, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        peopleRV.adapter = PeopleRVAdapter(TableTopicsApplication.db.allMembers)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        peopleRV.adapter = PeopleRVAdapter(TableTopicsApplication.db.allMembers)
    }

    //    override fun onActivityCreated(savedInstanceState: Bundle?) {
    //        super.onActivityCreated(savedInstanceState)
    //    }

    fun setupRecyclerView() {
        peopleRV.setHasFixedSize(true)
        peopleRV.layoutManager = LinearLayoutManager(activity)
    }

    fun hideKeyboard(showKeyboard: Boolean, view: View) {
        if (showKeyboard) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        } else {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }

//    companion object {
//        fun reloadData() {
//            Log.d("", "reloaded")
//            peopleRV.adapter = PeopleRVAdapter(TableTopicsApplication.db.allMembers)
//        }
//    }


}
