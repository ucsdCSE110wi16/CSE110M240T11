package group11.cse110.com.serviceforservice;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


/**
 * Created by David on 3/6/2016.
 * Test for posting a sell post
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SellPostTest {

    @Rule
    public ActivityTestRule<HomePage> mActivityRule = new ActivityTestRule(HomePage.class);

    @Test
    public void sellPostTest() {
        onView(withContentDescription("Open navigation drawer")).perform(click());
        onView(withText("Create Sell Post")).perform(click());
        onView(withId(R.id.sellFood)).perform(click());
        onView(withText("Continue")).perform(click());
        onView(withId(R.id.housingCheck)).perform(click());
        onView(withText("Continue")).perform(click());
        onView(withId(R.id.textEditForAdditionalInfo)).perform(replaceText("Anything is fine."));
        onView(withId(R.id.submitButton)).perform(click());
    }
}
