package group11.cse110.com.serviceforservice;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

/**
 * Created by David on 3/7/2016.
 */
public class FeedPostTest {

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
        Espresso.unregisterIdlingResources(idlingResource);
    }


    @Test
    public void feedPostTest() {
        onView(withId(R.id.signup)).perform(click());
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withContentDescription("Open navigation drawer")).perform(click());
        onView(withText("Sell Feed")).perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.listview))
                .atPosition(0).perform(click());

    }
}
