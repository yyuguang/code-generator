package com.lnzz.codegen.platform.api.vo;

/**
 * @classname: HealthStatusVO
 * @author: Fantasy
 * @date: 2026/4/8 21:05
 * @description: View object returned by the health check API.
 */
public record HealthStatusVO(String status, String application) {
}
