package com.lnzz.codegen.platform.domain.validator;

import com.lnzz.codegen.platform.domain.spec.FieldSpec;
import com.lnzz.codegen.platform.domain.spec.ModuleSpec;
import com.lnzz.codegen.platform.domain.spec.ProjectSpec;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @classname: SpecSchemaValidator
 * @author: Fantasy
 * @date: 2026/4/8 21:14
 * @description: Schema validator for unified specification objects.
 */
public class SpecSchemaValidator {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Validate a project specification.
     *
     * @param projectSpec project specification object
     * @return SpecValidationResult
     * @author Fantasy
     * @date 2026/4/8 21:14
     */
    public SpecValidationResult validateProjectSpec(ProjectSpec projectSpec) {
        List<SpecValidationIssue> issueList = new ArrayList<>();
        appendBeanValidationIssueList(projectSpec, issueList);
        appendModuleValidationIssueList(projectSpec, issueList);
        return SpecValidationResult.builder()
                .passed(issueList.isEmpty())
                .issueList(issueList)
                .build();
    }

    /**
     * Append bean validation issues.
     *
     * @param projectSpec project specification object
     * @param issueList validation issue list
     * @return void
     * @author Fantasy
     * @date 2026/4/8 21:14
     */
    public void appendBeanValidationIssueList(ProjectSpec projectSpec, List<SpecValidationIssue> issueList) {
        Set<ConstraintViolation<ProjectSpec>> violationSet = validator.validate(projectSpec);
        for (ConstraintViolation<ProjectSpec> violation : violationSet) {
            issueList.add(SpecValidationIssue.builder()
                    .ruleCode("BEAN_VALIDATION_ERROR")
                    .message(violation.getMessage())
                    .build());
        }
    }

    private void appendModuleValidationIssueList(ProjectSpec projectSpec, List<SpecValidationIssue> issueList) {
        if (projectSpec == null || projectSpec.getModuleSpecList() == null) {
            return;
        }
        for (ModuleSpec moduleSpec : projectSpec.getModuleSpecList()) {
            appendPrimaryKeyValidationIssue(moduleSpec, issueList);
            appendDuplicateFieldValidationIssue(moduleSpec, issueList);
        }
    }

    private void appendPrimaryKeyValidationIssue(ModuleSpec moduleSpec, List<SpecValidationIssue> issueList) {
        boolean hasPrimaryKey = moduleSpec.getFieldSpecList().stream().anyMatch(FieldSpec::getPrimaryKey);
        if (!hasPrimaryKey) {
            issueList.add(buildIssue("MODULE_PRIMARY_KEY_MISSING", "module must contain at least one primary key field"));
        }
    }

    private void appendDuplicateFieldValidationIssue(ModuleSpec moduleSpec, List<SpecValidationIssue> issueList) {
        Set<String> fieldNameSet = new HashSet<>();
        for (FieldSpec fieldSpec : moduleSpec.getFieldSpecList()) {
            if (!fieldNameSet.add(fieldSpec.getFieldName())) {
                issueList.add(buildIssue("FIELD_NAME_DUPLICATED", "field names must be unique inside one module"));
                return;
            }
        }
    }

    private SpecValidationIssue buildIssue(String ruleCode, String message) {
        return SpecValidationIssue.builder()
                .ruleCode(ruleCode)
                .message(message)
                .build();
    }
}
