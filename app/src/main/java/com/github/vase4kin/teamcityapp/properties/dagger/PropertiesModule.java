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

package com.github.vase4kin.teamcityapp.properties.dagger;

import com.github.vase4kin.teamcityapp.R;
import com.github.vase4kin.teamcityapp.base.list.view.BaseListView;
import com.github.vase4kin.teamcityapp.base.list.view.ViewHolderFactory;
import com.github.vase4kin.teamcityapp.base.tracker.ViewTracker;
import com.github.vase4kin.teamcityapp.properties.data.PropertiesDataManager;
import com.github.vase4kin.teamcityapp.properties.data.PropertiesDataManagerImpl;
import com.github.vase4kin.teamcityapp.properties.data.PropertiesDataModel;
import com.github.vase4kin.teamcityapp.properties.data.PropertiesValueExtractor;
import com.github.vase4kin.teamcityapp.properties.data.PropertiesValueExtractorImpl;
import com.github.vase4kin.teamcityapp.properties.view.PropertiesAdapter;
import com.github.vase4kin.teamcityapp.properties.view.PropertiesFragment;
import com.github.vase4kin.teamcityapp.properties.view.PropertiesView;
import com.github.vase4kin.teamcityapp.properties.view.PropertiesViewHolderFactory;
import com.github.vase4kin.teamcityapp.properties.view.PropertiesViewImpl;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

@Module
public class PropertiesModule {

    @Provides
    PropertiesDataManager providesPropertiesDataManager() {
        return new PropertiesDataManagerImpl();
    }

    @Provides
    PropertiesValueExtractor providesBaseValueExtractor(PropertiesFragment fragment) {
        return new PropertiesValueExtractorImpl(fragment.getArguments());
    }

    @Provides
    PropertiesView providesPropertiesView(PropertiesFragment fragment, PropertiesAdapter adapter) {
        return new PropertiesViewImpl(fragment.getView(), fragment.getActivity(), R.string.empty_list_message_parameters, adapter);
    }

    @Provides
    ViewTracker providesViewTracker() {
        return ViewTracker.Companion.getSTUB();
    }

    @Provides
    PropertiesAdapter providesPropertiesAdapter(Map<Integer, ViewHolderFactory<PropertiesDataModel>> viewHolderFactories) {
        return new PropertiesAdapter(viewHolderFactories);
    }

    @IntoMap
    @IntKey(BaseListView.TYPE_DEFAULT)
    @Provides
    ViewHolderFactory<PropertiesDataModel> providesPropertiesViewHolderFactory() {
        return new PropertiesViewHolderFactory();
    }
}
