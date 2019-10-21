package com.example.chatchat.Activities;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.chatchat.ImageUpload;
import com.example.chatchat.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

@LargeTest
public class ImageUploadTest {
    @Rule
    public ActivityTestRule<ImageUpload> ImageUploadActivityTestRule = new ActivityTestRule<>(ImageUpload.class);
    private ImageUpload ImageUpload;
    @Before
    public void setUp() {
        ImageUpload = ImageUploadActivityTestRule.getActivity();
    }

    @Test
    public void Choose_Button_test()
    {
        assertNotNull(ImageUpload.findViewById(R.id.ImageUpload_Choose));
        assertNotNull(ImageUpload.findViewById(R.id.ImageUpload_Upload));
        assertNotNull(onView(withId(R.id.ImageUpload_Progressbar)));
        assertNotNull(onView(withId(R.id.ImageUpload_Image)));
        onView(withId(R.id.ImageUpload_Choose)).perform(click());
    }
}
