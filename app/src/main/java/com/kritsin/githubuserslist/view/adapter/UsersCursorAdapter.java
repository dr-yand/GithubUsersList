package com.kritsin.githubuserslist.view.adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kritsin.githubuserslist.R;
import com.kritsin.githubuserslist.listener.LoadMoreListener;
import com.kritsin.githubuserslist.model.User;
import com.kritsin.githubuserslist.data.db.UsersDbAdapter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UsersCursorAdapter extends CursorAdapter {

    public static final int VIEW_TYPE_COUNT = 2;
    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    private Context context;
    private LayoutInflater inflater;
    private View progressBar, errorView;

    private boolean showProgressBar = true;

    private LoadMoreListener loadMoreListener;

    public UsersCursorAdapter(Context context, Cursor cursor, LoadMoreListener listener) {
        super(context, cursor, 0);
        this.context = context;
        this.loadMoreListener = listener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.progressBar = inflater.inflate(R.layout.item_progress_bar, null, false);
        this.errorView = inflater.inflate(R.layout.item_error, null, false);
    }

    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public int getCount() {
        return super.getCount()>0?super.getCount()+1:super.getCount();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT  ;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= super.getCount()&&super.getCount()>0) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(getItemViewType(position) == VIEW_TYPE_LOADING){
            if(showProgressBar) {
                convertView = progressBar;
                if (loadMoreListener != null) {
                    User item = UsersDbAdapter.fromCursor(getCursor(), position - 1);
                    loadMoreListener.loadMore(item.getId());
                }
            }
            else {
                convertView = errorView;
                showProgressBar=true;
                notifyDataSetChanged();
            }
        }
        else {
            UserViewHolder viewHolder;

            if (convertView != null) {
                viewHolder = (UserViewHolder) convertView.getTag();
            } else {
                convertView = inflater.inflate(R.layout.item_user, parent, false);
                viewHolder = new UserViewHolder(convertView);
                convertView.setTag(viewHolder);
            }

            User item = UsersDbAdapter.fromCursor(getCursor(), position);
            viewHolder.text.setText(item.getLogin());

            Picasso
                    .with(context)
                    .load(item.getAvatarUrl())
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(viewHolder.image);
        }

        return convertView;
    }

    static class UserViewHolder {
        @Bind(R.id.text)
        TextView text;
        @Bind(R.id.image)
        ImageView image;

        public UserViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}