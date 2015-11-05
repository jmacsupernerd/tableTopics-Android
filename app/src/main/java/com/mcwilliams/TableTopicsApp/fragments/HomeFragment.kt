package com.mcwilliams.TableTopicsApp.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.bindView
import com.google.android.gms.ads.AdView

import com.mcwilliams.TableTopicsApp.R
import com.mcwilliams.TableTopicsApp.TableTopicsApplication
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter
import com.mcwilliams.TableTopicsApp.model.Member
import com.mcwilliams.TableTopicsApp.model.Topic
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler
import com.mcwilliams.TableTopicsApp.utils.Utils



/**
 * Created by joshuamcwilliams on 7/2/15.
 */
class HomeFragment : Fragment() {

    val memberName: TextView by bindView(R.id.memberName)
    val topicName: TextView by bindView(R.id.topicText)
    val btnGenerate: Button by bindView(R.id.generate)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.home, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnGenerate.setOnClickListener{
            generateRandomTopicsMembers(TableTopicsApplication.db, memberName, topicName)
        }
    }

    fun generateRandomTopicsMembers(db: DatabaseHandler, memberName: TextView, topicName: TextView) {
        if (db.allMembers.size > 0 && db.allTopics.size > 0) {
            if (db.allMembers.size > 1) {
                memberName.text = db.allMembers[Utils.randInt(db.allMembers.size)]._name
            } else {
                memberName.text = db.allMembers[0]._name
            }

            if (db.allTopics.size > 1) {
                topicName.text = db.allTopics[Utils.randInt(db.allTopics.size)]._topic
            } else {
                topicName.text = db.allTopics[0]._topic
            }
        } else {
            val adb = AlertDialog.Builder(activity)
            adb.setTitle("Error").setMessage("Please enter some people and topics").setPositiveButton("Ok", null)
            adb.show()
        }
    }
}
