package com.mcwilliams.TableTopicsApp.arrayadapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mcwilliams.TableTopicsApp.R
import com.mcwilliams.TableTopicsApp.model.Member
import com.mcwilliams.TableTopicsApp.model.Topic

import java.util.ArrayList
import kotlin.properties.Delegates

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
class TopicsRvAdapter(topics: List<Topic>) : RecyclerView.Adapter<TopicsRvAdapter.ViewHolder>() {

    internal var topicList : List<Topic> by Delegates.notNull()
    internal var mItemClickListener: OnItemClickListener? = null

    init {
        this.topicList = topics
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mInflater = LayoutInflater.from(parent.context)
        val sView = mInflater.inflate(R.layout.lv_row_list_item, parent, false)
        return ViewHolder(sView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = topicList[position]._topic
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        internal var tvName: TextView

        init {
            tvName = view.findViewById(R.id.tvName) as TextView
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (mItemClickListener != null) {
                mItemClickListener!!.onItemClick(v, position)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun SetOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }
}
