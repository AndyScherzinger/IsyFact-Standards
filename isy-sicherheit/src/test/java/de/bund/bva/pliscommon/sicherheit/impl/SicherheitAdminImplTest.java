/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * The Federal Office of Administration (Bundesverwaltungsamt, BVA)
 * licenses this file to you under the Apache License, Version 2.0 (the
 * License). You may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package de.bund.bva.pliscommon.sicherheit.impl;

import de.bund.bva.pliscommon.sicherheit.SicherheitAdmin;
import de.bund.bva.pliscommon.sicherheit.config.SicherheitTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * Testfälle für das pingen des Policy Servers.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SicherheitTestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = {
    "isy.logging.anwendung.name=test", "isy.logging.anwendung.typ=test",
    "isy.logging.anwendung.version=test" })
public class SicherheitAdminImplTest {

    @Autowired
    private SicherheitAdmin sicherheitAdmin;

    @Test
    public void testePing() {
        assertTrue(this.sicherheitAdmin.pingAccessManager());
    }

}
