/*
 * Copyright 2016 Andrey Tolpeev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.vase4kin.teamcityapp.queue.view;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.github.vase4kin.teamcityapp.R;
import com.github.vase4kin.teamcityapp.TeamCityApplication;
import com.github.vase4kin.teamcityapp.api.TeamCityService;
import com.github.vase4kin.teamcityapp.buildlist.api.Build;
import com.github.vase4kin.teamcityapp.buildlist.api.Builds;
import com.github.vase4kin.teamcityapp.dagger.components.AppComponent;
import com.github.vase4kin.teamcityapp.dagger.components.RestApiComponent;
import com.github.vase4kin.teamcityapp.dagger.modules.AppModule;
import com.github.vase4kin.teamcityapp.dagger.modules.FakeTeamCityServiceImpl;
import com.github.vase4kin.teamcityapp.dagger.modules.Mocks;
import com.github.vase4kin.teamcityapp.dagger.modules.RestApiModule;
import com.github.vase4kin.teamcityapp.helper.CustomActivityTestRule;
import com.github.vase4kin.teamcityapp.home.view.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;

import java.util.Collections;

import io.reactivex.Single;
import it.cosenonjaviste.daggermock.DaggerMockRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.github.vase4kin.teamcityapp.helper.RecyclerViewMatcher.withRecyclerView;
import static com.github.vase4kin.teamcityapp.helper.TestUtils.hasItemsCount;
import static com.github.vase4kin.teamcityapp.helper.TestUtils.matchToolbarTitle;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class BuildQueueActivityTest {

    @Rule
    public DaggerMockRule<RestApiComponent> daggerRule = new DaggerMockRule<>(RestApiComponent.class, new RestApiModule(Mocks.URL))
            .addComponentDependency(AppComponent.class, new AppModule((TeamCityApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext()))
            .set(restApiComponent -> {
                TeamCityApplication app = (TeamCityApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
                app.setRestApiInjector(restApiComponent);
            });

    @Rule
    public CustomActivityTestRule<HomeActivity> activityRule = new CustomActivityTestRule<>(HomeActivity.class);

    @Spy
    private TeamCityService teamCityService = new FakeTeamCityServiceImpl();

    @Test
    public void testUserCanSeeSuccessFullyLoadedQueueBuilds() throws Exception {
        activityRule.launchActivity(null);

        // Click on build queue tab
        clickOnBuildQueueTab();

        checkBuildQueueTabBadgeCount("3");

        // List has item with header
        onView(withId(R.id.build_queue_recycler_view)).check(hasItemsCount(5));
        // Checking header 1
        onView(withRecyclerView(R.id.build_queue_recycler_view).atPositionOnView(0, R.id.section_text))
                .check(matches(withText("project name - build type name")));
        // Checking adapter item 1
        onView(withRecyclerView(R.id.build_queue_recycler_view).atPositionOnView(1, R.id.itemTitle))
                .check(matches(withText("Queued build")));
        onView(withRecyclerView(R.id.build_queue_recycler_view).atPositionOnView(1, R.id.itemSubTitle))
                .check(matches(withText("refs/heads/master")));
        // Checking adapter item 2
        onView(withRecyclerView(R.id.build_queue_recycler_view).atPositionOnView(2, R.id.itemTitle))
                .check(matches(withText("This build will not start because there are no compatible agents which can run it")));
        onView(withRecyclerView(R.id.build_queue_recycler_view).atPositionOnView(2, R.id.itemSubTitle))
                .check(matches(withText("refs/heads/dev")));
        // Checking header 2
        onView(withRecyclerView(R.id.build_queue_recycler_view).atPositionOnView(3, R.id.section_text))
                .check(matches(withText("Project name one two - Another configuration")));
        // Checking adapter item 3
        onView(withRecyclerView(R.id.build_queue_recycler_view).atPositionOnView(4, R.id.itemTitle))
                .check(matches(withText("This build will not start because there are no compatible agents which can run it")));
        onView(withRecyclerView(R.id.build_queue_recycler_view).atPositionOnView(4, R.id.itemSubTitle))
                .check(matches(withText("refs/heads/dev0feature")));
    }

    @Test
    public void testUserCanSeeUpdatedToolbar() throws Exception {
        when(teamCityService.listQueueBuilds(anyString(), anyString())).thenReturn(Single.<Builds>error(new RuntimeException("smth bad happend!")));

        activityRule.launchActivity(null);

        // Click on build queue tab
        clickOnBuildQueueTab();

        matchToolbarTitle("Build queue");
    }

    @Test
    public void testUserCanSeeFailureMessageIfSmthHappensOnQueueBuildsLoading() throws Exception {
        when(teamCityService.listQueueBuilds(anyString(), anyString())).thenReturn(Single.<Builds>error(new RuntimeException("smth bad happend!")));

        activityRule.launchActivity(null);

        // Click on build queue tab
        clickOnBuildQueueTab();

        checkBuildQueueTabBadgeCount("0");

        onView(withText(R.string.error_view_error_text)).check(matches(isDisplayed()));
    }

    @Test
    public void testUserCanSeeEmptyDataMessageIfBuildQueueIsEmpty() throws Exception {
        when(teamCityService.listQueueBuilds(anyString(), anyString())).thenReturn(Single.just(new Builds(0, Collections.<Build>emptyList())));

        activityRule.launchActivity(null);

        // Click on build queue tab
        clickOnBuildQueueTab();

        checkBuildQueueTabBadgeCount("0");
        onView(withId(R.id.queued_empty_title_view)).check(matches(isDisplayed())).check(matches(withText(R.string.empty_list_message_favorite_build_queue)));
    }

    private void clickOnBuildQueueTab() {
        onView(withChild(allOf(withId(R.id.bottom_navigation_small_item_title), withText(R.string.build_queue_drawer_item))))
                .perform(click());
    }

    private void checkBuildQueueTabBadgeCount(String count) {
        onView(allOf(
                withChild(allOf(withId(R.id.bottom_navigation_notification), withText(count))),
                withChild(allOf(withId(R.id.bottom_navigation_small_item_title), withText(R.string.build_queue_drawer_item))))
        )
                .check(matches(isDisplayed()));
    }
}