package com.lnzz.codegen.platform.domain.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @classname: SpecValidationResult
 * @author: Fantasy
 * @date: 2026/4/8 21:14
 * @description: Result object returned by schema validation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecValidationResult {

    private Boolean passed;

    private List<SpecValidationIssue> issueList;
}
