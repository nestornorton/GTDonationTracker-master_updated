package com.doki_feeling.gtdonationtracking;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.doki_feeling.gtdonationtracking.model.AccountOwner;
import com.doki_feeling.gtdonationtracking.model.Privilege;
import com.doki_feeling.gtdonationtracking.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserEditActivity extends BaseActivity implements
        View.OnClickListener {

    private static final String TAG = "UserEditActivity";
    private Spinner usergroupSpinner;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private User mUser;
    ArrayAdapter<Privilege> usergroupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        findViewById(R.id.submitButton).setOnClickListener(this);

        usergroupSpinner = findViewById(R.id.usergroupSpinner);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();
        mUser = AccountOwner.getInstance();
        usergroupAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Privilege.values());
    }

    @Override
    public void onStart() {
        super.onStart();
        usergroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usergroupSpinner.setAdapter(usergroupAdapter);
        usergroupSpinner.setSelection(usergroupAdapter.getPosition(mUser.getPrivilege()));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.submitButton) {
            mUser.setPrivilege((Privilege) usergroupSpinner.getSelectedItem());
            mDatabase.collection("users")
                    .document(mAuth.getUid())
                    .update("privilege", mUser.getPrivilege().getPrivilege());
        }
    }
}