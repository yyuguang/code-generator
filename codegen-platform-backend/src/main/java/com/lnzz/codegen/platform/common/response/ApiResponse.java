package com.lnzz.codegen.platform.common.response;

import java.util.UUID;

/**
 * @classname: ApiResponse
 * @author: Fantasy
 * @date: 2026/4/8 21:05
 * @description: Unified API response structure.
 */
public record ApiResponse<T>(Integer code, String message, T data, String traceId) {

    private static final Integer SUCCESS_CODE = 0;
    private static final String SUCCESS_MESSAGE = "success";

    /**
     * Build a success response.
     *
     * @param data response payload
     * @return ApiResponse<T>
     * @author Fantasy
     * @date 2026/4/8 21:05
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, data, UUID.randomUUID().toString());
    }
}
