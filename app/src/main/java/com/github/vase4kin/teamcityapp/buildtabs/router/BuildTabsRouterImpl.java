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

package com.github.vase4kin.teamcityapp.buildtabs.router;

import android.app.Activity;
import android.content.Intent;

import com.github.vase4kin.teamcityapp.R;
import com.github.vase4kin.teamcityapp.buildlist.api.Build;
import com.github.vase4kin.teamcityapp.buildtabs.view.BuildTabsActivity;

/**
 * Impl of {@link BuildTabsRouter}
 */
public class BuildTabsRouterImpl implements BuildTabsRouter {

    private Activity mActivity;

    public BuildTabsRouterImpl(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reopenBuildTabsActivity(Build build) {
        BuildTabsActivity.start(mActivity, build);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startShareBuildWebUrlActivity(String webUrl) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, webUrl);
        sendIntent.setType("text/plain");
        mActivity.startActivity(Intent.createChooser(sendIntent, mActivity.getString(R.string.text_share_build)));
    }
}
