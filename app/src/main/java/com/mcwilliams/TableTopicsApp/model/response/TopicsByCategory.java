package com.mcwilliams.TableTopicsApp.model.response;

import java.util.List;

/**
 * Created by joshuamcwilliams on 10/11/15.
 */
public class TopicsByCategory {
    private List<Topic> results;

    public TopicsByCategory(List<Topic> results) {
        this.results = results;
    }

    public List<Topic> getResults() {
        return results;
    }

    public void setResults(List<Topic> results) {
        this.results = results;
    }

    public class Topic {
        private String createdAt;
        private String objectId;
        private String topic;
        private String updatedAt;

        public Topic(String createdAt, String objectId, String topic, String updatedAt) {
            this.createdAt = createdAt;
            this.objectId = objectId;
            this.topic = topic;
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
