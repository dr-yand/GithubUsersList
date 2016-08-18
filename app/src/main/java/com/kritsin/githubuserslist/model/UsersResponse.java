package com.kritsin.githubuserslist.model;

import android.content.Context;

import com.kritsin.githubuserslist.data.db.UsersDbAdapter;

import java.util.List;

public class UsersResponse {

    private Object mAnswer;

    private RequestResult mRequestResult;

    public UsersResponse() {
        mRequestResult = RequestResult.ERROR;
    }
 
    public RequestResult getRequestResult() {
        return mRequestResult;
    }

    public UsersResponse setRequestResult(RequestResult requestResult) {
        mRequestResult = requestResult;
        return this;
    }

    public <T> T getTypedAnswer() {
        if (mAnswer == null) {
            return null;
        }
        return (T) mAnswer;
    }

    public UsersResponse setAnswer(Object answer) {
        mAnswer = answer;
        return this;
    }

    public void save(Context context) {
        List<User> users = getTypedAnswer();
        if (users != null) {
            UsersDbAdapter usersDbAdapter = new UsersDbAdapter(context);
            usersDbAdapter.addUsers(users);
            usersDbAdapter.close();
        }
    }
}