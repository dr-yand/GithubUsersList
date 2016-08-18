package com.kritsin.githubuserslist.data.net;

import com.kritsin.githubuserslist.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/users")
    Call<List<User>> getUsers(@Query("since") long maxUserId);
}
