package com.mcwilliams.TableTopicsApp.model.response;

import java.util.List;

/**
 * Created by joshuamcwilliams on 1/29/16.
 */
public class CategoryResponse {

    /**
     * created : 1454118188000
     * ___class : Categories
     * ownerId : null
     * updated : null
     * categoryName : Popular
     * objectId : 66D63097-A788-E78F-FF3E-CCB708984E00
     * __meta : {"relationRemovalIds":{},"selectedProperties":["created","___class","ownerId","updated","categoryName","objectId"],"relatedObjects":{}}
     */

    private List<Category> data;

    public void setData(List<Category> data) {
        this.data = data;
    }

    public List<Category> getData() {
        return data;
    }

    public static class Category {
        private String categoryName;
        private String objectId;

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public String getObjectId() {
            return objectId;
        }
    }
}
