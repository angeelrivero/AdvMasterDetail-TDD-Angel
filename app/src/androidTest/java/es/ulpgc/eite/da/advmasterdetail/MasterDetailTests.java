package es.ulpgc.eite.da.advmasterdetail;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.ulpgc.eite.da.advmasterdetail.categories.CategoryListActivity;


@SuppressWarnings("ALL")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MasterDetailTests {

  @Rule
  public ActivityTestRule<CategoryListActivity> testRule =
      new ActivityTestRule<>(CategoryListActivity.class);

  @Test
  public void test() {

//    ViewInteraction textView = onView(allOf(
//        withText("Visit Canary"),
//        withParent(allOf(
//            withId(R.id.toolbar),
//            withParent(withId(R.id.app_bar))
//        ))
//    ));
//    textView.check(matches(isDisplayed()));

    onView(new RecyclerViewMatcher(R.id.category_recycler)
        .atPositionOnView(0, R.id.category_name))
        .check(matches(withText("Tenerife")));

    onView(new RecyclerViewMatcher(R.id.category_recycler)
        .atPositionOnView(5, R.id.category_name))
        .check(matches(withText("La Gomera")));

    /*
    onView(new RecyclerViewMatcher(R.id.category_list)
        .atPositionOnView(2, R.id.content))
        .check(matches(withText("Gran Canaria")));

    ViewInteraction recyclerView = onView(allOf(withId(R.id.category_list)));
    recyclerView.perform(actionOnItemAtPosition(2, click()));
    */

    ViewInteraction recyclerView1 =
        onView(new RecyclerViewMatcher(R.id.category_recycler)
            .atPositionOnView(2, R.id.category_name))
            .check(matches(withText("Gran Canaria")));
    recyclerView1.perform(click());

//    ViewInteraction textView6 = onView(allOf(
//        withText("Gran Canaria"),
//        withParent(allOf(
//            withId(R.id.toolbar),
//            withParent(withId(R.id.app_bar))
//        ))
//    ));
//    textView6.check(matches(isDisplayed()));

    onView(new RecyclerViewMatcher(R.id.product_recycler)
        .atPositionOnView(0, R.id.product_name))
        .check(matches(withText("Beaches of Gran Canaria")));

    onView(new RecyclerViewMatcher(R.id.product_recycler)
        .atPositionOnView(1, R.id.product_name))
        .check(matches(withText("Las Palmas de Gran Canaria")));

//    ViewInteraction appCompatImageButton = onView(allOf(
//        withContentDescription("Navigate up"),
//        isDisplayed()
//    ));
//    appCompatImageButton.perform(click());

    pressBack();

//    ViewInteraction textView9 = onView(allOf(
//        withText("Visit Canary"),
//        withParent(allOf(
//            withId(R.id.toolbar),
//            withParent(withId(R.id.app_bar))
//        ))
//    ));
//    textView9.check(matches(isDisplayed()));

    onView(new RecyclerViewMatcher(R.id.category_recycler)
        .atPositionOnView(0, R.id.category_name))
        .check(matches(withText("Tenerife")));

    /*
    ViewInteraction recyclerView2 = onView(allOf(withId(R.id.category_list)));
    recyclerView2.perform(actionOnItemAtPosition(4, click()));
    */

    ViewInteraction recyclerView2 =
        onView(new RecyclerViewMatcher(R.id.category_recycler)
            .atPositionOnView(4, R.id.category_name))
            .check(matches(withText("La Palma")));
    recyclerView2.perform(click());

//    ViewInteraction textView11 = onView(allOf(
//        withText("La Palma"),
//        withParent(allOf(
//            withId(R.id.toolbar),
//            withParent(withId(R.id.app_bar))
//        ))
//    ));
//    textView11.check(matches(isDisplayed()));

    onView(new RecyclerViewMatcher(R.id.product_recycler)
        .atPositionOnView(0, R.id.product_name))
        .check(matches(withText("Santa Cruz de la Palma")));

    /*
    onView(new RecyclerViewMatcher(R.id.product_list)
        .atPositionOnView(1, R.id.content))
        .check(matches(withText("Caldera de Taburiente National Park")));

    ViewInteraction recyclerView3 = onView(allOf(withId(R.id.product_list)));
    recyclerView3.perform(actionOnItemAtPosition(1, click()));
    */

    ViewInteraction recyclerView3 =
        onView(new RecyclerViewMatcher(R.id.product_recycler)
          .atPositionOnView(1, R.id.product_name))
          .check(matches(withText("Caldera de Taburiente National Park")));
    recyclerView3.perform(click());

//    ViewInteraction textView14 = onView(allOf(
//        withText("Caldera de Taburiente National Park"),
//        withParent(allOf(
//            withId(R.id.detail_toolbar),
//            withParent(withId(R.id.app_bar))
//        ))
//    ));
//    textView14.check(matches(isDisplayed()));

    ViewInteraction textView15 = onView(allOf(
        withId(R.id.product_detail),
        isDisplayed()
    ));
    textView15.check(matches(withText(
        "Known as the Isla Bonita (Beautiful Island), " +
            "La Palma is the greenest of the Canary Islands. " +
            "Designated a UNESCO Biosphere Reserve, " +
            "La Palma's landscape varies from pristine forests " +
            "to sheer cliffs and black-sand beaches. " +
            "Among its many protected environments is " +
            "the Caldera de Taburiente National Park, " +
            "where volcanic peaks rise to 2,400 meters, " +
            "and lava flows descend to the sea. " +
            "For those in search of idyllic surroundings, " +
            "the park has wooded areas with streams and waterfalls. " +
            "Along the rocky coastline, picturesque little bays " +
            "are hidden away in between steep hillsides."
    )));

//    ViewInteraction appCompatImageButton2 = onView(allOf(
//        withContentDescription("Navigate up"),
//        isDisplayed()
//    ));
//    appCompatImageButton2.perform(click());

    pressBack();

//    ViewInteraction textView16 = onView(allOf(
//        withText("La Palma"),
//        withParent(allOf(
//            withId(R.id.toolbar),
//            withParent(withId(R.id.app_bar))
//        ))
//    ));
//    textView16.check(matches(isDisplayed()));

    onView(new RecyclerViewMatcher(R.id.product_recycler)
        .atPositionOnView(0, R.id.product_name))
        .check(matches(withText("Santa Cruz de la Palma")));

//    ViewInteraction appCompatImageButton3 = onView(allOf(
//        withContentDescription("Navigate up"),
//        isDisplayed()
//    ));
//    appCompatImageButton3.perform(click());

    pressBack();

//    ViewInteraction textView2 = onView(allOf(
//        withText("Visit Canary"),
//        withParent(allOf(
//            withId(R.id.toolbar),
//            withParent(withId(R.id.app_bar))
//        ))
//    ));
//    textView2.check(matches(isDisplayed()));

    onView(new RecyclerViewMatcher(R.id.category_recycler)
        .atPositionOnView(0, R.id.category_name))
        .check(matches(withText("Tenerife")));

    onView(new RecyclerViewMatcher(R.id.category_recycler)
        .atPositionOnView(5, R.id.category_name))
        .check(matches(withText("La Gomera")));
  }

  public class RecyclerViewMatcher {

    private final int recyclerViewId;

    public RecyclerViewMatcher(int recyclerViewId) {
      this.recyclerViewId = recyclerViewId;
    }

    public Matcher<View> atPosition(final int position) {
      return atPositionOnView(position, -1);
    }

    public Matcher<View> atPositionOnView(
        final int position, final int targetViewId
    ) {
      return new TypeSafeMatcher<View>() {
        Resources resources = null;
        View childView;

        public void describeTo(Description description) {
          String idDescription = Integer.toString(recyclerViewId);
          if (this.resources != null) {
            try {
              idDescription = this.resources.getResourceName(recyclerViewId);
            } catch (Resources.NotFoundException var4) {
              idDescription = String.format(
                  "%s (resource name not found)",
                  new Object[]{Integer.valueOf(recyclerViewId)}
              );
            }
          }

          description.appendText("with id: " + idDescription);
        }

        public boolean matchesSafely(View view) {

          this.resources = view.getResources();

          if (childView == null) {
            RecyclerView recyclerView =
                (RecyclerView) view.getRootView().findViewById(recyclerViewId);
            if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
              childView = recyclerView
                  .findViewHolderForAdapterPosition(position).itemView;
            } else {
              return false;
            }
          }

          if (targetViewId == -1) {
            return view == childView;
          } else {
            View targetView = childView.findViewById(targetViewId);
            return view == targetView;
          }

        }
      };
    }
  }
}
