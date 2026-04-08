package com.lnzz.codegen.platform.domain.parser;

import com.lnzz.codegen.platform.domain.spec.ProjectSpec;

/**
 * @classname: AiSpecParseService
 * @author: Fantasy
 * @date: 2026/4/8 21:41
 * @description: Coordinate prompt building, model invocation and JSON parsing.
 */
public class AiSpecParseService {

    private final AiModelGateway aiModelGateway;
    private final AiPromptTemplateBuilder aiPromptTemplateBuilder;
    private final AiSpecJsonParser aiSpecJsonParser;

    public AiSpecParseService(
            AiModelGateway aiModelGateway,
            AiPromptTemplateBuilder aiPromptTemplateBuilder,
            AiSpecJsonParser aiSpecJsonParser
    ) {
        this.aiModelGateway = aiModelGateway;
        this.aiPromptTemplateBuilder = aiPromptTemplateBuilder;
        this.aiSpecJsonParser = aiSpecJsonParser;
    }

    /**
     * Parse natural language requirement into unified specification object.
     *
     * @param requirementText natural language requirement
     * @return ProjectSpec
     * @author Fantasy
     * @date 2026/4/8 21:41
     */
    public ProjectSpec parseRequirement(String requirementText) {
        String prompt = aiPromptTemplateBuilder.buildSpecificationPrompt(requirementText);
        String specificationJson = aiModelGateway.requestSpecificationJson(prompt, requirementText);
        if (specificationJson == null || specificationJson.isBlank()) {
            throw new IllegalArgumentException("AI specification response must not be blank");
        }
        return aiSpecJsonParser.parseProjectSpec(specificationJson);
    }
}
