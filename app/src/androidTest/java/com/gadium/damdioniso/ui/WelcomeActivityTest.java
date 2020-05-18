package com.gadium.damdioniso.ui;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.gadium.damdioniso.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * IMPORTANTE ||| El SplashScreen sólo se mostrará la primera vez que ejecutemos la aplicación si pulsamos en Finalizar para arrancar el siguiente Activity
 * Si es el caso, será necesario desinstalar la app para hacer los tres primeros test, que corresponden al funcionamiento del SplashScreen.
 */
@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> welcomeActivityActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    /**
     * Test para comprobar que tras el primer click en el botón de Siguiente podemos ver el botón de Atrás
     */
    @Test
    public void ensurePageChange(){
        onView(withId(R.id.buttonNxt))
                .perform(click());
        onView(withId(R.id.buttonPrv)).check(matches((isDisplayed())));
    }

    /**
     * Test para comprobar que tras pasar dos páginas del SplashScreen el botón de Siguiente pasa a mostrar "Finalizar"
     */
    @Test
    public void ensureTextButtonNextChange(){
        onView(withId(R.id.buttonNxt))
                .perform(click()).perform(click());
        onView(withId(R.id.buttonNxt)).check(matches(withText("Finalizar")));
    }

    /**
     * Test para comprobar que tras pulsar en Finalizar el WellcomeActivity se carga el MainActivity
     */
    @Test
    public void ensurePageClose(){
        onView(withId(R.id.buttonNxt))
                .perform(click()).perform(click()).perform(click());
        onView(withId(R.id.buttonNxt))
                .check(doesNotExist());
        onView(withId(R.id.fabAll))
                .check(matches(isDisplayed()));
    }

    /**
     * Test para comprobar que tras pulsar en el FloatButton del MainAcitivity se depsliegan los otros dos
     */
    @Test
    public void ensureWrapButtonOpen(){
        onView(withId(R.id.fabAll))
                .perform(click());
        onView(withId(R.id.fabRed))
                .check(matches(isClickable()));
        onView(withId(R.id.fabFav))
                .check(matches(isClickable()));
    }

}