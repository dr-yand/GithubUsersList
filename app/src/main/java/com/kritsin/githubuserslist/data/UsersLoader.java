package com.kritsin.githubuserslist.data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.kritsin.githubuserslist.data.net.ApiInterface;
import com.kritsin.githubuserslist.data.net.ApiManager;
import com.kritsin.githubuserslist.model.RequestResult;
import com.kritsin.githubuserslist.model.User;
import com.kritsin.githubuserslist.model.UsersResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class UsersLoader extends AsyncTaskLoader<UsersResponse> {

    private long maxUserId = -1;

    public UsersLoader(Context context) {
        this(context, -1);
    }

    public UsersLoader(Context context, long maxUserId) {
        super(context);
        this.maxUserId = maxUserId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public UsersResponse loadInBackground() {
        try {
            UsersResponse response = apiCall();
            if (response.getRequestResult() == RequestResult.SUCCESS) {
                response.save(getContext());
                onSuccess();
            } else {
                onError();
            }
            return response;
        } catch (IOException e) {
            onError();
            return new UsersResponse();
        }
    }

    protected void onSuccess() {
    }

    protected void onError() {
    }

    protected UsersResponse apiCall() throws IOException {
        ApiInterface service = ApiManager.getApiInterface();
        Call<List<User>> call = null;
        if(maxUserId>-1)
            call = service.getUsers(maxUserId);
        else
            call = service.getUsers();
        List<User> users = call.execute().body();
        return new UsersResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(users);
    }
}