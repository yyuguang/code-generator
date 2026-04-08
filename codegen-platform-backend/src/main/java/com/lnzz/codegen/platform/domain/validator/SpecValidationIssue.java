package com.lnzz.codegen.platform.domain.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @classname: SpecValidationIssue
 * @author: Fantasy
 * @date: 2026/4/8 21:14
 * @description: Validation issue produced by schema checks.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecValidationIssue {

    private String ruleCode;

    private String message;
}
