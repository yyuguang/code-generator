package com.lnzz.codegen.platform.domain.validator;

import com.lnzz.codegen.platform.domain.spec.FieldSpec;
import com.lnzz.codegen.platform.domain.spec.ModuleSpec;
import com.lnzz.codegen.platform.domain.spec.ProjectSpec;
import com.lnzz.codegen.platform.domain.spec.enums.ConfidenceLevelEnum;
import com.lnzz.codegen.platform.domain.spec.enums.FieldSourceTypeEnum;
import com.lnzz.codegen.platform.domain.spec.enums.OperationTypeEnum;
import com.lnzz.codegen.platform.domain.spec.enums.SpecStatusEnum;
import com.lnzz.codegen.platform.domain.spec.enums.ValidationStatusEnum;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @classname: SpecSchemaValidatorTest
 * @author: Fantasy
 * @date: 2026/4/8 21:10
 * @description: ???? Schema ????
 */
class SpecSchemaValidatorTest {

    private final SpecSchemaValidator specSchemaValidator = new SpecSchemaValidator();

    @Test
    void shouldPassWhenProjectSpecIsValid() {
        ProjectSpec projectSpec = buildValidProjectSpec();

        SpecValidationResult validationResult = specSchemaValidator.validateProjectSpec(projectSpec);

        assertTrue(validationResult.getIssueList().isEmpty());
        assertTrue(validationResult.getPassed());
    }

    @Test
    void shouldFailWhenModuleHasNoPrimaryKeyField() {
        ProjectSpec projectSpec = buildValidProjectSpec();
        projectSpec.getModuleSpecList().get(0).getFieldSpecList().forEach(fieldSpec -> fieldSpec.setPrimaryKey(false));

        SpecValidationResult validationResult = specSchemaValidator.validateProjectSpec(projectSpec);

        assertFalse(validationResult.getPassed());
        assertEquals(1, validationResult.getIssueList().size());
        assertEquals("MODULE_PRIMARY_KEY_MISSING", validationResult.getIssueList().get(0).getRuleCode());
    }

    @Test
    void shouldFailWhenFieldNameIsDuplicated() {
        ProjectSpec projectSpec = buildValidProjectSpec();
        FieldSpec duplicateFieldSpec = FieldSpec.builder()
                .fieldName("productName")
                .displayName("??????")
                .columnName("product_name_copy")
                .dataType("String")
                .required(true)
                .primaryKey(false)
                .unique(false)
                .fieldSourceType(FieldSourceTypeEnum.AI_INFERRED)
                .confidenceLevel(ConfidenceLevelEnum.MEDIUM)
                .validationStatus(ValidationStatusEnum.PENDING)
                .sortOrder(3)
                .build();
        projectSpec.getModuleSpecList().get(0).getFieldSpecList().add(duplicateFieldSpec);

        SpecValidationResult validationResult = specSchemaValidator.validateProjectSpec(projectSpec);

        assertFalse(validationResult.getPassed());
        assertEquals("FIELD_NAME_DUPLICATED", validationResult.getIssueList().get(0).getRuleCode());
    }

    /**
     * ????????????
     *
     * @param ?
     * @return ProjectSpec
     * @author Fantasy
     * @date 2026/4/8 21:10
     */
    private ProjectSpec buildValidProjectSpec() {
        FieldSpec idFieldSpec = FieldSpec.builder()
                .fieldName("id")
                .displayName("??")
                .columnName("id")
                .dataType("Long")
                .required(true)
                .primaryKey(true)
                .unique(true)
                .fieldSourceType(FieldSourceTypeEnum.SYSTEM_COMPLETED)
                .confidenceLevel(ConfidenceLevelEnum.HIGH)
                .validationStatus(ValidationStatusEnum.PASSED)
                .sortOrder(1)
                .build();

        FieldSpec productNameFieldSpec = FieldSpec.builder()
                .fieldName("productName")
                .displayName("????")
                .columnName("product_name")
                .dataType("String")
                .length(128)
                .required(true)
                .primaryKey(false)
                .unique(false)
                .fieldSourceType(FieldSourceTypeEnum.AI_INFERRED)
                .confidenceLevel(ConfidenceLevelEnum.MEDIUM)
                .validationStatus(ValidationStatusEnum.PENDING)
                .sortOrder(2)
                .build();

        ModuleSpec moduleSpec = ModuleSpec.builder()
                .projectSpecId("project-001")
                .moduleName("product")
                .displayName("????")
                .tableName("product_info")
                .description("??????")
                .generateCrud(true)
                .enablePageQuery(true)
                .operationTypeList(List.of(OperationTypeEnum.CREATE, OperationTypeEnum.UPDATE, OperationTypeEnum.DELETE, OperationTypeEnum.DETAIL, OperationTypeEnum.PAGE))
                .fieldSpecList(new java.util.ArrayList<>(List.of(idFieldSpec, productNameFieldSpec)))
                .sortOrder(1)
                .build();

        return ProjectSpec.builder()
                .id("project-001")
                .projectName("AI???????")
                .groupId("com.lnzz")
                .artifactId("codegen-platform-backend")
                .packageName("com.lnzz.codegen.platform")
                .author("Fantasy")
                .specStatus(SpecStatusEnum.DRAFT)
                .sourceRequirementText("????????")
                .moduleSpecList(List.of(moduleSpec))
                .build();
    }
}
