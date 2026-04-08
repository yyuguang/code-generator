---
name: workflow-orchestrator
description: Entry skill for coordinating software engineering workflows in this repository. Use this skill whenever a user asks to implement a feature, fix a bug, refactor code, design APIs, or perform release preparation. This skill analyzes the task type and dispatches the correct engineering workflow using other skills such as requirement analysis, architecture design, planning, coding, testing, and review.
---

# workflow-orchestrator

## 执行策略约束

本 skill 必须结合以下规则执行：

- TRIGGERS.md（任务识别）
- EXECUTION_POLICY.md（执行策略控制）

所有任务在进入具体 core skill 前，必须完成：

1. 执行模式判定（AUTO / STEP / CONFIRM / PLAN ONLY）
2. 风险级别判定
3. 是否需要用户确认

未经 workflow-orchestrator 判定执行策略，不允许直接进入后续 skill。

## 目的

workflow-orchestrator 是本仓库所有工程任务的统一入口 skill。

它不直接替代各阶段 skill，也不直接承担具体需求分析、设计、编码、测试、发布职责。  
它的职责是：

1. 识别任务类型
2. 选择正确的 core 主流程
3. 判断是否需要引入 superpowers 辅助能力
4. 控制阶段推进顺序
5. 确保任务进入可追溯、可验证、可回退的工程闭环

---

## 核心原则

- 必须优先遵循 AGENTS.md
- 必须优先使用 lnzz-skills/core 中的主流程 skill
- superpowers 只能作为辅助能力，不得替代主流程
- 必须先规划、后实施、再验证、最后沉淀
- 必须保证所有任务有明确阶段状态
- 必须保证任务产物可写入规定目录

---

## 适用场景

以下任务都必须先经过 workflow-orchestrator：

- 新功能开发
- 已知 Bug 修复
- 架构设计补充
- API 设计补充
- 数据模型设计补充
- 测试补充
- 发布前检查
- 文档补齐
- 复杂任务拆分
- 多模块改造

---

## 非职责范围

workflow-orchestrator 不直接做以下事情：

- 不直接编写业务代码
- 不直接替代 requirement-analyst / task-planner / spec-driven-coder
- 不直接替代 test-writer / test-executor / code-reviewer
- 不直接产出最终设计结论
- 不直接跳过流程进入实现

它只负责“调度、分流、约束、检查”。

---

## 输入识别规则

接收到用户任务后，必须先判断任务类型。

### 1. 新功能开发

典型特征：

- 新增接口
- 新增能力
- 新增页面
- 新增模块逻辑
- 新增业务规则

默认主流程：

task-bootstrapper
→ requirement-analyst
→ requirement-clarifier
→ system-architect
→ api-designer
→ data-designer
→ task-planner
→ spec-driven-coder
→ test-designer
→ test-writer
→ test-executor
→ code-reviewer
→ bug-triager
→ bug-fixer
→ release-manager
→ knowledge-curator

---

### 2. Bug 修复

典型特征：

- 修复异常
- 修复报错
- 修复失败问题
- 修复逻辑错误
- 排查线上问题

默认主流程：

bug-triager
→ bug-fixer
→ test-writer
→ test-executor
→ code-reviewer

如果修复过程中发现需求、设计、接口、数据模型存在缺失或冲突，必须回退到对应阶段补文档。

---

### 3. 设计补充

典型特征：

- 补设计方案
- 补 API 定义
- 补数据模型
- 方案评审
- 技术选型

默认主流程：

task-bootstrapper
→ requirement-analyst
→ requirement-clarifier
→ system-architect
→ api-designer
→ data-designer
→ knowledge-curator

---

### 4. 测试补充

典型特征：

- 补测试用例
- 补自动化测试
- 补测试报告
- 提升覆盖率

默认主流程：

test-designer
→ test-writer
→ test-executor
→ code-reviewer

---

### 5. 发布检查

典型特征：

- 是否可发布
- 发布前检查
- 风险评估
- 发布说明整理

默认主流程：

release-manager
→ knowledge-curator

---

## Superpowers 调度规则

只有在当前阶段确实需要增强时，才允许调用 superpowers。

### 映射关系

