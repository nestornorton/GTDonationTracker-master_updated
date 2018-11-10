package com.doki_feeling.gtdonationtracking;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class BaseActivity extends AppCompatActivity {

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    /**
     * Method that shows the ProgressDialog in the User Interface View - "Loading.." that notifies
     * a user of some action being executed.
     * if the ProgressDialog is null, a new ProgressDialog object is created, then shown in the UI.
     * Used in many classes for example RegisterActivity.java
     *
     * @return <code>void</code>
     */
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    /**
     * Method that dismisses a ProgressDialog that is currently being shown.
     *
     * @return <code>void</code>
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    /**
     * Method that hides the keyboard shown in the User Interface. If there is no Keyboard already
     * shown, nothing is done.
     *
     * @param view the view currently shown in the user interface to the user.
     *
     * @return <code>void</code>
     */
    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

}
