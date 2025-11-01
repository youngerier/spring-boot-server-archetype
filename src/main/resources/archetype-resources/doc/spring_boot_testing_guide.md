# Spring Boot 测试体系详解

## 一、 引言

在现代企业级应用开发中，自动化测试是保障代码质量、提高开发效率和确保系统稳定性的基石。Spring Boot 在
`spring-boot-starter-test` 测试依赖包中，为我们提供了一套功能强大、易于集成的测试框架。它不仅整合了 JUnit 5、AssertJ、Mockito
等业界主流的测试库，还通过“测试切片”注解（Test Slices Annotations）极大地简化了不同层级的测试配置。

本文档旨在详细梳理 Spring Boot 的核心测试组件，阐述其工作原理和最佳适用场景，帮助开发者构建一个高效、分层、清晰的测试体系。

---

## 二、 核心测试注解与类

Spring Boot 的测试核心在于一系列的注解，它们能够按需加载 Spring 应用上下文（ApplicationContext）的一部分或全部，从而实现精准、快速的测试。

### 1. `@SpringBootTest` - 全应用上下文测试

这是 Spring Boot 中功能最强大、也最“重”的测试注解。它会启动一个完整的 Spring 应用程序上下文，几乎等同于在生产环境中运行您的应用。

- **核心解释**：
  `@SpringBootTest` 会扫描主配置类（`@SpringBootApplication` 标记的类），加载所有通过组件扫描（`@Component`, `@Service`,
  `@Repository`, `@Controller` 等）发现的 Bean，并执行所有自动配置。这为我们提供了一个与真实运行环境高度一致的集成测试环境。

- **关键属性**：
    - `webEnvironment`：控制 Web 环境的启动方式，至关重要。
        - `WebEnvironment.MOCK` (默认值): 加载一个 Web 应用上下文，但提供一个模拟的 Servlet 环境。不会启动真实的
          Tomcat/Jetty 等服务器。通常配合 `MockMvc` 使用。
        - `WebEnvironment.RANDOM_PORT`: 加载一个真实的 Web 服务器（如 Tomcat），并监听一个随机的可用端口。适合进行端到端的
          HTTP 接口测试。
        - `WebEnvironment.DEFINED_PORT`: 加载一个真实的 Web 服务器，并监听在 `application.properties` 或
          `application.yml` 中定义的端口（如 `server.port=8080`）。
        - `WebEnvironment.NONE`: 不加载任何 Web 应用上下文，适合测试非 Web 的后端服务。

