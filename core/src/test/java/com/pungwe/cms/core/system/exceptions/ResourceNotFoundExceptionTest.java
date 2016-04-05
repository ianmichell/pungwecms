package com.pungwe.cms.core.system.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * )with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * Created by ian on 30/03/2016.
 */
public class ResourceNotFoundExceptionTest {

    @Test(expected = ResourceNotFoundException.class)
    public void testDefaultConstructor() {
        throw new ResourceNotFoundException();
    }

    @Test
    public void testMessageConstructor() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Message");
        assertEquals("Message", ex.getMessage());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        IllegalArgumentException cause = new IllegalArgumentException();
        ResourceNotFoundException ex = new ResourceNotFoundException("Message", cause);
        assertEquals("Message", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testCauseConstructor() {
        IllegalArgumentException cause = new IllegalArgumentException();
        ResourceNotFoundException ex = new ResourceNotFoundException(cause);
        assertEquals(cause, ex.getCause());
    }
}
