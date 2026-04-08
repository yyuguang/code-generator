package com.lnzz.codegen.platform.domain.spec;

import com.lnzz.codegen.platform.domain.spec.enums.ConfidenceLevelEnum;
import com.lnzz.codegen.platform.domain.spec.enums.FieldSourceTypeEnum;
import com.lnzz.codegen.platform.domain.spec.enums.ValidationStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @classname: FieldSpec
 * @author: Fantasy
 * @date: 2026/4/8 21:14
 * @description: Field level specification object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldSpec {

    private String id;

    private String moduleSpecId;

    @NotBlank(message = "fieldName must not be blank")
    private String fieldName;

    @NotBlank(message = "displayName must not be blank")
    private String displayName;

    @NotBlank(message = "dataType must not be blank")
    private String dataType;

    @NotBlank(message = "columnName must not be blank")
    private String columnName;

    private Integer length;

    private Integer precision;

    private Integer scale;

    @NotNull(message = "primaryKey must not be null")
    private Boolean primaryKey;

    @NotNull(message = "required must not be null")
    private Boolean required;

    @NotNull(message = "unique must not be null")
    private Boolean unique;

    private String defaultValue;

    private String description;

    @NotNull(message = "fieldSourceType must not be null")
    private FieldSourceTypeEnum fieldSourceType;

    @NotNull(message = "confidenceLevel must not be null")
    private ConfidenceLevelEnum confidenceLevel;

    @NotNull(message = "validationStatus must not be null")
    private ValidationStatusEnum validationStatus;

    private Integer sortOrder;
}
