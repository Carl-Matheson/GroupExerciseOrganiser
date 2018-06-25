package com.mad.groupexerciseorganiser.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.creategroup.CreateGroupPresenter;
import com.mad.groupexerciseorganiser.creategroup.ICreateGroupView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Handles all view related components to creating a group
 */

public class CreateGroupActivity extends AppCompatActivity implements ICreateGroupView {

    CreateGroupPresenter mPresenter;
    private ProgressDialog mProgress;
    private Uri mUri;
    private static final String TAG = "Create Group Activity";

    @BindView(R.id.create_group_name)
    EditText mGroupName;
    @BindView(R.id.create_group_gender_spinner)
    Spinner mGenderSpinner;
    @BindView(R.id.create_group_sport_spinner)
    Spinner mSportSpinner;
    @BindView(R.id.create_group_create_button)
    Button mCreateBtn;
    @BindView(R.id.create_group_image_button)
    ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPresenter = new CreateGroupPresenter(this, getApplicationContext());
        mProgress = new ProgressDialog(this);

        ArrayAdapter<CharSequence> adapterGender = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(adapterGender);

        ArrayAdapter<CharSequence> adapterSport = ArrayAdapter.createFromResource(this,
                R.array.sports, android.R.layout.simple_spinner_item);
        adapterSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSportSpinner.setAdapter(adapterSport);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewGroup();
            }
        });

        //Saves selected image locally, uses image cropper
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Permissions Check
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(CreateGroupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), R.string.denied, Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(CreateGroupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        imagePicker();
                    }
                } else {
                    imagePicker();
                }
            }
        });
    }

    @Override
    public void createNewGroup() {

        mProgress.setMessage(getString(R.string.create_group_promt));
        mProgress.show();

        String name = mGroupName.getText().toString().trim();
        String sport = mSportSpinner.getSelectedItem().toString().trim();
        String gender = mGenderSpinner.getSelectedItem().toString().trim();

        mPresenter.validateGroup(name, gender, sport, mUri);
        Log.d(TAG, getString(R.string.group_successful));
    }

    @Override
    public void imagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(CreateGroupActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mUri = result.getUri();
                mImageButton.setImageURI(mUri);
                Log.d(TAG, getString(R.string.correct));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(), getString(R.string.error_prompt) + error, Toast.LENGTH_LONG).show();
                Log.d(TAG, getString(R.string.incorrect));
            }
        }
    }

    @Override
    public void groupSuccessfullyCreated() {
        mProgress.dismiss();
        startActivity(new Intent(CreateGroupActivity.this, MainActivity.class));
    }

    @Override
    public void emptyDetails() {
        mProgress.dismiss();
        Toast.makeText(getApplicationContext(), R.string.emptyField_prompt, Toast.LENGTH_LONG).show();
        Log.d(TAG, getString(R.string.group_empty_details));
    }

    @Override
    public void error(String error) {
        mProgress.dismiss();
        Toast.makeText(getApplicationContext(), getString(R.string.error_prompt) + error, Toast.LENGTH_LONG).show();
        Log.d(TAG, error);
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
