package com.lnzz.codegen.platform.domain.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnzz.codegen.platform.domain.spec.ProjectSpec;

/**
 * @classname: AiSpecJsonParser
 * @author: Fantasy
 * @date: 2026/4/8 21:30
 * @description: Parse AI generated JSON into unified specification objects.
 */
public class AiSpecJsonParser {

    private final ObjectMapper objectMapper;

    public AiSpecJsonParser() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Parse JSON into a project specification object.
     *
     * @param specificationJson AI generated specification JSON
     * @return ProjectSpec
     * @author Fantasy
     * @date 2026/4/8 21:30
     */
    public ProjectSpec parseProjectSpec(String specificationJson) {
        try {
            return objectMapper.readValue(specificationJson, ProjectSpec.class);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("failed to parse AI specification JSON", exception);
        }
    }
}
