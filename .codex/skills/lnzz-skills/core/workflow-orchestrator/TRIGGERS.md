# TRIGGERS.md

## 目的

定义 workflow-orchestrator 的自动触发规则。

用于：

- 自动识别任务类型
- 自动选择主流程
- 自动决定是否使用 superpowers

------

## 1. 触发优先级

当匹配多个规则时，优先级如下：

```
Bug修复
> 发布检查
> 新功能开发
> 设计补充
> 测试补充
> 默认（新功能）
```

------

## 2. Bug 修复触发规则

### 关键词

```
修复
bug
报错
异常
失败
问题
不生效
不工作
error
fix
```

### 示例

- 修复登录失败问题
- 接口报错 500
- 网关鉴权异常

### 行为

- 任务类型：Bug 修复
- 入口 Skill：bug-triager
- 强制使用：
  - systematic-debugging（推荐）

------

## 3. 发布检查触发规则

### 关键词

```
可以上线吗
能发布吗
是否可以发布
发布检查
上线风险
ready to release
```

### 行为

- 任务类型：发布检查
- 入口 Skill：release-manager
- 强制使用：
  - verification-before-completion

------

## 4. 新功能开发触发规则

### 关键词

```
新增
添加
实现
开发
支持
增加接口
新增接口
实现功能
build
implement
```

### 示例

- 新增用户注销接口
- 实现订单超时取消
- 添加权限校验

### 行为

- 任务类型：新功能开发
- 入口 Skill：task-bootstrapper
- 推荐使用：
  - brainstorming（需求不清晰）
  - writing-plans（复杂任务）

------

## 5. 设计补充触发规则

### 关键词

```
设计
方案
架构
如何实现
技术选型
设计一个
设计方案
architecture
design
```

### 行为

- 任务类型：设计补充
- 入口 Skill：requirement-analyst
- 推荐使用：
  - brainstorming

------

## 6. 测试补充触发规则

### 关键词

```
测试
测试用例
覆盖率
单测
测试代码
test
unit test
```

### 行为

- 任务类型：测试补充
- 入口 Skill：test-designer
- 推荐使用：
  - test-driven-development

------

## 7. 并行任务触发规则

### 条件

满足以下任意：

- 出现 “多个模块”
- 出现 “同时优化”
- 出现 “批量处理”
- 出现 “并行”
- 明确多个独立子任务

### 行为

- 允许使用：
  - subagent-driven-development
  - dispatching-parallel-agents

⚠️ 前提：

- 必须先完成依赖分析
- 禁止直接并行编码

------

## 8. 高风险任务识别规则

### 关键词

```
网关
鉴权
认证
公共模块
数据模型
数据库
接口变更
协议变更
```

### 行为

- 风险级别：高
- 强制要求：
  - 完整设计流程
  - 不允许跳过 system-architect
  - 发布前必须 verification-before-completion

------

## 9. 默认规则

当无法明确分类时：

- 任务类型：新功能开发
- 入口 Skill：task-bootstrapper

------

## 10. 冲突处理规则

当一个输入同时匹配多个类型：

### 示例

```
修复登录接口并新增日志
```

处理方式：

1. 拆分为多个子任务：
   - Bug 修复
   - 新功能开发
2. 分别走流程：

- Bug → bug-triager
- Feature → task-bootstrapper

------

## 11. 输出要求

触发后，必须输出：

- 任务类型
- 匹配规则
- 选择理由
- 入口 skill
- 是否使用 superpowers
- 下一步动作

------

## 一句话总结

Trigger Rules =
输入识别器 + 流程自动选择器 + 风险感知器