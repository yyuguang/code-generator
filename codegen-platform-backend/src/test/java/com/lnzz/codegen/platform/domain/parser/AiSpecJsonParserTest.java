package com.lnzz.codegen.platform.domain.parser;

import com.lnzz.codegen.platform.domain.spec.ProjectSpec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @classname: AiSpecJsonParserTest
 * @author: Fantasy
 * @date: 2026/4/8 21:28
 * @description: Tests for parsing AI generated specification JSON.
 */
class AiSpecJsonParserTest {

    private final AiSpecJsonParser aiSpecJsonParser = new AiSpecJsonParser();

    @Test
    void shouldParseProjectSpecFromJson() {
        String specificationJson = """
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

        ProjectSpec projectSpec = aiSpecJsonParser.parseProjectSpec(specificationJson);

        assertEquals("project-001", projectSpec.getId());
        assertEquals("product", projectSpec.getModuleSpecList().get(0).getModuleName());
        assertEquals("id", projectSpec.getModuleSpecList().get(0).getFieldSpecList().get(0).getFieldName());
    }

    @Test
    void shouldThrowExceptionWhenJsonIsInvalid() {
        String invalidJson = "{ invalid json }";

        assertThrows(IllegalArgumentException.class, () -> aiSpecJsonParser.parseProjectSpec(invalidJson));
    }
}
