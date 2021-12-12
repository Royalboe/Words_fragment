package com.example.wordsapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.runner.AndroidJUnit4
import com.example.wordsapp.letter.LetterListFragment
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavigationTests {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun navigate_to_word(){
        onView(withText("C")).perform(click())

//        onView(withId(R.id.recycler_view)).perform(
//            RecyclerViewActions
//                .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
//        )
        onView(withText("Words That Start With C")).check(matches(isDisplayed()))
    }

    @Test
    fun navigate_to_words_nav_component() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        val letterFragmentScenario = launchFragmentInContainer<LetterListFragment>(
            themeResId = R.style.Theme_Words
        )
        letterFragmentScenario.onFragment{
                fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )

        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)
    }
}