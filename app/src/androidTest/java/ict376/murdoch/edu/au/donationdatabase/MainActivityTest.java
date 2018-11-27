package ict376.murdoch.edu.au.donationdatabase;

import android.app.Fragment;
import android.content.ComponentName;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);


    private MainActivity mActivity = null;
    private ContactListFragment clFragment = null;

    @Before
    public void init() {

        mActivity = mainActivityTestRule.getActivity();

        FrameLayout f = mActivity.findViewById(R.id.contactlist_fragment_container);

        clFragment = new ContactListFragment();

        mActivity.getFragmentManager().beginTransaction()
                .remove(clFragment)
                .add(f.getId(), clFragment).commit();

        getInstrumentation().waitForIdleSync();


    }

    @Test
    public void checkIfFragmentThere() {

        View view = clFragment.getView().findViewById(R.id.total_amount);

        assertNotNull(view);

    }

    @Test
    public void check() {

        Intents.init();

        Button button = clFragment.getView().findViewById(R.id.new_donor_button);

        assert(button.isShown());

        onView(withId(button.getId()))
              .perform(click());

        intended(hasComponent(new ComponentName(getTargetContext(), DisplayContact.class)));
        //intended(hasComponent(DisplayContact.class.getName()));

        Intents.release();

    }

    @After
    public void tearDown() throws Exception {

        mActivity = null;

    }

}

/*
Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", 0);
                    Intent intent = new Intent(getActivity().getApplicationContext(), DisplayContact.class);
                    intent.putExtras(dataBundle);

                    java.lang.NullPointerException: Attempt to invoke virtual method 'void android.support.test.espresso.intent.Intents.internalIntended(org.hamcrest.Matcher, android.support.test.espresso.intent.VerificationMode, java.util.List)' on a null object reference

 */