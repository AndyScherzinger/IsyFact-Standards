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
package de.bund.bva.isyfact.batchrahmen.core.exception;

import de.bund.bva.isyfact.batchrahmen.batch.rahmen.BatchReturnCode;

/**
 * Diese Exception wird geworfen, wenn der Batchrahmen die Ausführung vorzeitig abbricht.
 * 
 *
 */
public class BatchrahmenAbbruchException extends BatchrahmenException {

    /**
     * Die UID.
     */
    private static final long serialVersionUID = -256100758378906675L;

    /**
     * Ergzeugt einen Fehler.
     * @param ausnahmeId
     *            Die Id der Ausnahme, wird zum laden des Fehlertexts verdendet.
     * @param parameter
     *            Parameter für die Fehlernachricht.
     */
    public BatchrahmenAbbruchException(String ausnahmeId, String... parameter) {
        super(ausnahmeId, parameter);
    }

    /**
     * Ergzeugt einen Fehler.
     * @param ausnahmeId
     *            Die Id der Ausnahme, wird zum laden des Fehlertexts verdendet.
     * @param cause
     *            Die Ursache des Fehlers.
     * @param parameter
     *            Parameter für die Fehlernachricht.
     */
    public BatchrahmenAbbruchException(String ausnahmeId, Throwable cause, String... parameter) {
        super(ausnahmeId, cause, parameter);
    }

    /**
     * Ergzeugt einen Fehler.
     * @param ausnahmeId
     *            Die Id der Ausnahme, wird zum laden des Fehlertexts verdendet.
     */
    public BatchrahmenAbbruchException(String ausnahmeId) {
        super(ausnahmeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BatchReturnCode getReturnCode() {
        return BatchReturnCode.FEHLER_ABBRUCH;
    }

}
