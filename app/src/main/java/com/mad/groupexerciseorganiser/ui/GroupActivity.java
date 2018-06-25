package com.mad.groupexerciseorganiser.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.models.Group;
import com.mad.groupexerciseorganiser.adapter.SectionsPageAdapter;
import com.mad.groupexerciseorganiser.ui.fragments.PlayersFragment;
import com.mad.groupexerciseorganiser.ui.fragments.ScheduleFragment;

import java.util.ArrayList;

/**
 * Parent class for fragments that handles all view related components to groups
 */

public class GroupActivity extends AppCompatActivity {

    private static final String TAG = "GroupActivity";
    ViewPager mViewPager;
    PlayersFragment mPlayersFragment;
    private Bundle mScheduleData;
    private Bundle mPlayerData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Intent intent = getIntent();
        String name = intent.getStringExtra(getString(R.string.name));
        String uid = intent.getStringExtra(getString(R.string.uid));

        mScheduleData = new Bundle();
        mScheduleData.putString(getString(R.string.uid), uid);
        mScheduleData.putString(getString(R.string.name), name);

        mPlayerData = new Bundle();
        mPlayerData.putString(getString(R.string.uid), uid);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(name);
        }

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * Initialises and creates fragments
     */
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.setArguments(mScheduleData);
        mPlayersFragment = new PlayersFragment();
        mPlayersFragment.setArguments(mPlayerData);

        adapter.addFragment(scheduleFragment, getString(R.string.schedule));
        adapter.addFragment(mPlayersFragment, getString(R.string.players));
        viewPager.setAdapter(adapter);
        Log.d(TAG, getString(R.string.fragment_init));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void removePlayer(View view) {
        mPlayersFragment.removePlayer();
        Log.d(TAG, getString(R.string.player_removed));
    }
}
