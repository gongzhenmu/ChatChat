package com.example.chatchat.Activities;

import com.example.chatchat.MainActivity;
import com.example.chatchat.R;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.action.TypeTextAction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class LoginUnitTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainPageButtonIsDisplayed() {
        onView(withId(R.id.login_bt)).check(matches(withText("LOG IN WITH EMAIL")));
        onView(withId(R.id.login_bt)).perform(click());
    }

    @Test
    public void Login() {
        onView(withId(R.id.login_bt)).perform(click());
        onView(withId(R.id.email)).perform(new TypeTextAction("user1@email.com"));
        onView(withId(R.id.password)).perform(new TypeTextAction("12345678"));
        onView(withId(R.id.password)).perform(closeSoftKeyboard());
        onView(withId(R.id.login_bt)).perform(click());
    }
}
