package com.banking.demo.controller;

import com.banking.demo.dto.CreateUserDto;
import com.banking.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    private static final String CREATE_USER_URL = "/user/create";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void createUserWhenSuccess() throws Exception {
        //GIVEN
        CreateUserDto createUserDto = CreateUserDto.builder()
                .firstName("Mock")
                .lastName("Mvc")
                .userId("mockmvc@test.com")
                .currency("INR")
                .mobileNo("960303300")
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(createUserDto);

        //WHEN
        doNothing().when(userService).createUser(any());

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(CREATE_USER_URL)
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNull();
        verify(userService).createUser(refEq(createUserDto));
    }

    @Test
    void createUserIdWhenFailed() throws Exception {
        //GIVEN
        CreateUserDto createUserDto = CreateUserDto.builder()
                .firstName("Mock")
                .lastName("Mvc")
                .currency("INR")
                .mobileNo("960303300")
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(createUserDto);

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(CREATE_USER_URL)
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("userId: UserId is mandatory to pass"))
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNotNull();
    }

    @Test
    void userFirstNameMissingWhenFailed() throws Exception {
        //GIVEN
        CreateUserDto createUserDto = CreateUserDto.builder()
                .lastName("Mvc")
                .userId("Mandy.kaur@gmail.com")
                .currency("INR")
                .mobileNo("960303300")
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(createUserDto);

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(CREATE_USER_URL)
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("firstName: first name is mandatory to pass"))
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNotNull();
    }

    @Test
    void userFirstNameNullWhenFailed() throws Exception {
        //GIVEN
        CreateUserDto createUserDto = CreateUserDto.builder()
                .firstName("")
                .lastName("MVC")
                .userId("Mandy.kaur@gmail.com")
                .currency("INR")
                .mobileNo("960303300")
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(createUserDto);

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(CREATE_USER_URL)
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("firstName: first name is mandatory to pass"))
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNotNull();
    }

    @Test
    void userCurrencyMissingWhenFailed() throws Exception {
        //GIVEN
        CreateUserDto createUserDto = CreateUserDto.builder()
                .firstName("Mvc1")
                .lastName("Mvc")
                .userId("Mandy.kaur@gmail.com")
                .mobileNo("960303300")
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(createUserDto);

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(CREATE_USER_URL)
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("currency: currency is mandatory to pass"))
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNotNull();
    }
}


