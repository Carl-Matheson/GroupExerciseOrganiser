package com.mad.groupexerciseorganiser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.models.Group;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Adapter handles underlying data for recycler views
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    //Declaration
    private ArrayList<Group> mUserGroupList;
    private Context mContext;

    /**
     * Inner class that handles
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        //Declaration
        @BindView(R.id.group_name)
        TextView mGroupName;
        @BindView(R.id.group_category)
        TextView mGroupSport;
        @BindView(R.id.group_icon)
        ImageView mGroupImage;
        @BindView(R.id.group_gender_icon)
        ImageView mGenderIcon;

        public ViewHolder(View view) {
            super(view);
            mGroupName = view.findViewById(R.id.group_name);
            mGroupImage = view.findViewById(R.id.group_icon);
            mGroupSport = view.findViewById(R.id.group_category);
            mGenderIcon = view.findViewById(R.id.group_gender_icon);
        }
    }

    public GroupAdapter(ArrayList<Group> groupList, Context context) {
        mUserGroupList = groupList;
        mContext = context;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Group group = mUserGroupList.get(position);
        holder.mGroupName.setText(group.getName());
        holder.mGroupSport.setText(group.getSport());
        Picasso.get().load(group.getUri()).into(holder.mGroupImage);

        if (group.getGender().equals(mContext.getString(R.string.female))) {
            holder.mGenderIcon.setImageResource(R.mipmap.female_icon);
        } else if (group.getGender().equals(mContext.getString(R.string.male))) {
            holder.mGenderIcon.setImageResource(R.mipmap.male_icon);
        } else if (group.getGender().equals(mContext.getString(R.string.mixed))) {
            holder.mGenderIcon.setImageResource(R.mipmap.mixed_icon);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mUserGroupList.size();
    }
}
