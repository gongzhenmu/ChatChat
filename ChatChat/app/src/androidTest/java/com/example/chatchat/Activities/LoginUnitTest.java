package com.example.chatchat.Activities;

import android.content.Intent;

import com.example.chatchat.LoginActivity;
import com.example.chatchat.R;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.action.TypeTextAction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@LargeTest
public class LoginUnitTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void login_with_correct_info() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        onView(withId(R.id.email)).perform(new TypeTextAction("user1@email.com"));
        onView(withId(R.id.email)).perform(closeSoftKeyboard());
        onView(withId(R.id.password)).perform(new TypeTextAction("12345678"));
        onView(withId(R.id.password)).perform(closeSoftKeyboard());
        onView(withId(R.id.login_bt)).perform(click());
        activityRule.finishActivity();
    }

    @Test
    public void login_with_wrong_pw() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        onView(withId(R.id.email)).perform(new TypeTextAction("user1@email.com"));
        onView(withId(R.id.email)).perform(closeSoftKeyboard());
        onView(withId(R.id.password)).perform(new TypeTextAction("0000000000"));
        onView(withId(R.id.password)).perform(closeSoftKeyboard());
        onView(withId(R.id.login_bt)).perform(click());
        activityRule.finishActivity();
    }

    @Test
    public void login_with_wrong_account() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        onView(withId(R.id.email)).perform(new TypeTextAction("userWrong@email.com"));
        onView(withId(R.id.email)).perform(closeSoftKeyboard());
        onView(withId(R.id.password)).perform(new TypeTextAction("12345678"));
        onView(withId(R.id.password)).perform(closeSoftKeyboard());
        onView(withId(R.id.login_bt)).perform(click());
        activityRule.finishActivity();

    }

    @Test
    public void login_without_pw() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        onView(withId(R.id.email)).perform(new TypeTextAction("user1@email.com"));
        onView(withId(R.id.email)).perform(closeSoftKeyboard());
        onView(withId(R.id.login_bt)).perform(click());
        activityRule.finishActivity();

    }

    @Test
    public void login_without_account() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        onView(withId(R.id.password)).perform(new TypeTextAction("12345678"));
        onView(withId(R.id.password)).perform(closeSoftKeyboard());
        onView(withId(R.id.login_bt)).perform(click());
        activityRule.finishActivity();
    }

}
