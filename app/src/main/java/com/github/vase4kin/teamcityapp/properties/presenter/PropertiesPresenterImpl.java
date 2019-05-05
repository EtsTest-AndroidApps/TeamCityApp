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

package com.github.vase4kin.teamcityapp.properties.presenter;

import androidx.annotation.NonNull;

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener;
import com.github.vase4kin.teamcityapp.base.list.presenter.BaseListPresenterImpl;
import com.github.vase4kin.teamcityapp.base.tracker.ViewTracker;
import com.github.vase4kin.teamcityapp.properties.api.Properties;
import com.github.vase4kin.teamcityapp.properties.data.PropertiesDataManager;
import com.github.vase4kin.teamcityapp.properties.data.PropertiesDataModel;
import com.github.vase4kin.teamcityapp.properties.data.PropertiesDataModelImpl;
import com.github.vase4kin.teamcityapp.properties.data.PropertiesValueExtractor;
import com.github.vase4kin.teamcityapp.properties.view.PropertiesView;

import java.util.List;

import javax.inject.Inject;

/**
 * Presenter handles logic of {@link com.github.vase4kin.teamcityapp.properties.view.PropertiesFragment}
 */
public class PropertiesPresenterImpl extends BaseListPresenterImpl<
        PropertiesDataModel,
        Properties.Property,
        PropertiesView,
        PropertiesDataManager,
        ViewTracker,
        PropertiesValueExtractor> implements PropertiesView.Listener {

    @Inject
    PropertiesPresenterImpl(@NonNull PropertiesView view,
                            @NonNull PropertiesDataManager dataManager,
                            @NonNull ViewTracker tracker,
                            @NonNull PropertiesValueExtractor valueExtractor) {
        super(view, dataManager, tracker, valueExtractor);
    }

    @Override
    protected void initViews() {
        mView.setListener(this);
        super.initViews();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadData(@NonNull OnLoadingListener<List<Properties.Property>> loadingListener, boolean update) {
        mDataManager.load(mValueExtractor.getBuildDetails(), loadingListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PropertiesDataModel createModel(List<Properties.Property> data) {
        return new PropertiesDataModelImpl(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCardClick(String header, String value) {
        mView.showCopyValueBottomSheet(header, value);
    }

}
