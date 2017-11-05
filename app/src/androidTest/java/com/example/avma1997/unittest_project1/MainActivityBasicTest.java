package com.example.avma1997.unittest_project1;

import android.provider.ContactsContract;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Avma1997 on 11/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
    @Rule public ActivityTestRule<MainActivity> mActivityTestRule=new ActivityTestRule<>(MainActivity.class);
    @Test
    public void clickQRCodeButton_OpenScanningActivity()
    {


        Intents.init();
        // Find The View
        //Perform Action On the View
        onView((withId(R.id.scan_qr))).perform(click());
        // Check if the View does what you expects
        intended(hasComponent(ScanningActivity.class.getName()));
        Intents.release();

    }


}
