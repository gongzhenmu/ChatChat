package com.example.chatchat.Activities;

import com.example.chatchat.ExploreActivity;
import com.example.chatchat.R;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class ExploreActivityTest {

    @Rule
    public ActivityTestRule<ExploreActivity> exploreActivityTestRule = new ActivityTestRule<>(ExploreActivity.class);
    private ExploreActivity exploreActivity = null;


    @Before
    public void setUp() throws Exception
    {
        exploreActivity = exploreActivityTestRule.getActivity();
    }

    @Test
    public void create_Button_test()
    {
        assertNotNull(exploreActivity.findViewById(R.id.explore_create));
        onView(withId(R.id.explore_create)).perform(click());
    }

    @Test
    public void empty_chatname_test()
    {

        assertNotNull(exploreActivity.findViewById(R.id.explore_create));
        assertNotNull(onView(withId(R.id.createchat_button_create)));
        assertNotNull(onView(withId(R.id.createchat_category_spinner)));
        assertNotNull(onView(withId(R.id.createchat_des_text)));
        assertNotNull(onView(withId(R.id.createchat_name_text)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.explore_create)).perform(click());
        onView(withId(R.id.createchat_des_text)).perform(typeText("testing empty chatname"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.createchat_category_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Sports")))
                .perform(click());
        onView(withId(R.id.createchat_button_create)).perform(click());
        onView(withId(R.id.createchat_des_text)).perform(pressBack());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Empty Chat name or description.")).inRoot(withDecorView(Matchers.not(exploreActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));


    }

    @Test
    public void empty_description_test()
    {

        assertNotNull(exploreActivity.findViewById(R.id.explore_create));
        assertNotNull(onView(withId(R.id.createchat_button_create)));
        assertNotNull(onView(withId(R.id.createchat_category_spinner)));
        assertNotNull(onView(withId(R.id.createchat_des_text)));
        assertNotNull(onView(withId(R.id.createchat_name_text)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.explore_create)).perform(click());
        onView(withId(R.id.createchat_name_text)).perform(typeText("chatname"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.createchat_category_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Sports")))
                .perform(click());
        onView(withId(R.id.createchat_button_create)).perform(click());
        onView(withId(R.id.createchat_des_text)).perform(pressBack());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Empty Chat name or description.")).inRoot(withDecorView(Matchers.not(exploreActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void no_category_test()
    {

        assertNotNull(exploreActivity.findViewById(R.id.explore_create));
        assertNotNull(onView(withId(R.id.createchat_button_create)));
        assertNotNull(onView(withId(R.id.createchat_category_spinner)));
        assertNotNull(onView(withId(R.id.createchat_des_text)));
        assertNotNull(onView(withId(R.id.createchat_name_text)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.explore_create)).perform(click());
        onView(withId(R.id.createchat_des_text)).perform(typeText("Testing no category,"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.createchat_name_text)).perform(typeText("testing no category"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.createchat_button_create)).perform(click());
        onView(withId(R.id.createchat_des_text)).perform(pressBack());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Please select a category!")).inRoot(withDecorView(Matchers.not(exploreActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void invalid_chatname_test()
    {

        assertNotNull(exploreActivity.findViewById(R.id.explore_create));
        assertNotNull(onView(withId(R.id.createchat_button_create)));
        assertNotNull(onView(withId(R.id.createchat_category_spinner)));
        assertNotNull(onView(withId(R.id.createchat_des_text)));
        assertNotNull(onView(withId(R.id.createchat_name_text)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.explore_create)).perform(click());
        onView(withId(R.id.createchat_name_text)).perform(typeText("chatname#@#<"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.createchat_des_text)).perform(typeText("testing invalid chatname input"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.createchat_category_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Sports")))
                .perform(click());
        onView(withId(R.id.createchat_button_create)).perform(click());
        onView(withId(R.id.createchat_des_text)).perform(pressBack());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Chat name contains invalid characters!")).inRoot(withDecorView(Matchers.not(exploreActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void invalid_description_test()
    {

        assertNotNull(exploreActivity.findViewById(R.id.explore_create));
        assertNotNull(onView(withId(R.id.createchat_button_create)));
        assertNotNull(onView(withId(R.id.createchat_category_spinner)));
        assertNotNull(onView(withId(R.id.createchat_des_text)));
        assertNotNull(onView(withId(R.id.createchat_name_text)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.explore_create)).perform(click());
        onView(withId(R.id.createchat_name_text)).perform(typeText("Testing invalid description input"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.createchat_des_text)).perform(typeText("Description@#$"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.createchat_category_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Sports")))
                .perform(click());
        onView(withId(R.id.createchat_button_create)).perform(click());
        onView(withId(R.id.createchat_des_text)).perform(pressBack());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Description contains invalid characters!")).inRoot(withDecorView(Matchers.not(exploreActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }





}