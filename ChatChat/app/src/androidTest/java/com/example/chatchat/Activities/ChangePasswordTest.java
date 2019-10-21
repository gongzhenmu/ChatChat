package com.example.chatchat.Activities;

import android.content.Intent;

import com.example.chatchat.ChangePasswordActivity;
import com.example.chatchat.ChangeUserNameActivity;
import com.example.chatchat.LoginActivity;
import com.example.chatchat.R;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.TypeTextAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

@LargeTest
public class ChangePasswordTest {
    @Rule
    public ActivityTestRule<ChangePasswordActivity> changePasswordActivityTestRule = new ActivityTestRule<>(ChangePasswordActivity.class);
    private ChangePasswordActivity changePasswordActivity = null;
    public ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception
    {
        changePasswordActivity = changePasswordActivityTestRule.getActivity();
    }

    @Test
    public void component_test(){
        assertNotNull(changePasswordActivity.findViewById(R.id.btnChange_passWord));
        assertNotNull(onView(withId(R.id.txtNewPassAgain)));
        assertNotNull(onView(withId(R.id.txtPassword)));
        assertNotNull(onView(withId(R.id.txtNewPass)));
    }
    /*
    @Test
    public void empty_password_test()
    {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        onView(withId(R.id.email)).perform(new TypeTextAction("user1@email.com"));
        onView(withId(R.id.password)).perform(closeSoftKeyboard());
        onView(withId(R.id.password)).perform(new TypeTextAction("12345678"));
        onView(withId(R.id.password)).perform(closeSoftKeyboard());
        onView(withId(R.id.login_bt)).perform(click());
        onView(withId(R.id.login_bt)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.explore_recyclerview))
                .perform(actionOnItemAtPosition(2, click()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.profile_recyclerview)).perform(actionOnItemAtPosition(2, click()));
        onView(withId(R.id.txtPassword)).perform(typeText("12345678"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.txtNewPass)).perform(typeText("12345677777777777777777777777777777777777777777"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.txtNewPassAgain)).perform(typeText("12345677777777777777777777777777777777777777777"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnChange_passWord)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Error. Password too long")).inRoot(withDecorView(Matchers.not(changePasswordActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        activityRule.finishActivity();
    }*/
}
