package com.mad.groupexerciseorganiser.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.models.Group;
import com.mad.groupexerciseorganiser.models.Schedule;
import com.mad.groupexerciseorganiser.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Adapter handles underlying data for recycler views
 */

public class PlayerSearchAdapter extends RecyclerView.Adapter<PlayerSearchAdapter.ViewHolder> {

    //Declaration
    private List<User> mUsers = new ArrayList<>();
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder {
        //Declaration
        @BindView(R.id.player_search_name)
        TextView mName;
        @BindView(R.id.player_search_email)
        TextView mEmail;

        public ViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.player_search_name);
            mEmail = view.findViewById(R.id.player_search_email);
        }
    }

    public PlayerSearchAdapter(List<User> users, Context context) {
        mUsers = users;
        mContext = context;
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.mName.setText(user.getFullName());
        holder.mEmail.setText(user.getEmail());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_search_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
