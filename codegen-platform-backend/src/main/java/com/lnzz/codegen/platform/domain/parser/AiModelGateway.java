package com.lnzz.codegen.platform.domain.parser;

/**
 * @classname: AiModelGateway
 * @author: Fantasy
 * @date: 2026/4/8 21:41
 * @description: Gateway abstraction for AI model calls.
 */
@FunctionalInterface
public interface AiModelGateway {

    /**
     * Request specification JSON from AI model.
     *
     * @param prompt prompt sent to the model
     * @param requirementText natural language requirement
     * @return String
     * @author Fantasy
     * @date 2026/4/8 21:41
     */
    String requestSpecificationJson(String prompt, String requirementText);
}
