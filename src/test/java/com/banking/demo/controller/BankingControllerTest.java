package com.banking.demo.controller;

import com.banking.demo.dto.AccountDto;
import com.banking.demo.dto.DepositRequestDto;
import com.banking.demo.dto.WithdrawDto;
import com.banking.demo.service.BankingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(BankingController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class BankingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BankingService bankingService;

    private static final String BANKING_URL = "/banking";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void depositAmountWhenSuccess() throws Exception {
        //GIVEN
        DepositRequestDto depositRequestDto = DepositRequestDto.builder()
                .amount(BigDecimal.valueOf(10))
                .userId("mockmvc@test.com")
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(depositRequestDto);

        //WHEN
        AccountDto accountDto = AccountDto.builder()
                .balance(10.0)
                .build();
        doReturn(accountDto).when(bankingService).depositAmount(any());

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(BANKING_URL+"/deposit")
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("10.0"))
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNull();
        verify(bankingService).depositAmount(refEq(depositRequestDto));
    }

    @Test
    void depositAmountNegativeWhenFailed() throws Exception {
        //GIVEN
        DepositRequestDto depositRequestDto = DepositRequestDto.builder()
                .amount(new BigDecimal(-10))
                .userId("mockmvc@test.com")
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(depositRequestDto);

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(BANKING_URL+"/deposit")
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("amount: must be greater than 0"))
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNotNull();
    }

    @Test
    void depositUserIdMissingWhenFailed() throws Exception {
        //GIVEN
        DepositRequestDto depositRequestDto = DepositRequestDto.builder()
                .amount(new BigDecimal(10))
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(depositRequestDto);

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(BANKING_URL+"/deposit")
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("userId: must not be blank"))
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNotNull();
    }

    @Test
    void withdrawAmountWhenSuccess() throws Exception {
        //GIVEN
        WithdrawDto withdrawDto = WithdrawDto.builder()
                .withdrawAmount(10)
                .userId("mockmvc@test.com")
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(withdrawDto);

        //WHEN
        AccountDto accountDto = AccountDto.builder()
                .balance(10.0)
                .build();
        doReturn(accountDto).when(bankingService).withdrawAmount(any());

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(BANKING_URL+"/withdraw")
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("10.0"))
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNull();
        verify(bankingService).withdrawAmount(refEq(withdrawDto));
    }

    @Test
    void withdrawAmountNegativeWhenFailed() throws Exception {
        //GIVEN
        WithdrawDto withdrawDto = WithdrawDto.builder()
                .withdrawAmount(-10)
                .userId("mockmvc@test.com")
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(withdrawDto);

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(BANKING_URL+"/withdraw")
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNotNull();
    }

    @Test
    void withdrawUserIdMissingWhenFailed() throws Exception {
        //GIVEN
        WithdrawDto withdrawDto = WithdrawDto.builder()
                .withdrawAmount(10)
                .build();
        String requestJson = OBJECT_MAPPER.writeValueAsString(withdrawDto);

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(post(BANKING_URL+"/withdraw")
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("userId: must not be blank"))
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNotNull();
    }

    @Test
    void checkBalanceWhenSuccess() throws Exception {
        String userID ="mvc@gmail.com";

        //WHEN
        double accountDto = AccountDto.builder()
                .balance(10.0)
                .build().getBalance();
        doReturn(accountDto).when(bankingService).getBalance(any());

        //EXECUTE
        MvcResult mvcResult = mockMvc.perform(get(BANKING_URL+"/balance/"+ userID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("10.0"))
                .andDo(print())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isNull();
    }
}