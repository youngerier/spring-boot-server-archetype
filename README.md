# Java 项目 Archetype 文档

本项目是一个 Maven Archetype，用于快速生成标准化的 Java 项目骨架（代码结构、基础依赖与配置）。本文档说明如何在本地安装、发布到仓库，以及如何使用该 Archetype 生成项目。

## 前置条件
- 已安装 `JDK`（建议 11 或以上）
- 已安装 `Apache Maven`（建议 3.8+）
- 可访问的 Maven 仓库（本地仓库默认存在，远程仓库如 Nexus/Sonatype 可选）

## 术语说明
- `archetypeGroupId` / `archetypeArtifactId` / `archetypeVersion`：指本 Archetype 的坐标（从本项目 `pom.xml` 中获取你实际的坐标）。
- `groupId` / `artifactId` / `package` / `version`：指你要生成的目标项目的坐标与包名。

---

## 版本管理（revision）
- 模板策略：
  - 生成项目的所有 `pom.xml` 会保留字面量 `<version>${revision}</version>`，不会在生成阶段替换。
  - 根模板 `pom.xml` 的 `<properties>` 中定义 `<revision>${version}</revision>`，使用原型的模板变量 `version` 作为默认版本源。
- 使用方式：
  - 在生成项目时通过 `-Dversion=1.0.0` 设定默认的 `revision` 值（这会被写入根 POM 的 `<properties><revision>...</revision></properties>`）。
  - 在构建或 CI 中可覆盖：`mvn -Drevision=1.2.3 clean package`（无需改动 POM，即可注入发布版本）。
- 说明：
  - `${artifactId}`、`${groupId}`、`${package}`、`${version}` 等是 Archetype 的模板变量，发生在“生成阶段”。
  - `${revision}` 是 Maven 属性，占位在 POM 中，发生在“构建阶段”。
  - 已启用 `flatten-maven-plugin`（在模板根 POM 中），便于发布时将有效版本等信息展平。

## 本地安装（开发/验证）
在本项目根目录执行：

```bash
mvn -DskipTests clean install
```

执行成功后，Archetype 会被安装到本地 Maven 仓库（`~/.m2/repository`）。

### 使用本地已安装的 Archetype 生成项目
- 交互式生成（将列出本地可用的 archetype）：
  ```bash
  mvn archetype:generate -DarchetypeCatalog=local
  ```

- 非交互式生成（直接指定坐标与目标项目信息）：
  ```bash
  mvn archetype:generate \
    -DarchetypeCatalog=local \
    -DarchetypeGroupId=io.github.youngerier \
    -DarchetypeArtifactId=spring-boot-server-archetype \
    -DarchetypeVersion=0.0.3 \
    -DgroupId=com.example \
    -DartifactId=my-app \
    -Dpackage=com.example.myapp \
    -Dversion=0.1.0-SNAPSHOT \
    -DappPrefix=myapp \
    -DinteractiveMode=false
  ```

  Windows（PowerShell）示例：
  ```powershell
  mvn archetype:generate `
    -DarchetypeCatalog=local `
    -DarchetypeGroupId=io.github.youngerier `
    -DarchetypeArtifactId=spring-boot-server-archetype `
    -DarchetypeVersion=0.0.3 `
    -DgroupId=com.example `
    -DartifactId=my-app `
    -Dpackage=com.example.myapp `
    -Dversion=0.1.0-SNAPSHOT `
    -DinteractiveMode=false
  ```

  Windows（CMD）示例：
  ```cmd
  mvn archetype:generate ^
    -DarchetypeCatalog=local ^
    -DarchetypeGroupId=io.github.youngerier ^
    -DarchetypeArtifactId=spring-boot-server-archetype ^
    -DarchetypeVersion=0.0.3 ^
    -DgroupId=com.example ^
    -DartifactId=my-app ^
    -Dpackage=com.example.myapp ^
    -Dversion=0.1.0-SNAPSHOT ^
    -DinteractiveMode=false
  ```

> 生成的项目会出现在你执行命令的当前目录下的 `my-app/`。

---

## 发布到远程仓库（团队共享）
你可以将 Archetype 部署到企业 Nexus（或 Sonatype/Maven Central）以便团队使用。

### 配置发布仓库（示例：Nexus）

在 `~/.m2/settings.xml` 中配置凭证：


随后执行：

```bash
# 发布快照版或正式版（取决于项目版本是否以 -SNAPSHOT 结尾）
- mvn -P release -Dmaven.test.skip=true -s ./settings.xml clean deploy
```