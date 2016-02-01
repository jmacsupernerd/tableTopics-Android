package com.mcwilliams.TableTopicsApp.model.response;

import java.util.List;

/**
 * Created by joshuamcwilliams on 2/1/16.
 */
public class CategoryTopicList {

    /**
     * created : 1454344122000
     * ___class : Conversational
     * topic : Should we destroy all nuclear weapons?
     * ownerId : null
     * updated : null
     * objectId : 3FA52A69-8F4A-F741-FFF7-0DEF2C9B1200
     * __meta : {"relationRemovalIds":{},"selectedProperties":["created","___class","topic","ownerId","updated","objectId"],"relatedObjects":{}}
     */

    private List<Topic> data;

    public void setData(List<Topic> data) {
        this.data = data;
    }

    public List<Topic> getData() {
        return data;
    }

    public static class Topic {
        private String topic;
        private String objectId;

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getTopic() {
            return topic;
        }

        public String getObjectId() {
            return objectId;
        }
    }
}
