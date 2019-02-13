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

import de.bund.bva.pliscommon.aufrufkontext.AufrufKontext;
import de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextImpl;
import de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextVerwalterImpl;
import de.bund.bva.pliscommon.sicherheit.Berechtigungsmanager;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungFehlgeschlagenException;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungTechnicalException;
import de.bund.bva.pliscommon.sicherheit.common.exception.InitialisierungsException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test der Klasse {@link SicherheitImpl}.
 *
 */
public class SicherheitImplTest extends AbstractSicherheitTest {

    @Test
    public void testeGetBerechtigungsManagerRollenErmittelt() throws Exception {
        AufrufKontext aufrufKontext = this.aufrufKontextFactory.erzeugeAufrufKontext();

        aufrufKontext.setDurchfuehrenderBenutzerKennung("nutzer");
        aufrufKontext.setDurchfuehrenderSachbearbeiterName("name");
        aufrufKontext.setDurchfuehrendeBehoerde("behoerde");
        aufrufKontext.setRolle(new String[] { "Rolle_A", "Rolle_B" });
        aufrufKontext.setRollenErmittelt(true);
        this.aufrufKontextVerwalter.setAufrufKontext(aufrufKontext);
        Berechtigungsmanager berechtigungsManager = this.sicherheit.getBerechtigungsManager();
        assertEquals(0, this.testAccessManager.getCountAuthentifziere());
        assertEquals("Nicht alle Rechte wurden zugewiesen", 2, berechtigungsManager.getRechte().size());
    }

    @Test
    public void testeAuthentifziereMitKontext() throws Exception {
        AufrufKontext aufrufKontext = this.aufrufKontextFactory.erzeugeAufrufKontext();
        aufrufKontext.setDurchfuehrenderBenutzerKennung("nutzer");
        aufrufKontext.setDurchfuehrenderSachbearbeiterName("name");
        aufrufKontext.setDurchfuehrendeBehoerde("behoerde");
        aufrufKontext.setDurchfuehrenderBenutzerPasswort("passwort");
        aufrufKontext.setRollenErmittelt(false);
        aufrufKontext.setRolle(new String[] { "Rolle_XYZ" });
        this.sicherheit.getBerechtigungsManagerUndAuthentifiziere(aufrufKontext);

        assertEquals(1, this.testAccessManager.getCountAuthentifziere());
        assertEquals("nutzer", this.testAccessManager.getParamAuthentifiziereAufrufKontext()
            .getDurchfuehrenderBenutzerKennung());
        assertEquals("passwort", this.testAccessManager.getParamAuthentifiziereAufrufKontext()
            .getDurchfuehrenderBenutzerPasswort());
        assertTrue(this.sicherheit.getBerechtigungsManager().hatRecht("Recht_A"));
        assertTrue(this.sicherheit.getBerechtigungsManager().hatRecht("Recht_B"));
        assertEquals(2, this.sicherheit.getBerechtigungsManager().getRollen().size());
    }

    @Test(expected = AuthentifizierungFehlgeschlagenException.class)
    public void testeAuthentifiziereMitKontextException() {
        this.testAccessManager.setAuthentifiziereException(new AuthentifizierungFehlgeschlagenException(
            "Testfehler"));
        this.sicherheit.getBerechtigungsManagerUndAuthentifiziere(this.aufrufKontextFactory
            .erzeugeAufrufKontext());
    }

    @Test(expected = AuthentifizierungTechnicalException.class)
    public void testeAuthentifiziereMitKontextException2() {
        this.testAccessManager.setAuthentifiziereException(new AuthentifizierungTechnicalException(
            "Testfehler"));
        this.sicherheit.getBerechtigungsManagerUndAuthentifiziere(this.aufrufKontextFactory
            .erzeugeAufrufKontext());
    }

    @Test
    public void testeGetBerechtigungsManagerRollenNichtErmittelt() throws Exception {
        AufrufKontext aufrufKontext = this.aufrufKontextFactory.erzeugeAufrufKontext();
        aufrufKontext.setDurchfuehrenderBenutzerKennung("nutzer");
        aufrufKontext.setDurchfuehrenderSachbearbeiterName("name");
        aufrufKontext.setDurchfuehrendeBehoerde("behoerde");
        aufrufKontext.setDurchfuehrenderBenutzerPasswort("passwort");
        aufrufKontext.setRollenErmittelt(false);
        aufrufKontext.setRolle(new String[] { "Rolle_XYZ" });
        this.aufrufKontextVerwalter.setAufrufKontext(aufrufKontext);
        this.sicherheit.getBerechtigungsManager();

        assertEquals(1, this.testAccessManager.getCountAuthentifziere());
        assertEquals("nutzer", this.testAccessManager.getParamAuthentifiziereAufrufKontext()
            .getDurchfuehrenderBenutzerKennung());
        assertEquals("passwort", this.testAccessManager.getParamAuthentifiziereAufrufKontext()
            .getDurchfuehrenderBenutzerPasswort());
        assertTrue(this.sicherheit.getBerechtigungsManager().hatRecht("Recht_A"));
        assertTrue(this.sicherheit.getBerechtigungsManager().hatRecht("Recht_B"));
        assertEquals(2, this.sicherheit.getBerechtigungsManager().getRollen().size());
    }

