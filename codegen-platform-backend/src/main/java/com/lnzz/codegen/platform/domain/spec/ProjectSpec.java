package com.lnzz.codegen.platform.domain.spec;

import com.lnzz.codegen.platform.domain.spec.enums.SpecStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @classname: ProjectSpec
 * @author: Fantasy
 * @date: 2026/4/8 21:14
 * @description: Project level specification aggregate.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSpec {

    private String id;

    @NotBlank(message = "projectName must not be blank")
    private String projectName;

    private String groupId;

    @NotBlank(message = "artifactId must not be blank")
    private String artifactId;

    @NotBlank(message = "packageName must not be blank")
    private String packageName;

    private String author;

    private SpecStatusEnum specStatus;

    private String sourceRequirementText;

    private String markdownSnapshot;

    @Valid
    @NotEmpty(message = "moduleSpecList must not be empty")
    private List<ModuleSpec> moduleSpecList;
}
