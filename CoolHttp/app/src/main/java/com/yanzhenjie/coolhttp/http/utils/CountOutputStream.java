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
package com.yanzhenjie.coolhttp.http.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Yan Zhenjie on 2017/1/8.
 */
public class CountOutputStream extends OutputStream {

    private AtomicLong atomicLong = new AtomicLong();

    @Override
    public void write(int b) throws IOException {
        atomicLong.addAndGet(1);
    }

    @Override
    public void write(byte[] b) throws IOException {
        atomicLong.addAndGet(b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        atomicLong.addAndGet(len);
    }

    public void write(long length) {
        atomicLong.addAndGet(length);
    }

    public long get() {
        return atomicLong.get();
    }
}
