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

package com.github.vase4kin.teamcityapp.base.list.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.vase4kin.teamcityapp.R;

/**
 * Base load more view holder
 *
 * @param <BM> - Base data model
 */
public class LoadMoreViewHolder<BM extends BaseDataModel> extends BaseViewHolder<BM> {

    /**
     * Constructor
     *
     * @param parent group view
     */
    public LoadMoreViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(BaseDataModel dataModel, int position) {
    }
}
