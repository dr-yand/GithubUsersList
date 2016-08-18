package com.kritsin.githubuserslist.view;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.kritsin.githubuserslist.R;
import com.kritsin.githubuserslist.listener.LoadMoreListener;
import com.kritsin.githubuserslist.model.RequestResult;
import com.kritsin.githubuserslist.data.db.UsersDbAdapter;
import com.kritsin.githubuserslist.data.UsersLoader;
import com.kritsin.githubuserslist.model.UsersResponse;
import com.kritsin.githubuserslist.view.adapter.UsersCursorAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoadMoreListener, LoaderManager.LoaderCallbacks<UsersResponse> {

    private final int USERS_LOADER_ID = 999;
    private final String MAX_USER_ID = "MAX_USER_ID";

    @Bind(R.id.users_list)
    ListView usersList;

    private UsersCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        View emptyView = getLayoutInflater().inflate(R.layout.item_progress_bar, null);
        addContentView(emptyView, usersList.getLayoutParams());
        usersList.setEmptyView(emptyView);

        initUserList();

        getSupportLoaderManager().initLoader(USERS_LOADER_ID, Bundle.EMPTY, this);
        getSupportLoaderManager().enableDebugLogging(true);
    }

    private void initUserList(){
        UsersDbAdapter usersDbAdapter = new UsersDbAdapter(this);
        adapter = new UsersCursorAdapter(getApplicationContext(), usersDbAdapter.getAllUsersCursor(), this);
        usersList.setAdapter(adapter);
    }

    private void refreshUsersList(){
        UsersDbAdapter usersDbAdapter = new UsersDbAdapter(this);
        adapter.changeCursor(usersDbAdapter.getAllUsersCursor());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(long maxUserId) {
        Bundle bundle = new Bundle();
        bundle.putLong(MAX_USER_ID, maxUserId);
        getSupportLoaderManager().initLoader(USERS_LOADER_ID, bundle, this);
    }


    @Override
    public Loader<UsersResponse> onCreateLoader(int id, Bundle args) {
        return new UsersLoader(this, args.getLong(MAX_USER_ID, -1));
    }

    @Override
    public void onLoadFinished(Loader<UsersResponse> loader, UsersResponse data) {
        View emptyView = getLayoutInflater().inflate(R.layout.empty_view, null);
        addContentView(emptyView, usersList.getLayoutParams());
        if(usersList.getEmptyView()!=null)
            ((ViewGroup)usersList.getEmptyView().getParent()).removeView(usersList.getEmptyView());
        usersList.setEmptyView(emptyView);
        if(data.getRequestResult() == RequestResult.ERROR) {
            adapter.setShowProgressBar(false);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, R.string.error_message, Toast.LENGTH_LONG).show();
        }
        else {
            refreshUsersList();
        }
        getSupportLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(Loader<UsersResponse> loader) {

    }
}
