# 文档信息

- 文档名称：DATA_MODEL.md
- 当前状态：已完成
- 最近更新阶段：数据设计
- 最近更新原因：定义统一规格中心及生成流程所需核心数据模型

# 数据设计概览

系统数据模型围绕“规格中心”展开，重点不是业务库实体本身，而是支持需求解析、规格校验、状态流转、代码生成与导出所需的核心对象。MVP 按单模块场景设计，但结构上保留多模块扩展能力。

# 核心实体列表

## ENTITY-1 ProjectSpec
- 名称：ProjectSpec
- 描述：表示一次生成任务对应的项目级规格对象，是整个规格事实源入口。
- 字段定义：
  - `id`：规格主键，字符串或雪花 ID
  - `projectName`：项目名称
  - `groupId`：Maven 组织标识
  - `artifactId`：Maven 工件标识
  - `packageName`：基础包名
  - `author`：作者
  - `status`：规格状态，如草稿、校验中、待确认、已确认、已生成
  - `sourceRequirementText`：原始需求文本
  - `markdownSnapshot`：规格 Markdown 视图快照
  - `createdAt` / `updatedAt`
- 约束：`projectName`、`artifactId`、`packageName` 不能为空。
- 索引建议：主键索引、状态索引、创建时间索引。

## ENTITY-2 ModuleSpec
- 名称：ModuleSpec
- 描述：项目内的模块级规格对象。MVP 仅启用一条记录，但模型允许一个项目下存在多个模块。
- 字段定义：
  - `id`
  - `projectSpecId`
  - `moduleName`：模块英文名
  - `displayName`：模块中文名
  - `tableName`：表名
  - `description`：模块描述
  - `generateCrud`：是否生成 CRUD
  - `enablePageQuery`：是否启用分页查询
  - `sortOrder`
- 约束：同一项目内 `moduleName` 与 `tableName` 唯一。
- 索引建议：`projectSpecId + moduleName` 唯一索引，`projectSpecId + tableName` 唯一索引。

## ENTITY-3 FieldSpec
- 名称：FieldSpec
- 描述：模块字段定义对象，驱动 SQL、实体类、DTO/VO 与接口参数生成。
- 字段定义：
  - `id`
  - `moduleSpecId`
  - `fieldName`
  - `displayName`
  - `dataType`：统一规格类型，如 String、Long、Integer、BigDecimal、LocalDateTime
  - `columnName`
  - `length`
  - `precision`
  - `scale`
  - `isPrimaryKey`
  - `isRequired`
  - `isUnique`
  - `defaultValue`
  - `description`
  - `sourceType`：AI 推断 / 系统补全 / 用户编辑
  - `confidenceLevel`：高 / 中 / 低
  - `validationStatus`：通过 / 警告 / 错误
  - `sortOrder`
- 约束：同一模块内 `fieldName` 与 `columnName` 不允许重复；至少存在一个主键字段。
- 索引建议：`moduleSpecId + fieldName` 唯一索引，`moduleSpecId + sortOrder` 普通索引。

## ENTITY-4 OperationSpec
- 名称：OperationSpec
- 描述：模块生成操作配置，控制 Controller/Service/Mapper 方法集合。
- 字段定义：
  - `id`
  - `moduleSpecId`
  - `operationType`：create、update、delete、detail、page、list 等
  - `enabled`
  - `queryFields`：用于条件查询的字段集合
  - `description`
- 约束：同一模块内同一 `operationType` 仅允许一条记录。
- 索引建议：`moduleSpecId + operationType` 唯一索引。

## ENTITY-5 ValidationIssue
- 名称：ValidationIssue
- 描述：规格校验结果对象，用于承载错误、警告与建议。
- 字段定义：
  - `id`
  - `projectSpecId`
  - `level`：error / warning / suggestion
  - `targetType`：project / module / field / operation
  - `targetId`
  - `ruleCode`
  - `message`
  - `recommendedFix`
  - `isBlocking`
  - `createdAt`
- 约束：阻断性错误必须在确认前清零。
- 索引建议：`projectSpecId + level` 组合索引。

## ENTITY-6 GenerationRecord
- 名称：GenerationRecord
- 描述：记录一次基于规格生成代码预览与导出的执行结果。
- 字段定义：
  - `id`
  - `projectSpecId`
  - `specVersion`
  - `generationStatus`：预览中、已预览、已导出、失败
  - `artifactSummary`
  - `previewSnapshotPath` 或内存引用标识
  - `exportFilePath`
  - `createdAt`
- 约束：同一规格版本可存在多次生成记录，但需保留最新有效记录。
- 索引建议：`projectSpecId + createdAt` 索引。

# 实体关系

- `ProjectSpec` 1 对多 `ModuleSpec`
- `ModuleSpec` 1 对多 `FieldSpec`
- `ModuleSpec` 1 对多 `OperationSpec`
- `ProjectSpec` 1 对多 `ValidationIssue`
- `ProjectSpec` 1 对多 `GenerationRecord`

MVP 中虽然只使用单模块，但关系模型保持可扩展，后续多模块场景无需推翻主数据结构。

# 数据一致性要求

1. `ProjectSpec` 状态流转必须与校验、确认、生成动作一致。
2. `FieldSpec` 的 `validationStatus` 应与 `ValidationIssue` 汇总结果保持一致。
3. 代码生成只能读取“已确认”或满足生成条件的规格版本。
4. 当用户修改规格后，历史生成记录应与新规格版本区分，避免预览与导出内容混淆。

# 数据风险与注意事项

- 若 AI 输出与用户编辑混杂但没有来源标记，后续可信度展示与回溯会受损。
- 若不约束字段类型枚举，模板映射将产生大量不确定分支。
- 若未区分校验问题的阻断级别，前端流程会难以判断能否进入下一步。
- 多模块扩展时需要新增模块关系实体或关系配置字段，当前应至少保留扩展入口。
