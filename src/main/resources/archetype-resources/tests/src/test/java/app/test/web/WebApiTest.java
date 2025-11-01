#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.test.web;

import com.alibaba.fastjson2.JSON;
import ${package}.admin.api.user.UserController;
import ${package}.api.user.model.dto.UserDTO;
import ${package}.api.user.model.request.ValidRequest;
import ${package}.api.user.service.UserService;
import ${package}.infra.business.properties.BusinessProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Import({
        BusinessProperties.class
})
@WebMvcTest(value = UserController.class)
// @ContextConfiguration(classes = PingAdminApplication.class) // Use default Spring Boot test configuration
@AutoConfigureMockMvc(addFilters = true) // 确保包含 Spring Security 过滤器
public class WebApiTest {
    @Autowired
    private MockMvc mockMvc;
    // 创建一个 Service 层的 Mock 对象，并注入到 Spring 上下文中
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void testGetUser() throws Exception {
        // 1. 准备模拟数据和行为
        UserDTO userDto = new UserDTO();
        userDto.setId(1L);
        userDto.setNickname("testuser");
        // 当调用 userService.findById(1L) 时，返回我们准备好的 user 对象
//        given(userService.queryUserById(1L)).willReturn(userDto);

        // 2. 执行 MockMvc 请求
        mockMvc.perform(get("/users"))
                // 3. 验证响应
                .andExpect(status().isOk())
                .andDo(h -> {
                    log.info(h.getResponse().getContentAsString(StandardCharsets.UTF_8));
                })
        ;
    }

    @Test
    @WithMockUser
    public void testEnv() throws Exception {

        // 2. 执行 MockMvc 请求
        mockMvc.perform(get("/env"))
                // 3. 验证响应
                .andExpect(status().isOk())
                .andDo(h -> {
                    log.info(h.getResponse().getContentAsString(StandardCharsets.UTF_8));
                })
        ;
    }

    @Test
    @WithMockUser
    public void testValid() throws Exception {
        ValidRequest validRequest = new ValidRequest();
        validRequest.setId(1L);
        validRequest.setGmtCreate(LocalDateTime.now());
        // 2. 执行 MockMvc 请求
        mockMvc.perform(post("/valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(validRequest))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                // 3. 验证响应
                .andExpect(status().isOk())
                .andDo(h -> {
                    log.info(h.getResponse().getContentAsString(StandardCharsets.UTF_8));
                })
        ;
    }
}
