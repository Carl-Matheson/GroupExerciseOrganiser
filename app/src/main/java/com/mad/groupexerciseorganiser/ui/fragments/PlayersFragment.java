package com.mad.groupexerciseorganiser.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.adapter.PlayerAdapter;
import com.mad.groupexerciseorganiser.adapter.ScheduleAdapter;
import com.mad.groupexerciseorganiser.main.RecyclerTouchListener;
import com.mad.groupexerciseorganiser.models.Schedule;
import com.mad.groupexerciseorganiser.models.User;
import com.mad.groupexerciseorganiser.players.IPlayerView;
import com.mad.groupexerciseorganiser.players.PlayerPresenter;
import com.mad.groupexerciseorganiser.schedule.SchedulePresenter;
import com.mad.groupexerciseorganiser.ui.SearchPlayersActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that Handles all view related components to players
 */

public class PlayersFragment extends Fragment implements IPlayerView {

    private String mUid;
    private RecyclerView mRecyclerView;
    private int mPosition;
    PlayerPresenter mPresenter;
    PlayerAdapter mAdapter;

    private Button removeButton;
    private FloatingActionButton mFloatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_players, container, false);
        mRecyclerView = view.findViewById(R.id.content_participants_recyclerView);
        removeButton = view.findViewById(R.id.player_delete_button);
        mUid = getArguments().getString(getString(R.string.uid));

        mPresenter = new PlayerPresenter(this, mUid, this.getContext());
        mPresenter.callDatabase();

        //touch listener that gets the position at the selected view
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mPosition = position;
            }
        }));

        mFloatingActionButton = view.findViewById(R.id.players_fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchPlayersActivity.class);
                intent.putExtra(getString(R.string.uid), mUid);
                startActivity(intent);
            }
        });
        return view;
    }

    public void loadUserTypeCoach() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
    }

    public void loadUserTypePlayer() {
        mFloatingActionButton.setVisibility(View.INVISIBLE);
    }

    public void showRemovedToast(String fullName) {
        Toast.makeText(this.getContext(), getString(R.string.removed_prompt) + " " + fullName, Toast.LENGTH_LONG).show();
    }

    public void updateRecyclerView(ArrayList<User> players) {
        mAdapter = new PlayerAdapter(players, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void removePlayer() { //View view
        mPresenter.removeUser(mPosition);
    }

}
