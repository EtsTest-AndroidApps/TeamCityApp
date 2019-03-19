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

package com.github.vase4kin.teamcityapp.agents.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.vase4kin.teamcityapp.R;
import com.github.vase4kin.teamcityapp.TeamCityApplication;
import com.github.vase4kin.teamcityapp.agents.dagger.AgentModule;
import com.github.vase4kin.teamcityapp.agents.dagger.DaggerAgentComponent;
import com.github.vase4kin.teamcityapp.agents.presenter.AgentPresenterImpl;
import com.github.vase4kin.teamcityapp.base.extractor.BundleExtractorValues;

import javax.inject.Inject;

/**
 * Manages single agents tab
 */
public class BaseAgentListFragment extends Fragment {

    @Inject
    AgentPresenterImpl mPresenter;

    public static Fragment newInstance(boolean includeDisconnected) {
        BaseAgentListFragment fragment = new BaseAgentListFragment();
        Bundle args = new Bundle();
        args.putBoolean(BundleExtractorValues.AGENT_TYPE, includeDisconnected);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Injecting presenter to fragment
        DaggerAgentComponent.builder()
                .agentModule(new AgentModule(this, view))
                .restApiComponent(((TeamCityApplication) getActivity().getApplication()).getRestApiInjector())
                .build()
                .inject(this);

        mPresenter.onViewsCreated();

        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.onViewsDestroyed();
        super.onDestroyView();
    }
}
