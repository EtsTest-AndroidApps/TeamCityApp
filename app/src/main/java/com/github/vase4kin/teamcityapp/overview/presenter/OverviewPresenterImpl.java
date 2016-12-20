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

package com.github.vase4kin.teamcityapp.overview.presenter;

import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener;
import com.github.vase4kin.teamcityapp.base.list.extractor.BaseValueExtractor;
import com.github.vase4kin.teamcityapp.base.list.presenter.BaseListPresenterImpl;
import com.github.vase4kin.teamcityapp.base.list.view.BaseDataModel;
import com.github.vase4kin.teamcityapp.navigation.api.BuildElement;
import com.github.vase4kin.teamcityapp.navigation.tracker.ViewTracker;
import com.github.vase4kin.teamcityapp.overview.data.OverViewInteractor;
import com.github.vase4kin.teamcityapp.overview.data.OverviewDataModelImpl;
import com.github.vase4kin.teamcityapp.overview.view.OverviewFragment;
import com.github.vase4kin.teamcityapp.overview.view.OverviewView;

import java.util.List;

import javax.inject.Inject;

/**
 * Presenter to handle logic of {@link OverviewFragment}
 */
public class OverviewPresenterImpl extends BaseListPresenterImpl<
        BaseDataModel,
        BuildElement,
        OverviewView,
        OverViewInteractor,
        ViewTracker,
        BaseValueExtractor> implements OverviewPresenter, OverviewView.OverviewViewListener {

    @Inject
    OverviewPresenterImpl(@NonNull OverviewView view,
                          @NonNull OverViewInteractor dataManager,
                          @NonNull ViewTracker tracker,
                          @NonNull BaseValueExtractor valueExtractor) {
        super(view, dataManager, tracker, valueExtractor);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mView.setOverViewListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadData(@NonNull OnLoadingListener<List<BuildElement>> loadingListener) {
        mDataManager.load(mValueExtractor.getBuild().getHref(), loadingListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BaseDataModel createModel(List<BuildElement> data) {
        return new OverviewDataModelImpl(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mValueExtractor.getBuild().isRunning()) {
            mView.createStopBuildOptionsMenu(menu, inflater);
        } else if (mValueExtractor.getBuild().isQueued()) {
            mView.createRemoveBuildFromQueueOptionsMenu(menu, inflater);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mView.onOptionsItemSelected(item);
    }

    @Override
    public void onCancelBuildContextMenuClick() {
        mDataManager.postStopBuildEvent();
    }
}
