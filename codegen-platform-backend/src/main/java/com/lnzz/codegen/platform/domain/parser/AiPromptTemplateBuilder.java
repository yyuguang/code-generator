package com.lnzz.codegen.platform.domain.parser;

/**
 * @classname: AiPromptTemplateBuilder
 * @author: Fantasy
 * @date: 2026/4/8 21:41
 * @description: Build prompts for AI specification extraction.
 */
public class AiPromptTemplateBuilder {

    /**
     * Build the prompt used for specification extraction.
     *
     * @param requirementText natural language requirement
     * @return String
     * @author Fantasy
     * @date 2026/4/8 21:41
     */
    public String buildSpecificationPrompt(String requirementText) {
        return "You are an enterprise specification parser. "
                + "Convert the following requirement into strict JSON only. "
                + "Do not output markdown. "
                + "The JSON must contain project level fields and moduleSpecList. "
                + "Each module item must contain fieldSpecList and operationTypeList. "
                + "Requirement: " + requirementText;
    }
}
