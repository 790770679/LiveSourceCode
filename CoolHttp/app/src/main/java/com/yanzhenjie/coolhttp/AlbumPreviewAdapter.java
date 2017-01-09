/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.coolhttp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yanzhenjie.album.task.ImageLocalLoader;

import java.util.List;

/**
 * Created by Yan Zhenjie on 2017/1/8.
 */
public class AlbumPreviewAdapter extends RecyclerView.Adapter<AlbumPreviewAdapter.PreviewViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<String> mImagePathList;
    private int size;

    public AlbumPreviewAdapter(Context context, int size) {
        mLayoutInflater = LayoutInflater.from(context);
        this.size = size;
    }

    public void notifyDataSetChanged(List<String> imagePathList) {
        this.mImagePathList = imagePathList;
        super.notifyDataSetChanged();
    }

    @Override
    public PreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PreviewViewHolder(mLayoutInflater.inflate(R.layout.item_preview, parent, false), size);
    }

    @Override
    public void onBindViewHolder(PreviewViewHolder holder, int position) {
        holder.setData(mImagePathList.get(position));
    }

    @Override
    public int getItemCount() {
        return mImagePathList == null ? 0 : mImagePathList.size();
    }

    static class PreviewViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public PreviewViewHolder(View itemView, int size) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            imageView.getLayoutParams().width = size;
            imageView.getLayoutParams().height = size;
            imageView.requestLayout();
        }

        public void setData(String path) {
            ImageLocalLoader.getInstance().loadImage(imageView, path);
        }
    }

}
