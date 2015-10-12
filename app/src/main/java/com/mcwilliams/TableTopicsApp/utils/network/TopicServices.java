package com.mcwilliams.TableTopicsApp.utils.network;

import com.mcwilliams.TableTopicsApp.model.response.Categories;
import com.mcwilliams.TableTopicsApp.model.response.TopicsByCategory;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by joshuamcwilliams on 10/11/15.
 */
public interface TopicServices {

    @GET("classes/Categories")
    Call<Categories> getCategories();

    @GET("classes/{category}")
    Call<TopicsByCategory> getTopicsByCategory(@Path("category") String category);

}
