package com.example.chatchat.Activities;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

import com.example.chatchat.R;
import com.example.chatchat.ResetPasswordActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

public class ResetPasswordTest {
    @Rule
    public ActivityTestRule<ResetPasswordActivity> ResetPasswordActivityTestRule = new ActivityTestRule<>(ResetPasswordActivity.class);
    private ResetPasswordActivity resetPasswordActivity;
    @Before
    public void setUp() {
        resetPasswordActivity = ResetPasswordActivityTestRule.getActivity();
    }

    @Test
    public void invalid_email()
    {
        assertNotNull(resetPasswordActivity.findViewById(R.id.reset_password_email_editText));
        assertNotNull(resetPasswordActivity.findViewById(R.id.reset_password_send_button));
        assertNotNull(onView(withId(R.id.reset_password_resend_button)));
        onView(withId(R.id.reset_password_email_editText)).perform(typeText("aaaaaaaaaaaaa"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.reset_password_send_button)).perform(click());
        //onView(withId(R.id.reset_password_send_button)).perform(pressBack());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Not a valid Email Address!")).inRoot(withDecorView(Matchers.not(resetPasswordActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}
