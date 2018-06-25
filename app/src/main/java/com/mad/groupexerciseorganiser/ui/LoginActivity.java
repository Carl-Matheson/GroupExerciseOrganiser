package com.mad.groupexerciseorganiser.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.login.ILoginView;
import com.mad.groupexerciseorganiser.login.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Handles all view related components to logging in
 */

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private static final String TAG = "LoginActivity";
    LoginPresenter mPresenter;
    private ProgressDialog mProgress;

    @BindView(R.id.login_email_editText)
    EditText mEmail;
    @BindView(R.id.login_password_editText)
    EditText mPassword;
    @BindView(R.id.login_register_btn)
    Button mRegisterBtn;
    @BindView(R.id.login_login_btn)
    Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this, getApplicationContext());
        mProgress = new ProgressDialog(this);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    /**
     * Core method, passes data to presenter than model(interactor)
     */
    @Override
    public void login() {
        mProgress.setMessage(getString(R.string.logging_prompt));
        mProgress.show();

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        Log.d(TAG, getString(R.string.validating_password));
        mPresenter.validateLogin(email, password);
    }

    @Override
    public void loginSuccessful() {
        mProgress.dismiss();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Log.d(TAG, getString(R.string.login_successful));
    }

    @Override
    public void loginFailed(String error) {
        mProgress.dismiss();
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        Log.d(TAG, getString(R.string.login_failed));
    }

    @Override
    public void emptyDetails() {
        mProgress.dismiss();
        Toast.makeText(getApplicationContext(), R.string.emptyField_prompt, Toast.LENGTH_LONG).show();
        Log.d(TAG, getString(R.string.group_empty_details));
    }

    @Override
    public void accountSetupFailure() {
        Toast.makeText(getApplicationContext(), R.string.setup_prompt, Toast.LENGTH_LONG).show();
        Log.d(TAG, getString(R.string.account_failure));
    }

}