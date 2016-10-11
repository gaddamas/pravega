/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.emc.pravega.service.server.host.selftest;

import com.emc.pravega.common.util.PropertyBag;
import lombok.Cleanup;

import java.util.concurrent.TimeUnit;

/**
 * Main entry point for Self Tester.
 */
public class TestRunner {
    public static void main(String[] args) throws Exception{
        TestConfig config = new TestConfig(PropertyBag
                .create()
                .with(TestConfig.PROPERTY_SEGMENT_COUNT, 1)
                .with(TestConfig.PROPERTY_PRODUCER_COUNT, 1)
                .with(TestConfig.PROPERTY_OPERATION_COUNT, 1000)
                .with(TestConfig.PROPERTY_MAX_TRANSACTION_SIZE, 1024)
                .with(TestConfig.PROPERTY_TRANSACTION_FREQUENCY, 100)
                .with(TestConfig.PROPERTY_THREAD_POOL_SIZE, 100));

        @Cleanup
        SelfTest test = new SelfTest(config);
        test.startAsync().awaitRunning(config.getTimeout().toMillis(), TimeUnit.MILLISECONDS);
        Thread.sleep(1000);
        test.stopAsync().awaitTerminated();
    }
}
