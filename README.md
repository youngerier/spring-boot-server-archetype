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
    -DarchetypeVersion=0.0.1-SNAPSHOT \
    -DgroupId=com.example \
    -DartifactId=my-app \
    -Dpackage=com.example.myapp \
    -Dversion=0.1.0-SNAPSHOT \
    -DinteractiveMode=false
  ```

> 生成的项目会出现在你执行命令的当前目录下的 `my-app/`。

---

## 发布到远程仓库（团队共享）
你可以将 Archetype 部署到企业 Nexus（或 Sonatype/Maven Central）以便团队使用。

### 配置发布仓库（示例：Nexus）
在本项目 `pom.xml` 中添加（或确认）如下 `distributionManagement`：

```xml
<distributionManagement>
  <repository>
    <id>releases</id>
    <url>https://nexus.example.com/repository/maven-releases</url>
  </repository>
  <snapshotRepository>
    <id>snapshots</id>
    <url>https://nexus.example.com/repository/maven-snapshots</url>
  </snapshotRepository>
</distributionManagement>
```

在 `~/.m2/settings.xml` 中配置凭证（与上面的 `<id>` 对应）：

```xml
<settings>
  <servers>
    <server>
      <id>releases</id>
      <username>your-username</username>
      <password>your-password</password>
    </server>
    <server>
      <id>snapshots</id>
      <username>your-username</username>
      <password>your-password</password>
    </server>
  </servers>
</settings>
```

随后执行：

```bash
# 发布快照版或正式版（取决于项目版本是否以 -SNAPSHOT 结尾）
mvn -DskipTests clean deploy
```

> 若发布到 Maven Central，还需配置 GPG 签名与 Sonatype 账号，并使用 `nexus-staging-maven-plugin` 完成关闭与释放仓库。企业内部 Nexus 一般不需要签名。

---

## 远程使用（从 Nexus/Sonatype 拉取）
当 Archetype 已发布到远程仓库后，任何人都可以按坐标使用：

```bash
mvn archetype:generate \
  -DarchetypeGroupId=<你的-archetypeGroupId> \
  -DarchetypeArtifactId=<你的-archetypeArtifactId> \
  -DarchetypeVersion=<你的-archetypeVersion> \
  -DgroupId=com.example \
  -DartifactId=my-app \
  -Dpackage=com.example.myapp \
  -Dversion=0.1.0-SNAPSHOT \
  -DinteractiveMode=false
```

> 如果远程仓库不是公共仓库（如公司 Nexus），请确保在 `~/.m2/settings.xml` 中配置了相应的 `<repositories>` 或通过父 POM/企业 BOM 引入仓库地址与访问权限。

---

## 示例：快速生成与构建
```bash
# 1) 在任意空目录下执行生成
mvn archetype:generate \
  -DarchetypeCatalog=local \
  -DarchetypeGroupId=<你的-archetypeGroupId> \
  -DarchetypeArtifactId=<你的-archetypeArtifactId> \
  -DarchetypeVersion=<你的-archetypeVersion> \
  -DgroupId=com.demo \
  -DartifactId=scan2pay-demo \
  -Dpackage=com.demo.scan2pay \
  -Dversion=0.1.0-SNAPSHOT \
  -DinteractiveMode=false

# 2) 进入生成的项目并构建
cd scan2pay-demo
mvn clean package
```

---

## 常见问题
- 找不到 Archetype：
  - 确认坐标是否正确，版本是否已安装/已发布。
  - 使用 `-DarchetypeCatalog=local` 测试本地安装是否生效。
- 交互式列出太多 Archetype：
  - 直接使用非交互式模式并显式指定坐标。
- 模板变量替换与文件名：
  - Archetype 会根据 `artifactId/groupId/package` 等自动替换模板中的变量。
  - 如需新增或调整变量，请在 `src/main/resources/META-INF/maven/archetype-metadata.xml` 中配置。
- 模板更新后不生效：
  - 重新执行 `mvn clean install` 更新本地仓库。
  - 若是远程使用，重新 `mvn deploy` 发布新版本，并更新使用时的 `archetypeVersion`。

---

## 维护建议
- 使用语义化版本：`MAJOR.MINOR.PATCH`（破坏性变更提升 MAJOR）。
- 为模板生成的项目添加最小示例与 CI 验证，确保可编译、可运行。
- 在 `CHANGELOG.md` 中记录每次模板的变更点与迁移说明。

---

## 快速参考（命令汇总）
- 本地安装：
  - `mvn -DskipTests clean install`
- 本地生成（非交互式）：
  - `mvn archetype:generate -DarchetypeCatalog=local -DarchetypeGroupId=... -DarchetypeArtifactId=... -DarchetypeVersion=... -DgroupId=... -DartifactId=... -Dpackage=... -Dversion=... -DinteractiveMode=false`
- 发布到远程：
  - `mvn -DskipTests clean deploy`
- 远程使用（非交互式）：
  - `mvn archetype:generate -DarchetypeGroupId=... -DarchetypeArtifactId=... -DarchetypeVersion=... -DgroupId=... -DartifactId=... -Dpackage=... -Dversion=... -DinteractiveMode=false`

如需进一步定制或增加模板内容，请在 `src/main/resources/archetype-resources/` 中维护模板文件，并在 `archetype-metadata.xml` 中声明它们的生成规则。