package com.mcwilliams.TableTopicsApp.arrayadapters;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.model.Topic;

import java.util.List;

/**
 * Created by jrclark on 1/27/15.
 */
public class TopicArrayAdapter extends ArrayAdapter<Topic> {
    private List<Topic> topics;
    private Activity context;
    private int resource;
    private Resources resources;

    public TopicArrayAdapter(Activity context, int textViewResourceId,
                                  List<Topic> topics) {
        super(context, textViewResourceId, topics);
        this.context = context;
        this.topics = topics;
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
            viewContainer.topicName = (TextView) rowView.findViewById(R.id.tvName);
            rowView.setTag(viewContainer);
        } else {
            viewContainer = (ViewContainer) rowView.getTag();
        }
        if (topics != null) {
            viewContainer.topicName.setText(topics.get(position).get_topic());
        }
        return rowView;
    }

    static class ViewContainer {
        public TextView topicName;
    }
}
