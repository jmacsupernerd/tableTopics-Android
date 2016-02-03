package com.mcwilliams.TableTopicsApp.arrayadapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.model.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuamcwilliams on 6/26/15.
 */
public class PeopleRVAdapter extends RecyclerView.Adapter<PeopleRVAdapter.ViewHolder> {

    private List<Member> memberList = new ArrayList<>();
    OnItemClickListener mItemClickListener;

    public PeopleRVAdapter(List<Member> members) {
        this.memberList = members;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.lv_row_list_item, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder , int position) {
        holder.tvName.setText(memberList.get(position).get_name());
    }

    @Override
    public int getItemCount() {
        return memberList.size();
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

    public Member getMember(int position){
        return memberList.get(position);
    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void addAllMembers(List<Member> members){
        memberList.clear();
        memberList.addAll(members);
    }
}
