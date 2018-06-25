package com.mad.groupexerciseorganiser.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.signup.ISignUpView;
import com.mad.groupexerciseorganiser.signup.SignUpPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Handles all view related components to sign-up
 */

public class SignupActivity extends AppCompatActivity implements ISignUpView {

    SignUpPresenter mPresenter;
    private ProgressDialog mProgress;
    private static final String TAG = "SignupActivity";

    @BindView(R.id.signup_firstName_txt)
    EditText mFirstNameText;
    @BindView(R.id.signup_lastName_txt)
    EditText mLastNameText;
    @BindView(R.id.signup_email_txt)
    EditText mEmailText;
    @BindView(R.id.signup_password_txt)
    EditText mPasswordText;
    @BindView(R.id.signup_radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.signup_register_btn)
    Button mRegisterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPresenter = new SignUpPresenter(this, getApplicationContext());
        mProgress = new ProgressDialog(this);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });
    }

    /**
     * Handles user input, passes it to the presenter, then to the interactor
     */
    @Override
    public void signUpUser() {
        mProgress.setMessage(getString(R.string.register_prompt));
        mProgress.show();

        String firstName = mFirstNameText.getText().toString().trim();
        String lastName = mLastNameText.getText().toString().trim();
        String email = mEmailText.getText().toString().trim();
        String password = mPasswordText.getText().toString().trim();

        String type = getString(R.string.empty);
        int selectedUserType = mRadioGroup.getCheckedRadioButtonId(); //Handles the selected user type

        if (selectedUserType != -1) {
            RadioButton selectedRadioButton = findViewById(selectedUserType);
            type = selectedRadioButton.getText().toString();
        }
        mPresenter.validateSignUp(firstName, lastName, email, password, type);
    }

    @Override
    public void signUpFailed(String error) {
        mProgress.dismiss();
        Toast.makeText(getApplicationContext(), getString(R.string.error_prompt) + error, Toast.LENGTH_LONG).show();
        Log.d(TAG, getString(R.string.signup_failed));
    }

    @Override
    public void signUpSuccessful() {
        mProgress.dismiss();
        Intent registerIntent = new Intent(SignupActivity.this, MainActivity.class);
        registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(registerIntent);
    }

    @Override
    public void emptyDetails() {
        mProgress.dismiss();
        Toast.makeText(getApplicationContext(), R.string.emptyFields_prompt, Toast.LENGTH_LONG).show();
        Log.d(TAG, getString(R.string.group_empty_details));
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
