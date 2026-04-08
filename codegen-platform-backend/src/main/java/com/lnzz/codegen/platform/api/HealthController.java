package com.lnzz.codegen.platform.api;

import com.lnzz.codegen.platform.api.vo.HealthStatusVO;
import com.lnzz.codegen.platform.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @classname: HealthController
 * @author: Fantasy
 * @date: 2026/4/8 21:05
 * @description: System health check endpoint.
 */
@RestController
@RequestMapping("/api/v1/system")
public class HealthController {

    /**
     * Query current system health status.
     *
     * @param none no request parameter
     * @return ApiResponse<HealthStatusVO>
     * @author Fantasy
     * @date 2026/4/8 21:05
     */
    @GetMapping("/health")
    public ApiResponse<HealthStatusVO> queryHealthStatus() {
        HealthStatusVO healthStatusVO = new HealthStatusVO("UP", "ai-codegen-platform");
        return ApiResponse.success(healthStatusVO);
    }
}
