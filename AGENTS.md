# AGENTS.md

------

## 0. 全局约定

对于任何工程任务：

👉 必须优先使用 **workflow-orchestrator 思维（流程驱动）**
👉 必须遵循 **lnzz-skills/core 主流程 Skill System**
👉 Superpowers 仅作为增强能力，不得替代主流程

```
对于任何工程任务，必须先经过 workflow-orchestrator 进行任务识别、流程路由与能力调度，再进入具体 core skill。
```

------

## 1. 文档目的

本文件定义本仓库中 AI Agent（Codex）执行任务时必须遵守的规则、流程和约束。

所有 AI 行为必须遵循以下原则：

- 文档驱动开发
- 单一职责流程
- 可追溯工程流程
- 明确的阶段状态管理

------

## 2. Skill 系统

本仓库采用 **分层 Skill System**：

```text
skills/
└── lnzz-skills/
    ├── core/
    └── superpowers/
```

------

## 2.1 主流程 Skill（Core）

主流程 Skill 是整个工程执行的唯一主干，不可被替代。

（以下保持不变）

| Skill                 | 职责           |
| --------------------- | -------------- |
| task-bootstrapper     | 初始化任务结构 |
| requirement-analyst   | 需求分析       |
| requirement-clarifier | 需求澄清       |
| system-architect      | 架构设计       |
| api-designer          | API 设计       |
| data-designer         | 数据模型设计   |
| task-planner          | 任务拆解       |
| spec-driven-coder     | 代码实现       |
| test-designer         | 测试方案设计   |
| test-writer           | 编写测试代码   |
| test-executor         | 执行测试       |
| code-reviewer         | 代码审查       |
| bug-triager           | 缺陷分析       |
| bug-fixer             | 缺陷修复       |
| release-manager       | 发布检查       |
| knowledge-curator     | 项目沉淀       |

------

## 2.2 Superpowers（辅助能力层）

目录：

```text
lnzz-skills/superpowers/
```

包含能力：

- brainstorming
- dispatching-parallel-agents
- executing-plans
- receiving-code-review
- requesting-code-review
- subagent-driven-development
- systematic-debugging
- test-driven-development
- using-git-worktrees
- using-superpowers
- verification-before-completion
- writing-plans
- writing-skills

------

## 2.3 主次关系（强约束）

必须遵守：

- ✅ core = 唯一流程驱动
- ✅ superpowers = 辅助能力增强
- ❌ 禁止 superpowers 接管流程
- ❌ 禁止跳过 core skill

优先级规则：

```
AGENTS.md
> lnzz-skills/core
> 用户任务
> lnzz-skills/superpowers
```

------

## 2.4 Superpowers 使用规则

### 允许使用场景

- 需求发散 → brainstorming
- 任务拆解增强 → writing-plans
- 执行约束 → executing-plans
- 测试增强 → test-driven-development
- 问题排查 → systematic-debugging
- 交付校验 → verification-before-completion
- 并行执行 → dispatching-parallel-agents
- 子任务拆分 → subagent-driven-development
- Code Review 协作 → requesting / receiving-code-review
- 多分支开发 → using-git-worktrees

------

### 禁止行为

- 用 executing-plans 替代 spec-driven-coder
- 用 writing-plans 替代 task-planner
- 用 debugging 替代 bug-triager
- 用 superpowers 完成完整流程

------

## 3. 默认工作流程（新功能开发）

（在原流程基础上增强）

```
task-bootstrapper
    ↓（可用 subagent-driven-development）
requirement-analyst
    ↓（可用 brainstorming）
requirement-clarifier
    ↓
system-architect
    ↓
api-designer
    ↓
data-designer
    ↓
task-planner
    ↓（可用 writing-plans / parallel-agents）
spec-driven-coder
    ↓（可用 executing-plans / TDD）
test-designer
    ↓
test-writer
    ↓（TDD）
test-executor
    ↓
code-reviewer
    ↓（request/review）
bug-triager
    ↓（systematic-debugging）
bug-fixer
    ↓
release-manager
    ↓（verification-before-completion）
knowledge-curator
```

------

## 4. Bug 修复流程

```
bug-triager
    ↓（systematic-debugging）
bug-fixer
    ↓
test-writer
    ↓
test-executor
    ↓
code-reviewer
```

如果问题涉及需求、设计或接口问题，必须回退阶段补文档。

------

## 5. 任务文档存储规则

所有 AI 工程任务文档必须放在：

```
ai_workspace/projects/
```

任务目录格式：

```
ai_workspace/projects/{task_name}/
```

`task_name` 必须使用 `snake_case`。

示例：

```
user_login_feature
order_timeout_fix
product_metadata_refactor
```

------

