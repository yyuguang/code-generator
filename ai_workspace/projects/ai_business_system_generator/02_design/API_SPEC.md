# 文档信息

- 文档名称：API_SPEC.md
- 当前状态：已完成
- 最近更新阶段：接口设计
- 最近更新原因：为 MVP 主流程定义后端服务接口规范

# 接口概述

MVP 接口围绕“需求输入 -> 规格生成 -> 规格校验/确认 -> 代码预览 -> 工程导出”主链路设计，优先满足单模块生成场景。接口粒度以平台后端服务为准，不等同于最终生成项目中的业务 CRUD 接口。

# 鉴权说明

- MVP 阶段可先按内部平台使用场景设计，默认预留统一登录态或 Token 鉴权接入位。
- 若首版仅用于内测，可允许在受控环境中关闭复杂权限校验，但接口设计需保留鉴权字段与中间件扩展点。

# 通用约定

- 接口基路径建议：`/api/v1`
- 返回结构建议统一为：`code`、`message`、`data`、`traceId`
- 规格事实源采用 JSON
- 对校验失败、生成失败、导出失败统一返回标准错误码与错误详情

# 错误码规范

- `SPEC_VALIDATION_ERROR`：规格校验失败
- `AI_PARSE_ERROR`：AI 解析失败或输出不可解析
- `TEMPLATE_RENDER_ERROR`：模板渲染失败
- `EXPORT_BUILD_ERROR`：导出打包失败
- `BAD_REQUEST`：请求参数不完整或格式错误

# 接口列表

## API-1 提交需求并生成规格草稿
- 路径：`POST /api/v1/specifications/draft`
- 方法：POST
- 描述：接收自然语言需求与基础项目配置，调用 AI 解析并返回规格草稿及初步校验结果。
- 鉴权要求：需要登录态或平台 Token
- 请求参数：
  - `projectName`：项目名称
  - `packageName`：基础包名
  - `author`：作者
  - `requirementText`：自然语言需求文本
  - `options`：生成选项对象
- 响应结构：
  - `specId`：规格标识
  - `draftSpec`：结构化规格草稿
  - `validationSummary`：校验摘要
  - `markdownPreview`：规格 Markdown 预览
- 错误响应：`BAD_REQUEST`、`AI_PARSE_ERROR`
- 约束说明：第一版仅接受单模块需求；若识别出多模块，应提示拆分或作为未来扩展能力处理。

## API-2 校验规格
- 路径：`POST /api/v1/specifications/validate`
- 方法：POST
- 描述：对前端提交的规格对象执行规则校验并返回错误、警告与标准化建议。
- 鉴权要求：需要登录态或平台 Token
- 请求参数：
  - `specId`：规格标识（可选）
  - `spec`：完整规格对象
- 响应结构：
  - `passed`：是否通过硬规则
  - `errors`：错误列表
  - `warnings`：警告列表
  - `suggestions`：建议修正列表
  - `normalizedSpec`：标准化后的规格
- 错误响应：`BAD_REQUEST`、`SPEC_VALIDATION_ERROR`
- 约束说明：硬规则不通过时不得进入代码生成。

## API-3 确认规格
- 路径：`POST /api/v1/specifications/confirm`
- 方法：POST
- 描述：保存用户最终确认的规格版本，并将状态切换为可生成。
- 鉴权要求：需要登录态或平台 Token
- 请求参数：
  - `specId`：规格标识
  - `spec`：用户确认后的规格对象
  - `confirmComment`：可选确认说明
- 响应结构：
  - `specId`
  - `status`：确认后的状态
  - `confirmedAt`：确认时间
- 错误响应：`BAD_REQUEST`、`SPEC_VALIDATION_ERROR`
- 约束说明：确认前应至少完成一次成功校验。

## API-4 生成代码预览
- 路径：`POST /api/v1/generations/preview`
- 方法：POST
- 描述：根据确认规格生成代码预览结果，返回文件树与关键文件内容。
- 鉴权要求：需要登录态或平台 Token
- 请求参数：
  - `specId`：规格标识
  - `specVersion`：规格版本（可选）
- 响应结构：
  - `generationId`：生成任务标识
  - `fileTree`：文件树
  - `previewFiles`：预览文件内容集合
  - `artifactSummary`：生成产物摘要
- 错误响应：`BAD_REQUEST`、`TEMPLATE_RENDER_ERROR`
- 约束说明：预览结果应与导出结果保持一致，不允许预览与导出使用不同规格源。

## API-5 导出工程 ZIP
- 路径：`POST /api/v1/generations/export`
- 方法：POST
- 描述：基于指定生成结果组装工程并返回下载地址或文件流。
- 鉴权要求：需要登录态或平台 Token
- 请求参数：
  - `generationId`：生成任务标识
  - `exportFormat`：导出格式，默认 `zip`
- 响应结构：
  - `downloadUrl` 或文件流
  - `fileName`
  - `expiresAt`：下载有效期（如适用）
- 错误响应：`BAD_REQUEST`、`EXPORT_BUILD_ERROR`
- 约束说明：导出前默认已完成预览；若生成结果失效需重新生成。

## API-6 获取规格详情
- 路径：`GET /api/v1/specifications/{specId}`
- 方法：GET
- 描述：查询规格详情、当前状态、最新校验信息和 Markdown 视图。
- 鉴权要求：需要登录态或平台 Token
- 请求参数：路径参数 `specId`
- 响应结构：
  - `specId`
  - `status`
  - `spec`
  - `validationSummary`
  - `markdownPreview`
- 错误响应：`BAD_REQUEST`
- 约束说明：为后续规格回看和重新生成提供查询入口。
