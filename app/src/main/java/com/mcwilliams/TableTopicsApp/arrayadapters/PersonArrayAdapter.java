package com.mcwilliams.TableTopicsApp.arrayadapters;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.model.Member;
import com.mcwilliams.TableTopicsApp.model.Topic;

import java.util.List;

/**
 * Created by joshuamcwilliams on 1/27/15.
 */
public class PersonArrayAdapter extends ArrayAdapter<Member> {
    private List<Member> members;
    private Activity context;
    private int resource;
    private Resources resources;

    public PersonArrayAdapter(Activity context, int textViewResourceId,
                             List<Member> members) {
        super(context, textViewResourceId, members);
        this.context = context;
        this.members = members;
        this.resource = textViewResourceId;
        resources = context.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewContainer viewContainer;
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(resource, null);
            viewContainer = new ViewContainer();
            viewContainer.memberName = (TextView) rowView.findViewById(R.id.tvName);
            rowView.setTag(viewContainer);
        } else {
            viewContainer = (ViewContainer) rowView.getTag();
        }
        if (members != null) {
            viewContainer.memberName.setText(members.get(position).get_name());
        }
        return rowView;
    }

    static class ViewContainer {
        public TextView memberName;
    }
}
