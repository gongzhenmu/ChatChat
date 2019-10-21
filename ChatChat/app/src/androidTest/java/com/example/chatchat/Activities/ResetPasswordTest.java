package com.example.chatchat.Activities;

import android.content.Intent;

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
    private ResetPasswordActivity resetActivity;
    @Before
    public void setUp() throws Exception
    {
        resetActivity = ResetPasswordActivityTestRule.getActivity();

    }
    @Test
    public void invalid_email()
    {
        Intent intent = new Intent();
        ResetPasswordActivityTestRule.launchActivity(intent);
        assertNotNull(resetActivity.findViewById(R.id.reset_password_email_editText));
        assertNotNull(resetActivity.findViewById(R.id.reset_password_send_button));
        assertNotNull(resetActivity.findViewById(R.id.reset_password_resend_button));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.reset_password_email_editText)).perform(typeText("aaaaaaaaaaaaa"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.reset_password_send_button)).perform(click());
        //onView(withText("Not a valid email address")).inRoot(withDecorView(Matchers.not(resetActivity.getWindow().getDecorView())))
        //       .check(matches(isDisplayed()));
        ResetPasswordActivityTestRule.finishActivity();
    }

    @Test
    public void resend_more_than_three_times()
    {
        Intent intent = new Intent();
        ResetPasswordActivityTestRule.launchActivity(intent);
        assertNotNull(resetActivity.findViewById(R.id.reset_password_email_editText));
        assertNotNull(resetActivity.findViewById(R.id.reset_password_send_button));
        assertNotNull(resetActivity.findViewById(R.id.reset_password_resend_button));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.reset_password_email_editText)).perform(typeText("hello123@gmail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.reset_password_send_button)).perform(click());
        onView(withId(R.id.reset_password_resend_button)).perform(click());
        onView(withId(R.id.reset_password_resend_button)).perform(click());
        onView(withId(R.id.reset_password_resend_button)).perform(click());
        onView(withId(R.id.reset_password_resend_button)).perform(click());
        onView(withText("An email has been sent 3 times, please check your inbox")).inRoot(withDecorView(Matchers.not(resetActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        ResetPasswordActivityTestRule.finishActivity();
    }

    @Test
    public void empty_email()
    {
        Intent intent = new Intent();
        ResetPasswordActivityTestRule.launchActivity(intent);
        onView(withId(R.id.reset_password_email_editText)).perform(typeText(""), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.reset_password_send_button)).perform(click());
        ResetPasswordActivityTestRule.finishActivity();
    }

    @Test
    public void invalid_character_for_email_address()
    {
        Intent intent = new Intent();
        ResetPasswordActivityTestRule.launchActivity(intent);
        onView(withId(R.id.reset_password_email_editText)).perform(typeText("$^&"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.reset_password_send_button)).perform(click());
        ResetPasswordActivityTestRule.finishActivity();
    }
}
