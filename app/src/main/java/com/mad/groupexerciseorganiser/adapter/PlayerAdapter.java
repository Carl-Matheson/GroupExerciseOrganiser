package com.mad.groupexerciseorganiser.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    //Declaration
    private ArrayList<User> mPlayers = new ArrayList<>();
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder {
        //Declaration
        @BindView(R.id.player_name)
        TextView mName;
        @BindView(R.id.player_email)
        TextView mEmail;
        @BindView(R.id.player_delete_button)
        Button mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.player_name);
            mEmail = view.findViewById(R.id.player_email);
            mDeleteButton = view.findViewById(R.id.player_delete_button);
        }
    }

    public PlayerAdapter(ArrayList<User> users, Context context) {
        mPlayers = users;
        mContext = context;
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mPlayers.get(position);
        holder.mName.setText(user.getFullName());
        holder.mEmail.setText(user.getEmail());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }
}