| core skill          | 可选 superpowers                               |
| ------------------- | ---------------------------------------------- |
| task-bootstrapper   | subagent-driven-development                    |
| requirement-analyst | brainstorming                                  |
| task-planner        | writing-plans / dispatching-parallel-agents    |
| spec-driven-coder   | executing-plans / test-driven-development      |
| bug-triager         | systematic-debugging                           |
| bug-fixer           | systematic-debugging                           |
| code-reviewer       | requesting-code-review / receiving-code-review |
| release-manager     | verification-before-completion                 |

---

## Superpowers 使用边界

### 允许

- 发散方案
- 优化计划质量
- 强化执行顺序
- 系统化调试
- 增强测试约束
- 增强发布前校验
- 对复杂任务进行拆分
- 对可并行子任务进行调度

### 禁止

- 用 writing-plans 替代 task-planner
- 用 executing-plans 替代 spec-driven-coder
- 用 systematic-debugging 替代 bug-triager
- 用 superpowers 直接完成完整开发流程
- 在未识别依赖关系前直接并行拆分任务

---

## 调度执行步骤

每次任务必须按以下步骤执行。

### Step 1：识别任务类型

判断当前任务属于：

- 新功能
- Bug 修复
- 设计补充
- 测试补充
- 发布检查
- 混合型任务

若为混合型任务，必须先拆分主任务与子任务，再分别归入对应流程。

---

### Step 2：识别影响范围

必须明确：

- 所属模块
- 是否跨模块
- 是否涉及公共模块
- 是否涉及接口变更
- 是否涉及数据结构变更
- 是否涉及发布风险

---

### Step 3：确定任务目录

若是新任务，先要求按规范创建目录：

ai_workspace/projects/{task_name}/

并建立标准结构：

- 00_meta
- 01_requirement
- 02_design
- 03_plan
- 04_impl
- 05_test
- 06_review
- 07_release
- 08_summary

---

### Step 4：选择主流程

根据任务类型，选择对应 core 流程。

---

### Step 5：判断是否插入 superpowers

按以下原则判断：

- 是否需要更强的需求发散
- 是否需要更细的实施计划
- 是否需要分步执行约束
- 是否需要系统性排障
- 是否需要并行拆分
- 是否需要发布前全面校验

只有答案明确为“需要”时才插入。

---

### Step 6：生成当前阶段动作建议

输出必须至少包含：

- 当前任务类型
- 当前建议进入的 skill
- 是否需要 superpowers
- 当前阶段目标
- 当前阶段产物
- 下一步动作

---

### Step 7：推进与回退控制

若某阶段发现信息不足，必须：

- 停止盲目推进
- 明确缺口
- 回退到上游阶段
- 补齐文档后再继续

---

## 输出格式规范

每次调度时，建议输出以下结构：

### 任务识别结果

- 任务类型：
- 所属模块：
- 是否跨模块：
- 风险级别：

### 主流程选择

- 当前入口 skill：
- 后续核心链路：

### Superpowers 判断

- 是否需要：
- 若需要，使用哪些：
- 使用原因：

### 当前阶段目标

- 目标：
- 产物：
- 完成标准：

### 下一步动作

- 立即执行：
- 后续阶段：

---

## 特殊规则

### 1. 多模块任务

如果任务涉及多个模块：

- 必须先做影响分析
- 优先拆分为可独立子任务
- 只有在依赖清晰时才允许 dispatching-parallel-agents

### 2. 高风险任务

以下视为高风险：

- 公共模块改动
- 鉴权改动
- 网关改动
- 数据模型改动
- 接口协议改动
- 跨模块联动改动

高风险任务必须增加：

- 设计补充
- 风险分析
- 更严格测试
- 发布前校验

### 3. 设计缺失

若发现设计缺失：

- 不允许直接编码
- 必须回到 system-architect / api-designer / data-designer

### 4. TODO 缺失

若 `TODO.md` 未完成：

- 不允许进入大范围开发

---

## 完成判定

workflow-orchestrator 完成其职责的标准不是“代码写完”，而是：

- 任务被正确分类
- 主流程被正确选择
- superpowers 被合理调度
- 当前阶段目标明确
- 当前阶段产物明确
- 下一步动作明确
- 无越权、无跳步、无流程混乱

---

## 一句话定义

workflow-orchestrator =  
任务入口识别器 + 流程路由器 + 能力调度器 + 阶段守门人