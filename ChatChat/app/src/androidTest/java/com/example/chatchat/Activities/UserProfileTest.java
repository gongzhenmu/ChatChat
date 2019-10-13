package com.example.chatchat.Activities;
import androidx.test.rule.ActivityTestRule;
import com.example.chatchat.R;
import com.example.chatchat.UserProfileActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class UserProfileTest {
    @Rule
    public ActivityTestRule<UserProfileActivity> UserProfileActivityTestRule = new ActivityTestRule<>(UserProfileActivity.class);
    private UserProfileActivity UserProfileActivity = null;


    @Before
    public void setUp()
    {
        UserProfileActivity = UserProfileActivityTestRule.getActivity();
    }

    @Test
    public void image_test()
    {
//        assertNotNull(onView(withId(R.id.userProfile_email)));
//        assertNotNull(onView(withId(R.id.userProfile_image)));
//        assertNotNull(UserProfileActivity.findViewById(R.id.userProfile_listTitle));
//        assertNotNull(UserProfileActivity.findViewById(R.id.userProfile_Name));
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //onView(withId(R.id.userProfile_image)).perform(click());
    }
}
