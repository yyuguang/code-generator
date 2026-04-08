package com.lnzz.codegen.platform.domain.parser;

import com.lnzz.codegen.platform.domain.spec.ProjectSpec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @classname: AiSpecParseServiceTest
 * @author: Fantasy
 * @date: 2026/4/8 21:37
 * @description: Tests for AI specification parse service.
 */
class AiSpecParseServiceTest {

    @Test
    void shouldBuildPromptCallClientAndReturnParsedProjectSpec() {
        FakeAiModelGateway fakeAiModelGateway = new FakeAiModelGateway();
        AiPromptTemplateBuilder aiPromptTemplateBuilder = new AiPromptTemplateBuilder();
        AiSpecJsonParser aiSpecJsonParser = new AiSpecJsonParser();
        AiSpecParseService aiSpecParseService = new AiSpecParseService(fakeAiModelGateway, aiPromptTemplateBuilder, aiSpecJsonParser);

        ProjectSpec projectSpec = aiSpecParseService.parseRequirement("Generate product management module");

        assertEquals("Generate product management module", fakeAiModelGateway.getLastRequirementText());
        assertEquals("project-001", projectSpec.getId());
        assertEquals("product", projectSpec.getModuleSpecList().get(0).getModuleName());
    }

    @Test
    void shouldThrowExceptionWhenModelResponseIsBlank() {
        AiModelGateway aiModelGateway = (prompt, requirementText) -> "   ";
        AiSpecParseService aiSpecParseService = new AiSpecParseService(aiModelGateway, new AiPromptTemplateBuilder(), new AiSpecJsonParser());

        assertThrows(IllegalArgumentException.class, () -> aiSpecParseService.parseRequirement("Generate product management module"));
    }

    private static final class FakeAiModelGateway implements AiModelGateway {

        private String lastRequirementText;

        @Override
        public String requestSpecificationJson(String prompt, String requirementText) {
            this.lastRequirementText = requirementText;
            return """
                    {
                      "id": "project-001",
                      "projectName": "AI Business Generator",
                      "groupId": "com.lnzz",
                      "artifactId": "codegen-platform-backend",
                      "packageName": "com.lnzz.codegen.platform",
                      "author": "Fantasy",
                      "specStatus": "DRAFT",
                      "sourceRequirementText": "generate product management module",
                      "moduleSpecList": [
                        {
                          "projectSpecId": "project-001",
                          "moduleName": "product",
                          "displayName": "Product",
                          "tableName": "product_info",
                          "description": "product module",
                          "generateCrud": true,
                          "enablePageQuery": true,
                          "operationTypeList": ["CREATE", "UPDATE", "DELETE", "DETAIL", "PAGE"],
                          "fieldSpecList": [
                            {
                              "fieldName": "id",
                              "displayName": "ID",
                              "dataType": "Long",
                              "columnName": "id",
                              "primaryKey": true,
                              "required": true,
                              "unique": true,
                              "fieldSourceType": "SYSTEM_COMPLETED",
                              "confidenceLevel": "HIGH",
                              "validationStatus": "PASSED",
                              "sortOrder": 1
                            }
                          ],
                          "sortOrder": 1
                        }
                      ]
                    }
                    """;
        }

        public String getLastRequirementText() {
            return lastRequirementText;
        }
    }
}
