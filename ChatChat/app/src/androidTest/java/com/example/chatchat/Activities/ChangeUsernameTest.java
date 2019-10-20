package com.example.chatchat.Activities;
import com.example.chatchat.ChangeUserNameActivity;
import com.example.chatchat.R;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

public class ChangeUsernameTest {
    @Rule
    public ActivityTestRule<ChangeUserNameActivity> changeUserNameActivityTestRule = new ActivityTestRule<>(ChangeUserNameActivity.class);
    private ChangeUserNameActivity changeUserNameActivity = null;

    @Before
    public void setUp() throws Exception
    {
        changeUserNameActivity = changeUserNameActivityTestRule.getActivity();
    }

    @Test
    public void component_test(){
        assertNotNull(changeUserNameActivity.findViewById(R.id.confirm_newUserName));
        assertNotNull(onView(withId(R.id.newUserName)));
    }
    @Test
    public void empty_username_test()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.newUserName)).perform(typeText("   "), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirm_newUserName)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Error. Display name cannot be empty")).inRoot(withDecorView(Matchers.not(changeUserNameActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
    @Test
    public void long_username_test()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.newUserName)).perform(typeText("just a suppppppppppppper loooooooooooooooong passssssssssssssssword"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirm_newUserName)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Error. chat ID too long")).inRoot(withDecorView(Matchers.not(changeUserNameActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void invalid_username_test()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.newUserName)).perform(typeText("@$%&*&^&%%#$@"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirm_newUserName)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Error. Invalid chat ID")).inRoot(withDecorView(Matchers.not(changeUserNameActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}
