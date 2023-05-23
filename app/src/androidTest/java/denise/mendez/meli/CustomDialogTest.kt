package denise.mendez.meli

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import denise.mendez.meli.modules.search.view.SearchFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CustomDialogTest {

    @get:Rule
    val activityRule = launchFragmentInContainer<SearchFragment>()

    @Test
    fun checkViewsDisplay() {
        onView(withId(R.id.text_view_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_view_message)).check(matches(isDisplayed()))
        onView(withId(R.id.image_view_dialog_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.button_negative)).check(matches(isDisplayed()))
        onView(withId(R.id.button_positive)).check(matches(isDisplayed()))
    }

    @Test
    fun checkTextViewsText() {
        onView(withId(R.id.text_view_title)).check(matches(withText("Title Text")))
        onView(withId(R.id.text_view_message)).check(matches(withText("Message Text")))
    }

    @Test
    fun checkButtonClick() {
        onView(withId(R.id.button_negative)).perform(click())
        onView(withId(R.id.button_positive)).perform(click())
    }
}
