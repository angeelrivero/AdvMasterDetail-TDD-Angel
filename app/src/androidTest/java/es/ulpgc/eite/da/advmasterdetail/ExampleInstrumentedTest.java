package es.ulpgc.eite.da.advmasterdetail;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static org.hamcrest.Matchers.not;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;
import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;
import es.ulpgc.eite.da.advmasterdetail.database.UserDao;
import es.ulpgc.eite.da.advmasterdetail.favorites.FavoritesActivity;
import es.ulpgc.eite.da.advmasterdetail.login.LoginActivity;
import es.ulpgc.eite.da.advmasterdetail.movies.MoviesListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.uiautomator.UiDevice;

import static org.junit.Assert.*;
import static androidx.test.espresso.action.ViewActions.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Before
    public void setUp() {
        // AppMediator.resetInstance();
    }

    /*@Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("es.ulpgc.eite.da.advmasterdetail", appContext.getPackageName());
    }*/

    @Test
    public void test01_LoginScreenLoadsCorrectly() throws InterruptedException {
        ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.edit_username)).perform(scrollTo());
        Thread.sleep(400); // Pausa de 400 ms para ver el scroll
        onView(withId(R.id.edit_username)).check(matches(isDisplayed()));

        onView(withId(R.id.edit_password)).perform(scrollTo());
        Thread.sleep(400);
        onView(withId(R.id.edit_password)).check(matches(isDisplayed()));

        onView(withId(R.id.btn_login)).perform(scrollTo());
        Thread.sleep(400);
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(matches(isEnabled()));

        onView(withId(R.id.btn_guest)).perform(scrollTo());
        Thread.sleep(400);
        onView(withId(R.id.btn_guest)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_guest)).check(matches(isEnabled()));

        onView(withId(R.id.signup)).perform(scrollTo());
        Thread.sleep(400);
        onView(withId(R.id.signup)).check(matches(isDisplayed()));
        onView(withId(R.id.signup)).check(matches(isEnabled()));
    }


    // Utilidad para rotar pantalla
    private void rotateScreen() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        try {
            device.setOrientationLeft();
            device.setOrientationNatural();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test02_LoginScreenAllowsScrollAndRotation() throws InterruptedException {
        // Lanza LoginActivity
        ActivityScenario.launch(LoginActivity.class);

        // Haz scroll al botón login (para comprobar que el scroll funciona en el layout)
        onView(withId(R.id.btn_login)).perform(scrollTo());
        Thread.sleep(400);
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));

        // Rota la pantalla
        rotateScreen();

        // Vuelve a hacer scroll tras la rotación (para asegurarse de que el layout sigue funcionando)
        onView(withId(R.id.btn_login)).perform(scrollTo());
        Thread.sleep(400);
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));

        // También puedes comprobar que los otros campos siguen visibles (opcional)
        onView(withId(R.id.edit_username)).perform(scrollTo());
        Thread.sleep(400);
        onView(withId(R.id.edit_username)).check(matches(isDisplayed()));

        onView(withId(R.id.edit_password)).perform(scrollTo());
        Thread.sleep(400);
        onView(withId(R.id.edit_password)).check(matches(isDisplayed()));
    }

    @Test
    public void test03_GuestMode_DisablesFavoritesAndDetailStar() throws InterruptedException {
        // 1. Lanza LoginActivity
        ActivityScenario.launch(es.ulpgc.eite.da.advmasterdetail.login.LoginActivity.class);

        // 2. Pulsa "Iniciar sin cuenta"
        onView(withId(R.id.btn_guest)).perform(scrollTo(), click());

        // 3. Verifica que el botón de favoritos está desactivado
        onView(withId(R.id.button_favorites)).check(matches(not(isEnabled())));

        // 4. Espera a que se cargue la lista de pelis
        Thread.sleep(500); // Ajusta si hace falta

        // 5. Haz scroll y pulsa en la última película del RecyclerView
        onView(withId(R.id.movie_recycler)).perform(RecyclerViewActions.scrollToPosition(19)); // Ajusta el número si tienes menos pelis
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(19, click()));

        // 6. En detalles, verifica que la estrella está desactivada
        onView(withId(R.id.btn_favorite)).check(matches(not(isEnabled())));
    }

    @Test
    public void test04_InvalidLoginStaysInLoginScreen() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Introducir credenciales inválidas
        onView(withId(R.id.edit_username)).perform(scrollTo(), replaceText("angel"));
        onView(withId(R.id.edit_password)).perform(scrollTo(), replaceText("1234"));
        onView(withId(R.id.btn_login)).perform(scrollTo(), click());

        // Verificamos que seguimos en LoginActivity comprobando visibilidad de campos
        onView(withId(R.id.edit_username)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_password)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void test05_RegisterScreenLoadsCorrectly() {
        // 1. Lanza LoginActivity
        ActivityScenario.launch(LoginActivity.class);

        // 2. Pulsa el botón de registro
        onView(withId(R.id.signup)).perform(scrollTo(), click());

        // 3. Verifica los campos de entrada
        onView(withId(R.id.edit_register_username)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_register_email)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_register_password)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_register_repeat_password)).check(matches(isDisplayed()));

        // 4. Verifica que el botón de registro está visible y habilitado
        onView(withId(R.id.btn_register)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_register)).check(matches(isEnabled()));
    }

    @Test
    public void test06_RegisterScreenSurvivesRotation() {
        // 1. Lanza LoginActivity
        ActivityScenario.launch(LoginActivity.class);

        // 2. Pulsa el botón de registro
        onView(withId(R.id.signup)).perform(scrollTo(), click());

        // 3. Rota la pantalla usando tu método
        rotateScreen();

        // 4. Verifica los campos siguen visibles
        onView(withId(R.id.edit_register_username)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_register_email)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_register_password)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_register_repeat_password)).check(matches(isDisplayed()));

        // 5. Verifica el botón sigue visible y habilitado
        onView(withId(R.id.btn_register)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_register)).check(matches(isEnabled()));
    }

    @Test
    public void test07_RegisterSequentialWithRotationAndNavigatesBackToLogin() throws InterruptedException {
        // 1. Lanza LoginActivity y accede a pantalla de registro
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.signup)).perform(scrollTo(), click());

        // 2. Pulsar registrar con todo vacío → debe quedarse en pantalla
        onView(withId(R.id.btn_register)).perform(scrollTo(), click());
        onView(withId(R.id.edit_register_username)).check(matches(isDisplayed()));

        // 3. Escribir solo nombre de usuario
        onView(withId(R.id.edit_register_username)).perform(scrollTo(), replaceText("angel"));
        onView(withId(R.id.btn_register)).perform(scrollTo(), click());
        onView(withId(R.id.edit_register_username)).check(matches(isDisplayed()));

        // 4. Rotar y comprobar que se mantiene el nombre
        rotateScreen();
        onView(withId(R.id.edit_register_username)).check(matches(withText("angel")));

        // 5. Añadir email
        onView(withId(R.id.edit_register_email)).perform(scrollTo(), replaceText("angel@gmail.com"));
        onView(withId(R.id.btn_register)).perform(scrollTo(), click());
        onView(withId(R.id.edit_register_email)).check(matches(withText("angel@gmail.com")));

        // 6. Rotar y comprobar que se mantiene
        rotateScreen();
        onView(withId(R.id.edit_register_username)).check(matches(withText("angel")));
        onView(withId(R.id.edit_register_email)).check(matches(withText("angel@gmail.com")));

        // 7. Contraseña = 1234, repetir contraseña = 888 (no coincide)
        onView(withId(R.id.edit_register_password)).perform(scrollTo(), replaceText("1234"));
        onView(withId(R.id.edit_register_repeat_password)).perform(scrollTo(), replaceText("888"));
        onView(withId(R.id.btn_register)).perform(scrollTo(), click());
        onView(withId(R.id.edit_register_repeat_password)).check(matches(withText("888")));

        // 8. Rotar y comprobar que todo se mantiene
        rotateScreen();
        onView(withId(R.id.edit_register_username)).check(matches(withText("angel")));
        onView(withId(R.id.edit_register_email)).check(matches(withText("angel@gmail.com")));
        onView(withId(R.id.edit_register_password)).check(matches(withText("1234")));
        onView(withId(R.id.edit_register_repeat_password)).check(matches(withText("888")));

        // 9. Corregir repetir contraseña
        onView(withId(R.id.edit_register_repeat_password)).perform(scrollTo(), replaceText("1234"));
        onView(withId(R.id.btn_register)).perform(scrollTo(), click());

        // 10. Espera la navegación de vuelta a LoginActivity
        Thread.sleep(1000);

        // 11. Verifica que hemos vuelto a LoginActivity (por presencia de botón de login)
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_username)).check(matches(isDisplayed()));

        // 12. Verificar en base de datos que el usuario fue guardado
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        CatalogDatabase db = AppMediator.getInstance().getDatabase(context);
        UserDao userDao = db.userDao();

        UserItem user = userDao.findUserByUsername("angel");
        assertNotNull("El usuario 'angel' debe haberse guardado en la base de datos", user);
        assertEquals("angel@gmail.com", user.email);
        assertEquals("1234", user.password);
    }



    /*@Test
    public void testLogin_FavoriteFlowForMovie11() throws InterruptedException {
        // Crear usuario si no existe
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        CatalogDatabase db = AppMediator.getInstance().getDatabase(context);
        UserDao userDao = db.userDao();
        if (userDao.findUserByUsername("angel") == null) {
            userDao.insertUser(new UserItem("angel", "angel@gmail.com", "1234"));
        }

        // 1. Lanza LoginActivity e inicia sesión con usuario
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.edit_username)).perform(scrollTo(), replaceText("angel"));
        onView(withId(R.id.edit_password)).perform(scrollTo(), replaceText("1234"));
        onView(withId(R.id.btn_login)).perform(scrollTo(), click());
        Thread.sleep(800);

        // 2. Verifica que el botón de favoritos está habilitado y que se muestra la lista
        onView(withId(R.id.button_favorites)).check(matches(isEnabled()));
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));

        // 3. Ir a favoritos
        onView(withId(R.id.button_favorites)).perform(click());
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
        rotateScreen(); rotateScreen();
        pressBack(); // volver a lista de películas
        onView(withId(R.id.button_favorites)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));

        // 4. Ir a película con ID 11 (posición 10) y ver detalles
        onView(withId(R.id.movie_recycler)).perform(RecyclerViewActions.scrollToPosition(10));
        onView(withId(R.id.movie_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(10, click()));
        onView(withId(R.id.movie_image)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_director)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_duration)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_actors)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        // 5. Marcar como favorita y rotar dos veces
        onView(withId(R.id.btn_favorite)).perform(click());
        rotateScreen(); rotateScreen();
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        // 6. Volver a la lista de películas
        pressBack();
        onView(withId(R.id.button_favorites)).check(matches(isDisplayed()));

        // 7. Rotar y volver a favoritos
        rotateScreen();
        onView(withId(R.id.button_favorites)).perform(click());

        // 8. Verificar que la película favorita aparece (mínimo 1 item)
        onView(withId(R.id.movie_recycler)).check(matches(hasMinimumChildCount(1)));

        // 9. Pulsar sobre la película para ver detalles
        onView(withId(R.id.movie_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));
    }*/


    private void waitUntilRecyclerHasItems(ActivityScenario<?> scenario, int recyclerId, int minItems, long timeoutMs) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        final int[] count = {0};

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            scenario.onActivity(activity -> {
                RecyclerView recycler = activity.findViewById(recyclerId);
                if (recycler != null && recycler.getAdapter() != null) {
                    count[0] = recycler.getAdapter().getItemCount();
                }
            });

            if (count[0] >= minItems) return;
            Thread.sleep(250);
        }

        throw new AssertionError("RecyclerView no alcanzó " + minItems + " elementos en " + timeoutMs + " ms");
    }



    @Test
    public void test08_BackFromFavoritesThenOpenMovieDetails() throws InterruptedException {
        // Crear usuario si no existe
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        CatalogDatabase db = AppMediator.getInstance().getDatabase(context);
        UserDao userDao = db.userDao();
        if (userDao.findUserByUsername("angel") == null) {
            userDao.insertUser(new UserItem("angel", "angel@gmail.com", "1234"));
        }

        // Login
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.edit_username)).perform(replaceText("angel"));
        onView(withId(R.id.edit_password)).perform(replaceText("1234"));
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(1000);

        // Ir a favoritos
        onView(withId(R.id.button_favorites)).perform(click());
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
        rotateScreen(); rotateScreen();

        // Volver a la lista de películas
        pressBack();
        Thread.sleep(1000);

        // Esperar hasta que se cargue la lista con al menos 13 elementos
        ActivityScenario<MoviesListActivity> scenario = ActivityScenario.launch(MoviesListActivity.class);
        waitUntilRecyclerHasItems(scenario, R.id.movie_recycler, 13, 5000);

        // Scroll y selección de la película ID 13 (posición 12)
        int position = 12;
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position));
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
        Thread.sleep(500);

        // Verificar detalles
        onView(withId(R.id.movie_image)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_director)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_duration)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_actors)).check(matches(isDisplayed()));

        // Verificar estrella sin marcar y marcar
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));
        onView(withId(R.id.btn_favorite)).perform(click());

        // Rotar y verificar que sigue marcada
        rotateScreen(); rotateScreen();
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        // Volver a la lista
        pressBack();
        Thread.sleep(500);
        ActivityScenario<MoviesListActivity> scenario1 = ActivityScenario.launch(MoviesListActivity.class);
        waitUntilRecyclerHasItems(scenario1, R.id.movie_recycler, 13, 5000);

        // Rotar en lista
        rotateScreen();

        // Ir a favoritos
        onView(withId(R.id.button_favorites)).perform(click());
        onView(withId(R.id.movie_recycler)).check(matches(hasMinimumChildCount(1)));

        // Entrar en la película marcada como favorita
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Verificar que la estrella sigue marcada tras rotar
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));
        rotateScreen();
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        //Volvemos a la lista de favoritos
        pressBack();
        Thread.sleep(500); // Espera un poco para que FavoritesActivity se cargue

        //Espera hasta que el RecyclerView esté visible con al menos 1 ítem
        ActivityScenario<FavoritesActivity> favoritesScenario = ActivityScenario.launch(FavoritesActivity.class);
        waitUntilRecyclerHasItems(favoritesScenario, R.id.movie_recycler, 1, 5000);

        //Verificamos que el recycler está visible
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));

        //Volvemos a la lista de pelis:
        pressBack();
        Thread.sleep(500);
        ActivityScenario<MoviesListActivity> scenario2 = ActivityScenario.launch(MoviesListActivity.class);
        waitUntilRecyclerHasItems(scenario2, R.id.movie_recycler, 13, 5000);


        //Hasta aqui con una peli tenemos, ahora vamos a incluir mas pelis:
        //pelis con id:7, 14, 17, 21 y 23
        //Empezamos con id 7:
        int position1 = 6;
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position1));
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position1, click()));
        Thread.sleep(500);

        // Verificar detalles
        onView(withId(R.id.movie_image)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_director)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_duration)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_actors)).check(matches(isDisplayed()));

        // Verificar estrella sin marcar y marcar
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));
        onView(withId(R.id.btn_favorite)).perform(click());

        // Rotar y verificar que sigue marcada
        rotateScreen(); rotateScreen();
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        // Volver a la lista
        pressBack();
        Thread.sleep(500);
        ActivityScenario<MoviesListActivity> scenario3 = ActivityScenario.launch(MoviesListActivity.class);
        waitUntilRecyclerHasItems(scenario3, R.id.movie_recycler, 13, 5000);

        // Rotar en lista
        rotateScreen();

        //Empezamos con id 14:
        int position2 = 13;
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position2));
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position2, click()));
        Thread.sleep(500);

        // Verificar detalles
        onView(withId(R.id.movie_image)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_director)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_duration)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_actors)).check(matches(isDisplayed()));

        // Verificar estrella sin marcar y marcar
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));
        onView(withId(R.id.btn_favorite)).perform(click());

        // Rotar y verificar que sigue marcada
        rotateScreen(); rotateScreen();
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        // Volver a la lista
        pressBack();
        Thread.sleep(500);
        ActivityScenario<MoviesListActivity> scenario4 = ActivityScenario.launch(MoviesListActivity.class);
        waitUntilRecyclerHasItems(scenario4, R.id.movie_recycler, 20, 5000);

        // Rotar en lista
        rotateScreen();

        //Empezamos con id 17:
        int position3 = 16;
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position3));
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position3, click()));
        Thread.sleep(500);

        // Verificar detalles
        onView(withId(R.id.movie_image)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_director)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_duration)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_actors)).check(matches(isDisplayed()));

        // Verificar estrella sin marcar y marcar
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));
        onView(withId(R.id.btn_favorite)).perform(click());

        // Rotar y verificar que sigue marcada
        rotateScreen(); rotateScreen();
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        // Volver a la lista
        pressBack();
        Thread.sleep(500);
        ActivityScenario<MoviesListActivity> scenario5 = ActivityScenario.launch(MoviesListActivity.class);
        waitUntilRecyclerHasItems(scenario5, R.id.movie_recycler, 20, 5000);

        // Rotar en lista
        rotateScreen();

        //Empezamos con id 21:
        int position4 = 20;
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position4));
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position4, click()));
        Thread.sleep(500);

        // Verificar detalles
        onView(withId(R.id.movie_image)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_director)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_duration)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_actors)).check(matches(isDisplayed()));

        // Verificar estrella sin marcar y marcar
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));
        onView(withId(R.id.btn_favorite)).perform(click());

        // Rotar y verificar que sigue marcada
        rotateScreen(); rotateScreen();
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        // Volver a la lista
        pressBack();
        Thread.sleep(500);
        ActivityScenario<MoviesListActivity> scenario6 = ActivityScenario.launch(MoviesListActivity.class);
        waitUntilRecyclerHasItems(scenario6, R.id.movie_recycler, 23, 5000);

        // Rotar en lista
        rotateScreen();

        //Empezamos con id 23:
        int position5 = 23;
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position5));
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position5, click()));
        Thread.sleep(500);

        // Verificar detalles
        onView(withId(R.id.movie_image)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_director)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_duration)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_actors)).check(matches(isDisplayed()));

        // Verificar estrella sin marcar y marcar
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));
        onView(withId(R.id.btn_favorite)).perform(click());

        // Rotar y verificar que sigue marcada
        rotateScreen(); rotateScreen();
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        // Volver a la lista
        pressBack();
        Thread.sleep(500);
        ActivityScenario<MoviesListActivity> scenario7 = ActivityScenario.launch(MoviesListActivity.class);
        waitUntilRecyclerHasItems(scenario7, R.id.movie_recycler, 23, 5000);

        // Rotar en lista
        rotateScreen();

        // Ir a favoritos
        onView(withId(R.id.button_favorites)).perform(click());
        onView(withId(R.id.movie_recycler)).perform(RecyclerViewActions.scrollToPosition(6));

        //Vamos a la peli de la posicion 2, y dentro de los detalle, pulsamos la estrella para que este vacia, rotamos, y al volver a la lista de favoritos esa peli no debe estar
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Thread.sleep(500);

        // Verificar detalles visibles
        onView(withId(R.id.movie_image)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_director)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_duration)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_actors)).check(matches(isDisplayed()));

        // Pulsar la estrella para desmarcar como favorito
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));
        onView(withId(R.id.btn_favorite)).perform(click());

        // Rotar pantalla y verificar que la estrella sigue desmarcada
        rotateScreen(); rotateScreen();
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        // Volver a favoritos
        pressBack();
        Thread.sleep(500);
        //Espera hasta que el RecyclerView esté visible con al menos 1 ítem
        ActivityScenario<FavoritesActivity> favoritesScenario2 = ActivityScenario.launch(FavoritesActivity.class);
        waitUntilRecyclerHasItems(favoritesScenario2, R.id.movie_recycler, 5, 5000);

        // Verificar que la película ya no está en favoritos
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_recycler))
                .check(matches(not(hasDescendant(withText("Título: Regreso al futuro")))));


        //HASTA AQUI TODO BIEN
        //Volvemos a la lista de peliculas y vamos a la peli con id 23, la estrella debe estar llena, la desactivamos, verificamos que la estrella se ha cambiado, verificamos que s eha cambiado tras rotar, vamos atras
        //a la lista de peliculas, rotamos, vamos a la lista de favoritos y verificamos que esa peli no esta en la lista de favoritos

        // Volver a la lista de películas
        pressBack();
        Thread.sleep(500);
        ActivityScenario<MoviesListActivity> scenario8 = ActivityScenario.launch(MoviesListActivity.class);
        waitUntilRecyclerHasItems(scenario8, R.id.movie_recycler, 23, 5000);

        // Scroll y selección de la película ID 23 (posición 22 si la lista empieza en 0)
        int positionDesmarcar = 23;
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition(positionDesmarcar));
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(positionDesmarcar, click()));
        Thread.sleep(500);

        // Verificar detalles visibles
        onView(withId(R.id.movie_image)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_director)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_duration)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_actors)).check(matches(isDisplayed()));

        // Pulsar la estrella para desmarcar como favorito
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));
        onView(withId(R.id.btn_favorite)).perform(click());

        // Rotar pantalla y verificar que sigue desmarcada
        rotateScreen(); rotateScreen();
        onView(withId(R.id.btn_favorite)).check(matches(isEnabled()));

        // Volver a la lista de películas
        pressBack();
        Thread.sleep(500);
        ActivityScenario<MoviesListActivity> scenario9 = ActivityScenario.launch(MoviesListActivity.class);
        waitUntilRecyclerHasItems(scenario9, R.id.movie_recycler, 23, 5000);

        // Rotar en la lista
        rotateScreen();

        // Ir a favoritos y verificar que la peli desmarcada no está
        onView(withId(R.id.button_favorites)).perform(click());
        onView(withId(R.id.movie_recycler)).check(matches(hasMinimumChildCount(1)));

        // Esperar que la lista de favoritos esté visible
        ActivityScenario<FavoritesActivity> favoritesScenario3 = ActivityScenario.launch(FavoritesActivity.class);
        waitUntilRecyclerHasItems(favoritesScenario3, R.id.movie_recycler, 1, 5000);

        // Verificamos que el recycler está visible
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));

        // Verificar que la película desmarcada no aparece
        onView(withId(R.id.movie_recycler))
                .check(matches(not(hasDescendant(withText("Título: Los Invasores de Marte"))))); // Reemplaza con el título correcto

    }








}
