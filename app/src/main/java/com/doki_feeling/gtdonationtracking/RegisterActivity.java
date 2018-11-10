package com.doki_feeling.gtdonationtracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.doki_feeling.gtdonationtracking.model.AccountOwner;
import com.doki_feeling.gtdonationtracking.model.Privilege;
import com.doki_feeling.gtdonationtracking.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends BaseActivity implements
        View.OnClickListener {

    private static final String TAG = "Registration";
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private EditText mFullNameField;
    private EditText mPhoneField;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Views
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);
        mConfirmPasswordField = findViewById(R.id.fieldConfirmPassword);
        mFullNameField = findViewById(R.id.fieldFullName);
        mPhoneField = findViewById(R.id.fieldPhone);

        // Buttons
        findViewById(R.id.emailCreateAccountButton).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();
        mUser = AccountOwner.getInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Exists solely to verify menu options are created
     *
     * @return <code>true</code>
     * @params menu        The menu item for which options are created
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Update database
                            onRegisterSuccess(task.getResult().getUser());
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;
        String emptyField = getString(R.string.error_field_required);
        String noMatch = getString(R.string.password_not_match);
        String invalidEmail = getString(R.string.error_invalid_email);
        String invalidPassword = getString(R.string.error_invalid_password);
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(emptyField);
            valid = false;
        } else if (!validEmail(email)) {
            mEmailField.setError(invalidEmail);
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError(emptyField);
            valid = false;
        } else if (!validPassword(password)) {
            mPasswordField.setError(invalidPassword);
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        String confirmPassword = mConfirmPasswordField.getText().toString();
        if (!TextUtils.equals(password, confirmPassword)) {
            mConfirmPasswordField.setError(noMatch);
            valid = false;
        } else if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPasswordField.setError(emptyField);
            valid = false;
        } else {
            mConfirmPasswordField.setError(null);
        }

        String fullName = mFullNameField.getText().toString();
        if (TextUtils.isEmpty(fullName)) {
            mFullNameField.setError(emptyField);
            valid = false;
        } else {
            mFullNameField.setError(null);
        }

        String phoneNumber = mPhoneField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneField.setError(emptyField);
            valid = false;
        } else {
            mPhoneField.setError(null);
        }

        return valid;
    }

    private boolean validEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validPassword(String password) {
        if (password.length() >= 6 && !TextUtils.isDigitsOnly(password)) {
            return true;
        } else {
            return false;
        }

    }

    private void onRegisterSuccess(FirebaseUser user) {
        String email = user.getEmail();
        String userID = user.getUid();
        String name = mFullNameField.getText().toString();
        String phone = mPhoneField.getText().toString();
        mUser.updateAll(name, email, phone, false, Privilege.User);
        mDatabase.collection("users").document(userID).set(mUser);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.emailCreateAccountButton) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }
}
