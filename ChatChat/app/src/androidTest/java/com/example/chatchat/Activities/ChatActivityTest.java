package com.example.chatchat.Activities;

import com.example.chatchat.ChatActivity;
import com.example.chatchat.R;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.contrib.RecyclerViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;


public class ChatActivityTest {

    @Rule
    public ActivityTestRule<ChatActivity> chatActivityTestRule = new ActivityTestRule<>(ChatActivity.class);
    private ChatActivity chatActivity = null;

    @Before
    public void setUp() throws Exception
    {
        chatActivity = chatActivityTestRule.getActivity();

    }

    @Test
    public void empty_message_test()
    {
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_message_recyclerView));
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_message_editText));
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_button_send));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.activity_chat_button_send)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Empty message!")).inRoot(withDecorView(Matchers.not(chatActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

    @Test
    public void invalid_message_input()
    {
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_message_recyclerView));
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_message_editText));
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_button_send));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.activity_chat_message_editText)).perform(typeText("@#testing invalid input+_"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_chat_button_send)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("message contains invalid input!")).inRoot(withDecorView(Matchers.not(chatActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void message_too_long()
    {
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_message_recyclerView));
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_message_editText));
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_button_send));
        String input = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasssssssssssawdawdsdadsdawdafasdawdfsacasdqwdcafadwdacafawdascadacasdawdscasdaxsadawdscafsadffgagduudyhasuiyduashdajsfhasufhaisuydwuhedwurfhoasdowirwrhasujhduhu2y8u4hushdfjuahseudqwuy48yhuhdujashdjahsudho28485y85s";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.activity_chat_message_editText)).perform(typeText(input), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.activity_chat_button_send)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Message too long!")).inRoot(withDecorView(Matchers.not(chatActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void swipe_down_up_chat()
    {
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_message_recyclerView));
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_message_editText));
        assertNotNull(chatActivity.findViewById(R.id.activity_chat_button_send));
        
        onView(withId(R.id.activity_chat_message_recyclerView))
                .perform(ViewActions.swipeDown()).check(matches(isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.activity_chat_message_recyclerView))
                .perform(ViewActions.swipeUp()).check(matches(isDisplayed()));
    }
}