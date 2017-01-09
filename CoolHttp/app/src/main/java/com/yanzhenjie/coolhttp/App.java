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

import android.app.Application;

import com.yanzhenjie.coolhttp.http.CoolHttp;
import com.yanzhenjie.coolhttp.http.execute.OkHttpExecutor;

/**
 * Created by Yan Zhenjie on 2017/1/8.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 第一种初始化方法。
        // CoolHttp.initialize(this);

        // 第二种初始化方法。
        CoolHttp.initialize(
                this,
                new CoolHttp.HttpConfig()
                        .setConnectionTimeout(10 * 1000)
                        .setReadTimeout(20 * 1000)
                        .setHttpExecutor(OkHttpExecutor.getInstance())
        );
    }
}
