package com.lnzz.codegen.platform.domain.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @classname: AiPromptTemplateBuilderTest
 * @author: Fantasy
 * @date: 2026/4/8 21:37
 * @description: Tests for AI prompt template builder.
 */
class AiPromptTemplateBuilderTest {

    private final AiPromptTemplateBuilder aiPromptTemplateBuilder = new AiPromptTemplateBuilder();

    @Test
    void shouldBuildPromptWithRequirementAndSchemaConstraints() {
        String prompt = aiPromptTemplateBuilder.buildSpecificationPrompt("Generate product management module with CRUD support");

        assertTrue(prompt.contains("Generate product management module with CRUD support"));
        assertTrue(prompt.contains("JSON"));
        assertTrue(prompt.contains("moduleSpecList"));
        assertTrue(prompt.contains("fieldSpecList"));
        assertTrue(prompt.contains("Do not output markdown"));
    }
}
