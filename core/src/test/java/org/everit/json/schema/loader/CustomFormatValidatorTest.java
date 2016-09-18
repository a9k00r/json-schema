/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.json.schema.loader;

import org.everit.json.schema.FormatValidator;
import org.everit.json.schema.ResourceLoader;
import org.everit.json.schema.ValidationException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class CustomFormatValidatorTest {

    private final ResourceLoader loader = ResourceLoader.DEFAULT;

    static class EvenCharNumValidator implements FormatValidator {

        @Override
        public Optional<String> validate(final String subject) {
            if (subject.length() % 2 == 0) {
                return Optional.empty();
            } else {
                return Optional.of(String.format("the length of srtring [%s] is odd", subject));
            }
        }
    }

    @Test
    public void test() {
        SchemaLoader schemaLoader = SchemaLoader.builder()
                .schemaJson(loader.readObj("customformat-schema.json"))
                .addFormatValidator("evenlength", new EvenCharNumValidator())
                .build();
        try {
            schemaLoader.load().build().validate(loader.readObj("customformat-data.json"));
            Assert.fail("did not throw exception");
        } catch (ValidationException ve) {
        }

    }

}