"""- **适用场景与示例**：

1. **端到端测试（End-to-End Testing）**：测试一个完整的“用户注册”流程。
   ```java
   // --- Imports ---
   import com.your.project.user.User;
   import com.your.project.user.UserDto;
   import com.your.project.user.UserRepository;
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.boot.test.web.client.TestRestTemplate;
   import org.springframework.http.HttpStatus;
   import org.springframework.http.ResponseEntity;
   import java.util.Optional;
   import static org.assertj.core.api.Assertions.assertThat;

   // --- Test Class ---
   @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
   class UserRegistrationIntegrationTest {

       @Autowired
       private TestRestTemplate restTemplate;

       @Autowired
       private UserRepository userRepository;

       @Test
       void testRegisterUser_Success() {
           // 准备请求体
           UserDto newUser = new UserDto("testuser", "password123");

           // 发起真实的 HTTP POST 请求
           ResponseEntity<String> response = restTemplate.postForEntity("/api/users/register", newUser, String.class);

           // 验证 HTTP 响应
           assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

           // 验证数据库状态
           Optional<User> savedUser = userRepository.findByUsername("testuser");
           assertThat(savedUser).isPresent();
           assertThat(savedUser.get().getUsername()).isEqualTo("testuser");
       }
   }
   ```
2. **全应用集成测试**：验证应用的自定义配置（如 `@ConfigurationProperties`）是否被正确加载和使用。
   ```java
   // --- Imports ---
   import com.your.project.config.MyAppProperties;
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import java.time.Duration;
   import static org.assertj.core.api.Assertions.assertThat;

   // --- Test Class ---
   @SpringBootTest
   class AppPropertiesIntegrationTest {

       @Autowired
       private MyAppProperties appProperties;

       @Test
       void testPropertiesAreLoaded() {
           // 验证 application.yml 或 application.properties 中的自定义配置是否正确注入
           assertThat(appProperties.getApiUrl()).isEqualTo("https://api.example.com");
           assertThat(appProperties.getTimeout()).isEqualTo(Duration.ofSeconds(10));
       }
   }
   ```
3. **带有真实 Web 服务器的 API 测试**：与第一点类似，使用 `TestRestTemplate` 对外暴露的 API 进行黑盒测试。""

- **辅助工具**：
    - `TestRestTemplate`: 在 `webEnvironment` 为 `RANDOM_PORT` 或 `DEFINED_PORT` 时，Spring Boot 会自动配置一个
      `TestRestTemplate` Bean，它可以方便地向测试服务器发起 HTTP 请求，并处理了 URL 的动态端口问题。

### 2. `@WebMvcTest` - Web 层切片测试

`@WebMvcTest` 是一个“测试切片”注解，它专注于测试 Spring MVC 的组件，即 Web 层。

- **核心解释**：
  它只会加载与 Spring MVC 相关的配置和 Bean，包括：
    - `@Controller`, `@ControllerAdvice`, `@JsonComponent`, `Converter`, `GenericConverter`, `Filter`,
      `WebMvcConfigurer` 和 `HandlerMethodArgumentResolver`。
    - **不会加载** `@Component`, `@Service`, `@Repository` 等业务层和数据层的 Bean。

- **适用场景与示例**:
    1. **Controller 单元测试/组件测试**：测试 `UserController` 的 `GET /api/users/{id}` 端点。
       ```java
       // --- Imports ---
       import com.your.project.user.User;
       import com.your.project.user.UserDto;
       import com.your.project.user.UserService;
       import com.your.project.user.UserController;
       import org.junit.jupiter.api.Test;
       import org.springframework.beans.factory.annotation.Autowired;
       import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
       import org.springframework.boot.test.mock.mockito.MockBean;
       import org.springframework.http.MediaType;
       import org.springframework.test.web.servlet.MockMvc;
       import java.util.Optional;
       import static org.mockito.BDDMockito.given;
       import static org.mockito.ArgumentMatchers.anyLong;
       import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
       import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
       // --- Test Class ---
       // 只测试 UserController，相关的 UserService 将被模拟
       @WebMvcTest(UserController.class)
       class UserControllerTest {
 
           @Autowired
           private MockMvc mockMvc;
 
           // 创建一个 Service 层的 Mock 对象，并注入到 Spring 上下文中
           @MockBean
           private UserService userService;
 
           @Test
           void testGetUserById_WhenUserExists_ReturnsUserDto() throws Exception {
               // 1. 准备模拟数据和行为
               User user = new User(1L, "testuser");
               UserDto userDto = new UserDto(1L, "testuser");
               // 当调用 userService.findById(1L) 时，返回我们准备好的 user 对象
               given(userService.findById(1L)).willReturn(Optional.of(user));
 
               // 2. 执行 MockMvc 请求
               mockMvc.perform(get("/api/users/{id}", 1L)
                       .accept(MediaType.APPLICATION_JSON))
                       // 3. 验证响应
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                       .andExpect(jsonPath("$.id").value(1))
                       .andExpect(jsonPath("$.username").value("testuser"));
           }
 
           @Test
           void testGetUserById_WhenUserNotFound_Returns404() throws Exception {
               // 模拟 Service 层返回空，表示用户不存在
               given(userService.findById(anyLong())).willReturn(Optional.empty());
 
               mockMvc.perform(get("/api/users/{id}", 99L))
                       .andExpect(status().isNotFound());
           }
       }
       ```
    2. **API 接口契约测试**：验证请求体验证规则（`@Valid`）是否生效。
       ```java
       // --- Imports ---
       import com.your.project.user.UserController;
       import org.junit.jupiter.api.Test;
       import org.springframework.beans.factory.annotation.Autowired;
       import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
       import org.springframework.boot.test.mock.mockito.MockBean;
       import org.springframework.http.MediaType;
       import org.springframework.test.web.servlet.MockMvc;
       import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
       import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
       // --- Test Class ---
       @WebMvcTest(UserController.class)
       class UserControllerValidationTest {
           // ... (MockMvc, MockBean 设置同上)
 
           @Test
           void testCreateUser_WithInvalidData_ReturnsBadRequest() throws Exception {
               // 准备一个无效的请求体（例如，username 为空）
               String invalidUserJson = "{\"username\": \"\", \"password\": \"123\"}";
 
               mockMvc.perform(post("/api/users")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(invalidUserJson))
                       .andExpect(status().isBadRequest()); // 预期得到 400 错误
           }
       }
       ```

- **关键点**：
  由于 `@WebMvcTest` 不会加载业务逻辑 Bean（如 `UserService`），因此在 Controller 中注入的 Service 必须使用 `@MockBean`
  进行模拟（Mock）。

- **重要提示：解决 `Unable to find a @SpringBootConfiguration` 错误**
  如果您在运行 `@WebMvcTest` 时遇到 `IllegalStateException: Unable to find a @SpringBootConfiguration`
  错误，这几乎总是因为您的测试类包结构不正确。
  **最佳实践**：请确保您的测试类位于主应用类（带有 `@SpringBootApplication` 的类）的子包中。Spring Test
  会从测试类所在的包向上搜索，直到找到主配置类。如果您的测试类与主应用类在完全不同的包路径下，搜索就会失败。

- **辅助工具**：
    - `MockMvc`: Spring Test 提供的强大工具，用于在不启动真实服务器的情况下，模拟 HTTP 请求并对 Controller 进行调用。你可以用它来发送
      GET, POST 等请求，并对响应的状态码、Header、Body 内容等进行断言。

### 3. `@DataJpaTest` - 数据持久层切片测试

`@DataJpaTest` 同样是一个“测试切片”，它专注于测试 JPA 相关的组件，即数据持久层。

- **核心解释**：
  它只会加载与 JPA 相关的配置，包括：
    - 扫描 `@Entity` 实体类。
    - 配置 Spring Data JPA 的 Repository（`@Repository` 接口）。
    - **默认配置一个嵌入式的内存数据库**（如 H2），用于测试，避免污染真实的开发数据库。
    - **默认开启事务，并在每个测试方法结束后回滚**。这保证了测试之间的独立性。

"""- **适用场景与示例**：

