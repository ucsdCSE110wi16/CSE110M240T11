package group11.cse110.com.serviceforservice;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by David on 3/4/2016.\
 * Test for Login
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    @Rule
    public ActivityTestRule<StartScreen> mActivityRule = new ActivityTestRule(StartScreen.class);
    private LoginIdlingResource idlingResource;
    @Before
    public void registerIntentServiceIdlingResource() {
        Parse.enableLocalDatastore(mActivityRule.getActivity());
        Parse.initialize(mActivityRule.getActivity());

        ParseFacebookUtils.initialize(mActivityRule.getActivity());
        idlingResource = new LoginIdlingResource(mActivityRule.getActivity());
        Espresso.registerIdlingResources(idlingResource);
    }

    @After
    public void unregisterIntentServiceIdlingResource() {
        if(idlingResource != null)
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void loginWithFacebook() {
        onView(withId(R.id.signup)).perform(click());
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }
}

