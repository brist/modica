/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id$ */

package org.modica.web.model;

import java.io.IOException;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * This is used by to manage the session state of the Afp document.
 *
 */
public class AfpServiceRequestCycleListener extends AbstractRequestCycleListener {

    private AfpService afpService;

    @Override
    public void onBeginRequest(RequestCycle cycle) {
        try {
            afpService.beginSession();
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public void onEndRequest(RequestCycle cycle) {
        try {
            afpService.endSession();
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    public void setAfpService(AfpService afpService) {
        this.afpService = afpService;
    }

}
