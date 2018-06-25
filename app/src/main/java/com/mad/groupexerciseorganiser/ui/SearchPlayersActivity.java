package com.mad.groupexerciseorganiser.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.adapter.GroupAdapter;
import com.mad.groupexerciseorganiser.adapter.PlayerSearchAdapter;
import com.mad.groupexerciseorganiser.main.RecyclerTouchListener;
import com.mad.groupexerciseorganiser.models.Group;
import com.mad.groupexerciseorganiser.models.Schedule;
import com.mad.groupexerciseorganiser.models.User;
import com.mad.groupexerciseorganiser.searchplayers.ISearchPlayersView;
import com.mad.groupexerciseorganiser.searchplayers.SearchPlayersPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Handles all view related components to search for players
 */

public class SearchPlayersActivity extends AppCompatActivity implements ISearchPlayersView {

    @BindView(R.id.content_players_search_recyclerView)
    RecyclerView mRecyclerView;
    PlayerSearchAdapter adapter;

    private static final String TAG = "SearchPlayersActivity";
    SearchPlayersPresenter mPresenter;
    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_players);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String uid = intent.getStringExtra(getString(R.string.uid));

        mPresenter = new SearchPlayersPresenter(this, uid, getApplicationContext());
        mPresenter.callDatabaseListener();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //touch listener that gets the position at the selected view
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mPosition = position;
            }
        }));
    }

    public void updateRecyclerView(ArrayList<User> users) {
        adapter = new PlayerSearchAdapter(users, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * Xml button on click method, called when a user selects the invite button
     */
    public void invitePlayer(View view) {
        mPresenter.invitePlayer(mPosition);
    }

    public void showInvitedToast(String fullName) {
        Toast.makeText(getApplicationContext(), getString(R.string.invited_prompt) + " " + fullName, Toast.LENGTH_LONG).show();
        Log.d(TAG, getString(R.string.user_invited));
    }

    public void showAlreadyInvitedToast(String fullName) {
        Toast.makeText(getApplicationContext(), getString(R.string.already_invited_prompt) + " " + fullName, Toast.LENGTH_LONG).show();
        Log.d(TAG, getString(R.string.user_invited));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
