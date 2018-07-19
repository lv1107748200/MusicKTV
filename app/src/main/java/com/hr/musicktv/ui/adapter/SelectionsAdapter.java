/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hr.musicktv.ui.adapter;

import android.content.Context;

import com.hr.musicktv.R;
import com.hr.musicktv.net.entry.ItemBean;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewHolder;


public class SelectionsAdapter extends CommonRecyclerViewAdapter<ItemBean> {
    public SelectionsAdapter(Context context ) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return  R.layout.item_selections;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ItemBean item, int position) {
        helper.getHolder().setText(R.id.title, String.valueOf(position) + "选集");
    }
}