## 6. 任务目录结构

每个任务必须使用以下结构：

```
ai_workspace/projects/{task_name}/
├── 00_meta/
│   ├── TASK_CONTEXT.md
│   ├── PROJECT_INDEX.md
│   └── STATUS.md
├── 01_requirement/
│   ├── REQUIREMENT.md
│   └── CLARIFICATION.md
├── 02_design/
│   ├── DESIGN.md
│   ├── API_SPEC.md
│   └── DATA_MODEL.md
├── 03_plan/
│   └── TODO.md
├── 04_impl/
│   └── src/
├── 05_test/
│   ├── TESTPLAN.md
│   ├── tests/
│   └── TEST_REPORT.md
├── 06_review/
│   ├── REVIEW.md
│   ├── BUG_REPORT.md
│   └── FIX_LOG.md
├── 07_release/
│   └── RELEASE.md
└── 08_summary/
    └── SUMMARY.md
```

------

## 7. 状态机规则

项目必须维护统一状态文件：

```
00_meta/STATUS.md
```

状态列表如下：

```
initialized
requirement_ready
clarification_ready
design_ready
api_ready
data_ready
plan_ready
implementation_in_progress
implementation_done
testing_done
review_done
bugfix_in_progress
release_ready
archived
```

每完成一个阶段，必须更新 `STATUS.md`。

如果流程被阻断，必须在 `STATUS.md` 中写明：

- 当前阻断原因
- 阻断阶段
- 建议下一步动作

------

## 8. 文档语言规则

所有生成文档必须满足以下要求：

- 使用中文
- 使用 Markdown
- 使用结构化标题
- 包含文档信息头
- 明确最近更新阶段和更新原因

文档头模板如下：

```
# 文档信息

- 文档名称：
- 当前状态：
- 最近更新阶段：
- 最近更新原因：
```

------

## 9. 文档驱动规则

在本仓库中，AI Agent 必须遵守文档驱动原则。

### 9.1 新需求

对新需求，必须优先生成：

- `REQUIREMENT.md`
- `CLARIFICATION.md`

### 9.2 新设计

对新设计，必须优先生成：

- `DESIGN.md`
- `API_SPEC.md`
- `DATA_MODEL.md`

### 9.3 新开发

对开发实施，必须优先生成：

- `TODO.md`

在 `TODO.md` 未完成前，不允许进入大范围编码。

### 9.4 测试与审查

对测试与审查，必须生成：

- `TESTPLAN.md`
- `TEST_REPORT.md`
- `REVIEW.md`

### 9.5 缺陷处理

对缺陷处理，必须生成：

- `BUG_REPORT.md`
- `FIX_LOG.md`

### 9.6 发布与沉淀

任务结束前必须生成：

- `RELEASE.md`
- `SUMMARY.md`

------

## 10. 代码开发规则

AI 进行代码实现时必须遵守以下规则：

1. 必须基于 `TODO.md`
2. 一次只实现一个 TASK
3. 不允许跳过规划阶段直接大范围编码
4. 不允许在未设计的情况下实现复杂逻辑
5. 修改代码后必须记录变更说明
6. 推荐使用 test-driven-development
7. 禁止绕过 TODO.md
8. 复杂任务可使用 executing-plans 强化执行顺序

每次代码改动必须说明：

```
变更模块
变更原因
影响范围
验证方式
风险说明
```



------

## 11. Maven 多模块项目规则

本仓库为 Maven 多模块项目。

AI 修改代码时必须遵守以下规则。

### 11.1 模块隔离

修改前必须先确认变更所属模块，例如：

```
product-auth
product-gateway
product-metadata
oms-product-business
tms-product-business
wms-product-business
```

禁止无理由跨多个模块同时修改。

### 11.2 公共逻辑归属

公共逻辑优先放在合适的公共模块中，例如：

```
otwb-common
```

禁止将公共能力散落到多个业务模块重复实现。

### 11.3 网关模块约束

```
product-gateway
```

该模块职责通常包括：

- 路由转发
- 鉴权接入
- 流量控制
- 网关层编排

禁止在网关模块中实现核心业务逻辑。

### 11.4 认证模块约束

```
product-auth
```

该模块职责通常包括：

- 登录认证
- 注销登录
- Token/Session 处理
- 权限校验相关能力

禁止在认证模块中实现商品业务、订单业务或仓储业务逻辑。

### 11.5 业务模块约束

业务逻辑必须放在对应业务模块中，例如：

```
oms-product-business
tms-product-business
wms-product-business
```

禁止把明确的业务逻辑塞入公共模块或网关模块。

------

## 12. 编译与验证规则

AI 修改代码后必须执行验证。

### 12.1 单模块修改

优先执行对应模块测试：

