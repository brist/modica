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

package org.modica.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.types.EndType;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link EndCodePage}.
 */
public class EndCodePageTestCase extends StructuredFieldTestCase<EndCodePage> {

    private EndCodePage sut;
    private EndCodePage sutMatchesAny;
    private final String cpName = "CodePage";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(EndType.ECP);

        Parameters params = new Parameters(cpName.getBytes("Cp500"));
        sut = new EndCodePage(intro, params, new Context());
        Parameters matchesAnyParams = new Parameters(ByteUtils.createByteArray(0xff, 0xff));
        sutMatchesAny = new EndCodePage(intro, matchesAnyParams, new Context());
        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(cpName, sut.getCodePageName());
        assertEquals(false, sut.nameMatchesAny());

        assertEquals(null, sutMatchesAny.getCodePageName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("CodePageName", cpName));
        testParameters(expectedParams, sut);
    }
}