1. **Repository 接口测试**：验证 `UserRepository` 的自定义查询方法 `findByStatus`。
   ```java
   // --- Imports ---
   import com.your.project.user.User;
   import com.your.project.user.UserRepository;
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
   import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
   import java.util.List;
   import static org.assertj.core.api.Assertions.assertThat;

   // --- Test Class ---
   @DataJpaTest
   class UserRepositoryTest {

       @Autowired
       private TestEntityManager entityManager;

       @Autowired
       private UserRepository userRepository;

       @Test
       void testFindByStatus_ReturnsActiveUsers() {
           // 1. 准备数据
           // 使用 TestEntityManager 来持久化测试数据
           entityManager.persist(new User("active_user", User.Status.ACTIVE));
           entityManager.persist(new User("inactive_user", User.Status.INACTIVE));
           entityManager.flush(); // 将数据同步到内存数据库

           // 2. 执行被测试的方法
           List<User> activeUsers = userRepository.findByStatus(User.Status.ACTIVE);

           // 3. 验证结果
           assertThat(activeUsers).hasSize(1);
           assertThat(activeUsers.get(0).getUsername()).isEqualTo("active_user");
       }
   }
   ```
2. **JPA 实体映射测试**：验证 `@ManyToOne` 关联关系是否正确。
   ```java
   // --- Imports ---
   import com.your.project.order.Order;
   import com.your.project.user.User;
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
   import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
   import static org.assertj.core.api.Assertions.assertThat;

   // --- Test Class ---
   @DataJpaTest
   class OrderRepositoryTest {

       @Autowired
       private TestEntityManager entityManager;

       @Test
       void testUserOrderAssociation() {
           User customer = new User("customer1");
           entityManager.persist(customer);

           Order order = new Order("order_details", customer);
           entityManager.persist(order);
           entityManager.flush();
           entityManager.clear(); // 清除持久化上下文缓存，确保从数据库加载

           // 验证从数据库取出的 Order 对象是否正确关联了 User
           Order foundOrder = entityManager.find(Order.class, order.getId());
           assertThat(foundOrder.getUser()).isNotNull();
           assertThat(foundOrder.getUser().getUsername()).isEqualTo("customer1");
       }
   }
   ```
3. **数据校验与约束测试**：测试 `username` 字段的 `unique=true` 约束。
   ```java
   // --- Imports ---
   import com.your.project.user.User;
   import com.your.project.user.UserRepository;
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
   import org.springframework.dao.DataIntegrityViolationException;
   import static org.assertj.core.api.Assertions.assertThatThrownBy;

   // --- Test Class ---
   @DataJpaTest
   class UserRepositoryConstraintTest {
       // ... (EntityManager, Repository 注入同上)

       @Test
       void testSaveUser_WithDuplicateUsername_ThrowsException() {
           User user1 = new User("duplicate_user");
           entityManager.persistAndFlush(user1);

           User user2 = new User("duplicate_user");

           // 验证当尝试保存一个具有相同用户名的用户时，会抛出数据完整性异常
           assertThatThrownBy(() -> {
               userRepository.saveAndFlush(user2);
           }).isInstanceOf(DataIntegrityViolationException.class);
       }
   }
   ```"""

- **辅助工具**：
    - `TestEntityManager`: 一个专为 JPA 测试设计的工具类，它提供了一系列方便的方法来在测试中创建和管理实体（Entity），如
      `persist`, `find`, `flush` 等，非常适合在测试开始前准备数据，或在测试后验证数据状态。

### 4. `@MockBean` - 依赖模拟

`@MockBean` 虽然不是一个测试场景注解，但它是实现“切片测试”的粘合剂，至关重要。

- **核心解释**：
  该注解用于向 Spring 的 `ApplicationContext` 中添加一个由 Mockito 创建的 Mock 对象。如果上下文中已存在同类型的
  Bean，它会被这个 Mock 对象所**替换**。

"""- **适用场景与示例**：

