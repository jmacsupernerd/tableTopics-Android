package com.mcwilliams.TableTopicsApp.arrayadapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
public class TopicsRvAdapter extends RecyclerView.Adapter<TopicsRvAdapter.ViewHolder> {

    private List<Topic> topicList = new ArrayList<>();
    OnItemClickListener mItemClickListener;

    public TopicsRvAdapter(List<Topic> topics) {
        this.topicList = topics;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.lv_row_list_item, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder , int position) {
        holder.tvName.setText(topicList.get(position).get_topic());
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
