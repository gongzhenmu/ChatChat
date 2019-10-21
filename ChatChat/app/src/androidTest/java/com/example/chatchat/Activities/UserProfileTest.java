//package com.example.chatchat.Activities;
//import android.content.Intent;
//
//import androidx.test.rule.ActivityTestRule;
//import com.example.chatchat.R;
//import com.example.chatchat.UserProfileActivity;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static org.junit.Assert.assertNotNull;
//
//public class UserProfileTest {
//    @Rule
//    public ActivityTestRule<UserProfileActivity> UserProfileActivityTestRule = new ActivityTestRule<>(UserProfileActivity.class);
//
//
//    @Test
//    public void functionality()
//    {
//        assertNotNull(onView(withId(R.id.userProfile_email)));
//        assertNotNull(onView(withId(R.id.userProfile_image)));
//        assertNotNull(onView(withId(R.id.userProfile_Name)));
//
//    }
//
////    @Test
////    public void nameTest(){
////        Intent intent = new Intent();
////        intent.putExtra("UID","OZ3Q3M38JSMdIn0qiiqjcqlEY4M2");
////        UserProfileActivityTestRule.launchActivity(intent);
////
////        onView(withId(R.id.userProfile_Name)).check(matches(isDisplayed()));
////        //onView(withId(R.id.userProfile_Name)).check(matches(withText("123")));
////        UserProfileActivityTestRule.finishActivity();
////
////    }
////    @Test
////    public void emailTest(){
////        Intent intent = new Intent();
////        intent.putExtra("UID","OZ3Q3M38JSMdIn0qiiqjcqlEY4M2");
////        UserProfileActivityTestRule.launchActivity(intent);
////
////        onView(withId(R.id.userProfile_Name)).check(matches(isDisplayed()));
////        //onView(withId(R.id.userProfile_email)).check(matches(withText("gongzhenmu@163.com")));
////        UserProfileActivityTestRule.finishActivity();
////
////    }
////    @Test
////    public void name1Test(){
////        Intent intent = new Intent();
////        intent.putExtra("UID","B1c9PZOlFfhO5Csa9DjMlvj8k422");
////        UserProfileActivityTestRule.launchActivity(intent);
////
////        onView(withId(R.id.userProfile_Name)).check(matches(isDisplayed()));
////        //onView(withId(R.id.userProfile_Name)).check(matches(withText("user3")));
////        UserProfileActivityTestRule.finishActivity();
////
////    }
////    @Test
////    public void email1Test(){
////        Intent intent = new Intent();
////        intent.putExtra("UID","B1c9PZOlFfhO5Csa9DjMlvj8k422");
////        UserProfileActivityTestRule.launchActivity(intent);
////
////        onView(withId(R.id.userProfile_Name)).check(matches(isDisplayed()));
////        //onView(withId(R.id.userProfile_email)).check(matches(withText("user3@email.com")));
////        UserProfileActivityTestRule.finishActivity();
////
////    }
//
//}