```
mvn -pl {module} test
```

### 12.2 跨模块修改

如涉及公共模块或多个业务模块，优先执行更完整的验证：

```
mvn clean install
```

### 12.3 验证结果记录

验证后必须记录：

- 执行了什么命令
- 是否通过
- 是否存在失败项
- 失败项是否为环境问题
- 是否需要补充测试

禁止隐藏测试失败。

------

## 13. 代码审查规则

进入 `release-manager` 前，至少必须满足以下条件：

- `TODO.md` 中相关任务已完成或已明确标记状态
- `TEST_REPORT.md` 已生成
- `REVIEW.md` 已生成
- 所有高优先级 BUG 已关闭，或已明确记录风险接受结论

如果不满足以上条件，默认不进入发布阶段。

允许流程：

```
requesting-code-review
→ receiving-code-review
→ code-reviewer
```

------

## 14. 发布规则

发布前必须执行：

```
verification-before-completion
```

至少必须包含：

- 发布范围
- 已完成项
- 未完成项
- 已知风险
- 发布建议
- 发布后观察建议

如果存在高风险未关闭问题，必须明确写出：

```
不建议发布
```

------

## 15. 知识沉淀规则

任务结束后必须生成：

```
08_summary/SUMMARY.md
```

总结内容至少包括：

- 关键设计决策
- 关键问题
- 修复经验
- 可复用经验
- 后续优化建议

------

## 16. Agent 行为原则

### 原则 1：优先使用主流程 Skill

优先使用：

```
lnzz-skills/core
```

必要时使用：

```
lnzz-skills/superpowers
```

### 原则 2：优先生成文档

优先生成需求、设计、计划、测试、审查文档，而不是一开始直接写代码。

### 原则 3：发现缺失必须回写

当发现需求、设计、接口、数据或测试信息缺失时，必须回写问题，不能假装信息完整。

### 原则 4：禁止一次性大规模修改

默认采用小步迭代，禁止一次性大规模跨模块修改，除非用户明确要求且已完成必要设计。

### 原则 5：重大变更必须先计划

任何重大改动必须先给出计划和影响分析，再开始编码。

------

## 17. Agent 默认行为

```
1. 识别任务类型
2. 选择 core skill
3. 判断是否需要 superpowers 增强
4. 严格按流程推进
5. 写入文档
6. 更新 STATUS.md
```

------

## 18. 不允许的行为

AI Agent 不允许：

- 跳过需求直接编码
- 在没有 `TODO.md` 的情况下大范围开发
- 修改多个模块但不说明原因
- 隐藏测试失败
- 隐藏缺陷
- 伪造测试结果
- 修改历史文档结论而不留痕迹
- 把假设内容伪装成已确认事实

- 使用 superpowers 替代 core
- 并行执行但未做依赖分析

------

## 19. Agent 自检清单

在任务完成前，必须自检以下事项：

```
是否生成需求文档
是否生成设计文档
是否生成任务规划
是否执行测试
是否完成代码审查
是否更新 STATUS.md
是否记录风险与验证结果
是否合理使用 superpowers
```

------

## 20. 默认语言

以下内容默认使用中文：

- 文档
- 设计说明
- 分析报告
- 审查结论
- 缺陷报告
- 发布说明
- 总结沉淀

代码、命令、路径、模块名保持原始技术格式。

------

## 21. 优先级规则

```
AGENTS.md
> lnzz-skills/core
> 用户任务
> lnzz-skills/superpowers
```

------

## 22. 示例

### 示例 1：新功能

用户输入：

```
为 product-auth 模块新增用户注销接口
```

Agent 默认应先走：

```
task-bootstrapper
→ requirement-analyst
→ requirement-clarifier
→ system-architect
→ api-designer
→ data-designer
→ task-planner
```

完成规划后，再进入开发阶段。

### 示例 2：缺陷修复

用户输入：

```
修复 product-gateway 模块鉴权失败问题
```

Agent 默认应先走：

```
bug-triager
→ bug-fixer
→ test-writer
→ test-executor
→ code-reviewer
```

如果修复过程中发现需求或设计问题，再回退到对应阶段补文档。

------

## 23. 项目规则扩展

### 项目规则扩展

AI 在执行任务时，必须同时遵守：

- `.ai_rules/CODING_STYLE.md`
- `.ai_rules/DB_STYLE.md`
- `.ai_rules/API_STYLE.md`

## 24. 最终目标

确保 AI 在本仓库中具备以下能力：

- 可控的软件工程流程
- 完整的文档追溯能力
- 可重复执行的开发过程
- 低风险代码修改机制
- 明确的验证、审查和发布闭环

- 可扩展的 AI 能力体系（Superpowers）