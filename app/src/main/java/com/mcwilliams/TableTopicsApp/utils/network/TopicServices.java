package com.mcwilliams.TableTopicsApp.utils.network;

import com.mcwilliams.TableTopicsApp.model.response.Categories;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by joshuamcwilliams on 10/11/15.
 */
public interface TopicServices {

    @GET("classes/Categories")
    Call<Categories> getCategories();

}