    @Test(expected = InitialisierungsException.class)
    public void testeRollenrechteNichtGesetzt() throws Exception {
        AufrufKontextVerwalterImpl aufrufKontextVerwalterImpl = new AufrufKontextVerwalterImpl();
        AufrufKontextImpl aufrufKontext = new AufrufKontextImpl();
        aufrufKontext.setDurchfuehrenderBenutzerKennung("nutzer");
        aufrufKontext.setDurchfuehrenderSachbearbeiterName("name");
        aufrufKontext.setDurchfuehrendeBehoerde("behoerde");
        aufrufKontext.setRolle(new String[] { "Rolle_A", "Rolle_B" });
        aufrufKontext.setRollenErmittelt(true);
        aufrufKontextVerwalterImpl.setAufrufKontext(aufrufKontext);
        SicherheitImpl sicherheit = new SicherheitImpl(null, aufrufKontextVerwalterImpl, null, null, null);
        sicherheit.afterPropertiesSet();
    }

    @Test(expected = InitialisierungsException.class)
    public void testeFehlerAufrufKontextVerwalterNichtGesetzt() throws Exception {
        SicherheitImpl sicherheit = new SicherheitImpl("/resources/sicherheit/rollenrechte.xml", null, aufrufKontextFactory, testAccessManager, isySicherheitConfigurationProperties);
        sicherheit.afterPropertiesSet();
    }

    @Test(expected = AuthentifizierungTechnicalException.class)
    public void testeFehlerAufrufKontextNichtGesetzt() throws Exception {
        this.sicherheit.getBerechtigungsManager();
    }

    @Test(expected = AuthentifizierungTechnicalException.class)
    public void testGetBerechtigungsmanagerBeiAufrufKontextVerwalterGleichNull(){
        SicherheitImpl sich = new SicherheitImpl("/resources/sicherheit/rollenrechte.xml", null, aufrufKontextFactory, testAccessManager, isySicherheitConfigurationProperties);
        sich.getBerechtigungsManager();
    }

    @Test(expected = AuthentifizierungTechnicalException.class)
    public void testGetBerechtigungsmanagerUndAuthentifiziereBeiAufrufKontextGleichNull(){
        SicherheitImpl sicherheit = new SicherheitImpl("/resources/sicherheit/rollenrechte.xml", aufrufKontextVerwalter, aufrufKontextFactory, testAccessManager, isySicherheitConfigurationProperties);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(null);
    }


    @Test(expected = InitialisierungsException.class)
    public void testAfterPropertiesSetKeinAufrufKontextVerwalter() throws Exception{
        SicherheitImpl sicherheit = new SicherheitImpl("/resources/sicherheit/rollenrechte.xml", null, null, null, null);
        sicherheit.afterPropertiesSet();
    }

    @Test
    public void testAfterPropertiesSetCacheTTLNull() throws Exception{
        isySicherheitConfigurationProperties.setTtl(0);
        ((SicherheitImpl)sicherheit).afterPropertiesSet();

        AufrufKontext aufrufKontext = this.aufrufKontextFactory.erzeugeAufrufKontext();
        aufrufKontext.setDurchfuehrenderBenutzerKennung("nutzer");
        aufrufKontext.setDurchfuehrenderSachbearbeiterName("name");
        aufrufKontext.setDurchfuehrendeBehoerde("behoerde");
        aufrufKontext.setDurchfuehrenderBenutzerPasswort("passwort");
        aufrufKontext.setRollenErmittelt(false);
        aufrufKontext.setRolle(new String[] { "Rolle_XYZ" });

        sicherheit.getBerechtigungsManagerUndAuthentifiziere(aufrufKontext);
        sicherheit.getBerechtigungsManagerUndAuthentifiziere(aufrufKontext);
        assertEquals(2, testAccessManager.getCountAuthentifziere());
    }
}