1. **`@WebMvcTest` 中的必备工具**：请参考上面 `@WebMvcTest` 的示例，其中 `UserService` 就是通过 `@MockBean` 模拟的。

2. **`@SpringBootTest` 中的外部服务模拟**：假设你的 `OrderService` 依赖一个 `PaymentGateway`
   接口来处理支付，在测试“创建订单”这个业务流程时，你不想真的调用支付接口。
   ```java
   // --- Imports ---
   import com.your.project.order.Order;
   import com.your.project.order.OrderCreationRequest;
   import com.your.project.order.OrderService;
   import com.your.project.order.OrderStatus;
   import com.your.project.payment.PaymentGateway;
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.boot.test.mock.mockito.MockBean;
   import java.math.BigDecimal;
   import static org.assertj.core.api.Assertions.assertThat;
   import static org.mockito.ArgumentMatchers.any;
   import static org.mockito.BDDMockito.given;
   import static org.mockito.Mockito.times;
   import static org.mockito.Mockito.verify;

   // --- Test Class ---
   @SpringBootTest
   class OrderServiceIntegrationTest {

       @Autowired
       private OrderService orderService;

       // 模拟支付网关，替换掉真实环境中可能存在的 RealPaymentGateway Bean
       @MockBean
       private PaymentGateway paymentGateway;

       @Test
       void testCreateOrder_ShouldSucceedAndNotCallRealPayment() {
           // 模拟支付行为：当调用 processPayment 时，总是返回 true
           given(paymentGateway.processPayment(any(BigDecimal.class))).willReturn(true);

           // 执行创建订单的业务逻辑
           OrderCreationRequest request = new OrderCreationRequest(...);
           Order order = orderService.createOrder(request);

           // 验证订单已创建
           assertThat(order.getStatus()).isEqualTo(OrderStatus.PAID);

           // 验证 paymentGateway 的 processPayment 方法被精确调用了一次
           verify(paymentGateway, times(1)).processPayment(any(BigDecimal.class));
       }
   }
   ```
3. **在任何 Spring 测试中替换特定 Bean**：这个场景与第二点类似，核心思想是在一个需要加载大量真实 Bean
   的集成测试环境中，精确地“拔掉”一个或几个组件，用可控的 Mock 来代替，以增强测试的稳定性、速度和独立性。"""

---

## 三、 总结与选择指南

为了帮助您快速选择合适的测试注解，下表总结了它们的核心区别：

| 注解                    | 主要目的                | 加载内容              | 辅助工具                   | 适用场景                    |
|:----------------------|:--------------------|:------------------|:-----------------------|:------------------------|
| **`@SpringBootTest`** | 全应用集成测试             | 整个 Spring 应用上下文   | `TestRestTemplate`     | 端到端流程测试、模块间集成测试         |
| **`@WebMvcTest`**     | Web 层（Controller）测试 | 仅 Spring MVC 相关组件 | `MockMvc`, `@MockBean` | Controller 逻辑、API 契约测试  |
| **`@DataJpaTest`**    | 数据持久层（Repository）测试 | 仅 JPA 相关组件        | `TestEntityManager`    | Repository 自定义查询、实体映射测试 |

### 实践建议

遵循“测试金字塔”原则：

1. **编写大量的 `@DataJpaTest` 和纯粹的单元测试**：这些测试速度快，反馈迅速，是构建稳固基础的保障。
2. **编写适量的 `@WebMvcTest`**：确保你的 API 接口行为符合预期，覆盖核心的 Web 交互逻辑。
3. **谨慎编写少量的 `@SpringBootTest`**：这些测试运行慢，但价值极高。用它们来覆盖最关键的、跨越多个系统层级的核心业务流程。

通过合理地组合使用这些测试注解，你可以为你的 Spring Boot 应用构建一个既全面又高效的自动化测试安全网。
