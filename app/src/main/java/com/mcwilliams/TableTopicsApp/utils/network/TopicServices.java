package com.mcwilliams.TableTopicsApp.utils.network;

import com.mcwilliams.TableTopicsApp.model.response.CategoryResponse;
import com.mcwilliams.TableTopicsApp.model.response.CategoryTopicList;
import com.mcwilliams.TableTopicsApp.model.response.TopicsByCategory;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by joshuamcwilliams on 10/11/15.
 */
public interface TopicServices {

    @GET("data/Categories")
    Call<CategoryResponse> getCategories();

    @GET("data/{category}")
    Call<CategoryTopicList> getTopicsByCategory(@Path("category") String category);

}
