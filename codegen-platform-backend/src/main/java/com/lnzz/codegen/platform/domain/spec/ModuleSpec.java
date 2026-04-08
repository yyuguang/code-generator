package com.lnzz.codegen.platform.domain.spec;

import com.lnzz.codegen.platform.domain.spec.enums.OperationTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @classname: ModuleSpec
 * @author: Fantasy
 * @date: 2026/4/8 21:14
 * @description: Module level specification object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleSpec {

    private String id;

    private String projectSpecId;

    @NotBlank(message = "moduleName must not be blank")
    private String moduleName;

    @NotBlank(message = "displayName must not be blank")
    private String displayName;

    @NotBlank(message = "tableName must not be blank")
    private String tableName;

    private String description;

    private Boolean generateCrud;

    private Boolean enablePageQuery;

    @NotEmpty(message = "operationTypeList must not be empty")
    private List<OperationTypeEnum> operationTypeList;

    @Valid
    @NotEmpty(message = "fieldSpecList must not be empty")
    private List<FieldSpec> fieldSpecList;

    private Integer sortOrder;
}
