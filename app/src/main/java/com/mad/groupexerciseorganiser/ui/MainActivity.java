package com.mad.groupexerciseorganiser.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.adapter.GroupAdapter;
import com.mad.groupexerciseorganiser.main.IMainView;
import com.mad.groupexerciseorganiser.main.MainPresenter;
import com.mad.groupexerciseorganiser.main.RecyclerTouchListener;
import com.mad.groupexerciseorganiser.models.Group;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Handles all view related content for Teams/Groups displayed on main
 */

public class MainActivity extends AppCompatActivity implements IMainView {

    private static final String TAG = "MainActivity";
    MainPresenter mPresenter;
    GroupAdapter mGroupAdapter;
    private int mPosition;

    @BindView(R.id.content_main_recyclerView)
    RecyclerView mGroupRecylcerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new MainPresenter(this, getApplicationContext());
        mPresenter.checkUserState();
        mPresenter.callDatabaseListener();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateGroupActivity.class));
            }
        });


        //touch listener that gets the position at the selected view
        mGroupRecylcerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mGroupRecylcerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mPosition = position;
            }
        }));
    }

    @Override
    public void startLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    @Override
    public void loadUserTypeOrganiser() {
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadUserTypeParticipant() {
        fab.setVisibility(View.INVISIBLE);
        fab.setImageResource(R.drawable.ic_action_search);
    }

    /**
     * XML OnClick Listener method called when user clicks on a group
     */
    public void groupClicked(View view) {
        //gets and returns the selected group object
        Group group = mPresenter.handleGroupClick(mPosition);
        Log.d(TAG, getString(R.string.group_selected));

        Intent groupIntent = new Intent(MainActivity.this, GroupActivity.class);
        groupIntent.putExtra(getString(R.string.name), group.getName());
        groupIntent.putExtra(getString(R.string.uid), group.getUid());
        startActivity(groupIntent);
    }

    @Override
    public void updateRecyclerView(ArrayList<Group> groups) {
        mGroupAdapter = new GroupAdapter(groups, this);
        mGroupRecylcerView.setLayoutManager(new LinearLayoutManager(this));
        mGroupRecylcerView.setItemAnimator(new DefaultItemAnimator());
        mGroupRecylcerView.setAdapter(mGroupAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.addAuthStateListener();
        Log.d(TAG, getString(R.string.start_activity));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            mPresenter.logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
