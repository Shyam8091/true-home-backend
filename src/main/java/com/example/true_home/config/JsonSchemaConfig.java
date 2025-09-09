package com.example.true_home.config;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonSchemaConfig {
    private static final String SCHEMA_VALIDATION_FILE = "{ \"$schema\":\"https://json-schema.org/draft/2020-12/schema\", \"type\":\"object\", \"required\":[ \"userId\", \"otp\" ], \"properties\":{ \"userId\":{ \"uniqueItems\":true, \"type\":\"string\" }, \"otp\":{ \"type\":\"string\" } } }";

    @Bean
    public JsonSchema jsonSchema() {
        return JsonSchemaFactory
                .getInstance(SpecVersion.VersionFlag.V7)
                .getSchema(SCHEMA_VALIDATION_FILE);
    }
}
