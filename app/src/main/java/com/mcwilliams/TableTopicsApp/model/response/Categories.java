package com.mcwilliams.TableTopicsApp.model.response;

import com.mcwilliams.TableTopicsApp.model.Category;

import java.util.List;

/**
 * Created by joshuamcwilliams on 10/11/15.
 */
public class Categories {
    private List<Category> results;

    public Categories(List<Category> results) {
        this.results = results;
    }

    public List<Category> getResults() {
        return results;
    }

    public void setResults(List<Category> results) {
        this.results = results;
    }

    public class Category {
        private String categoryName;
        private String createdAt;
        private String objectId;
        private String updatedAt;

        public Category(String categoryName, String createdAt, String objectId, String updatedAt) {
            this.categoryName = categoryName;
            this.createdAt = createdAt;
            this.objectId = objectId;
            this.updatedAt = updatedAt;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
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

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
