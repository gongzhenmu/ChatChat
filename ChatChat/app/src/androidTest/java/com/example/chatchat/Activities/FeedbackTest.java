package com.example.chatchat.Activities;

import android.content.Intent;

import com.example.chatchat.FeedbackActivity;
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
public class FeedbackTest {
    @Rule
    public ActivityTestRule<FeedbackActivity> activityRule
            = new ActivityTestRule<>(FeedbackActivity.class);
    @Test
    public void feedback_without_bug(){
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        onView(withId(R.id.featureSuggestion)).perform(new TypeTextAction("everything is perfect"));
        onView(withId(R.id.featureSuggestion)).perform(closeSoftKeyboard());
        onView(withId(R.id.sendFeedbackButton)).perform(click());
        activityRule.finishActivity();
    }

    @Test
    public void feedback_without_feature(){
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        onView(withId(R.id.bugfeedBack)).perform(new TypeTextAction("no bugs"));
        onView(withId(R.id.bugfeedBack)).perform(closeSoftKeyboard());
        onView(withId(R.id.sendFeedbackButton)).perform(click());
        activityRule.finishActivity();
    }
    @Test
    public void feedback(){
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        onView(withId(R.id.bugfeedBack)).perform(new TypeTextAction("no bugs"));
        onView(withId(R.id.bugfeedBack)).perform(closeSoftKeyboard());
        onView(withId(R.id.featureSuggestion)).perform(new TypeTextAction("everything is perfect"));
        onView(withId(R.id.featureSuggestion)).perform(closeSoftKeyboard());
        onView(withId(R.id.sendFeedbackButton)).perform(click());
        activityRule.finishActivity();
    }


}
