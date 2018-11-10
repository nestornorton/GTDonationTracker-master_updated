package com.doki_feeling.gtdonationtracking;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device. Grade must be update to most recent
 * version as well as Android Studio for these Junits and ActivityTestRule to work.
 *
 * NOTE: if an error "process crashed" or something like that comes up, make sure the emulator is
 * "free" of any running processes- shut it down, and run this test again.
 * @author nestormoreno GT ID: 903036415, nmoreno8
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityRule =
            new ActivityTestRule<>(RegisterActivity.class);

    /**
     * This Junit Instrumentation Test tests the onClick() method of RegisterActivity()-
     * onClick() has a if statement that checks if the user presses the signUp() button (using an if
     * statement), then the user account is either created or not created because of invalid
     * input information. (uses if/else in validateForm() method to check if email/pass is valid)
     * <p>
     * First a failed signUp, a error-text should appear for pass and email
     */
    @Test
    public void test_Register_SignUp_Malformed_Failure() {

        //fill in Register text boxes with invalid email/pass inputs
        onView(withId(R.id.fieldEmail))
                .perform(typeText("MALFORMED_EMAIL_TEST"), closeSoftKeyboard());

        onView(withId(R.id.fieldFullName)).perform(scrollTo(), typeText("GT_TEST"));

        onView(withId(R.id.fieldPhone)).perform(scrollTo(), typeText("1234567890"));

        onView(withId(R.id.fieldPassword)).perform(scrollTo(), typeText("123"));

        onView(withId(R.id.fieldConfirmPassword)).perform(typeText("123"));
        onView(withId(R.id.emailCreateAccountButton)).perform(scrollTo(), click());

        //wait some time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //assert that the UI does not create a new user, it stays stuck in the same Register Screen
        //giving the user a reason if their email/pass should be a certain length, etc.

        onView(withId(R.id.fieldEmail))
                .check(matches(withText("MALFORMED_EMAIL_TEST")));

        onView(withId(R.id.fieldPassword))
                .check(matches(withText("123")));

        onView(withId(R.id.fieldEmail)).check(matches(hasErrorText
                ("This email address is invalid.")));

        onView(withId(R.id.fieldPassword)).check(matches(hasErrorText
                ("Password should be at least 6 digits, and not purely numbers.")));

    }

    /**
     * Valid email/pass and inputs, onClick() should successfully validateForm() and a
     * "Loading.." progress dialog should pop up.
     */
    @Test
    public void test_Register_SignUp_Valid_Success() {

        /** Now test the onClick() method works when valid information
         * (email, password, full name, phone number) and a ProgressDialog "loading" is shown, which
         * means the valid info passed the onClick() method and validateForm() method.
         **/

        //fill in Register text boxes with VALID email/pass/name/phone number inputs
        onView(withId(R.id.fieldEmail))
                .perform(scrollTo(), typeText("gt@gatech.edu"));

        onView(withId(R.id.fieldConfirmPassword))
                .perform(typeText("123456a"));

        onView(withId(R.id.fieldFullName)).perform(scrollTo(), typeText("GT_TEST"));

        onView(withId(R.id.fieldPhone)).perform(scrollTo(), typeText("1234567890"));

        onView(withId(R.id.fieldPassword)).perform(typeText("123456a")).perform(scrollTo());

        onView(withId(R.id.emailCreateAccountButton)).perform(scrollTo(), click());

        //wait some time
        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Successful creation of user means a valid email/pass and OnClick() successfully creates
        //the user in Firebase Database.
        onView(withText(R.string.loading)).check(matches(withText("Loading…")));


    }
}