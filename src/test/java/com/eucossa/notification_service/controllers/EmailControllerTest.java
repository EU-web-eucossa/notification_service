package com.eucossa.notification_service.controllers;

import com.eucossa.notification_service.dtos.SimpleEmailDto;
import com.eucossa.notification_service.services.interfaces.EmailSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmailController.class)
class EmailControllerTest {

    private static ObjectMapper objectMapper;
    private final StringBuilder baseUrl = new StringBuilder("/email");
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmailSenderService emailSenderService;
    private SimpleEmailDto requestBody;
    private SimpleEmailDto responseBody;

    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void setUp() {
        String[] to = {"abc1@gmail.com", "abc2@gmail.com"};
        String[] cc = {"abc3@gmail.com", "abc4@gmail.com"};
        String[] bcc = {"abc5@gmail.com", "abc6@gmail.com"};
        String testSubject = "Test Subject";
        String sampleMessage = "Sample Message";
        BigDecimal id = BigDecimal.valueOf(2);


        requestBody = SimpleEmailDto.builder()
                .id(null)
                .emailTo(to)
                .bcc(bcc)
                .cc(cc)
                .message(sampleMessage)
                .subject(testSubject)
                .build();
        responseBody = SimpleEmailDto.builder()
                .id(id)
                .emailTo(to)
                .bcc(bcc)
                .cc(cc)
                .message(sampleMessage)
                .subject(testSubject)
                .build();

    }

    @Test
    void sendEmail() throws Exception {
        baseUrl.append("/send/simple-email");
        given(emailSenderService.sendSimpleEmail(any(SimpleEmailDto.class))).willReturn(responseBody);

        String actualResponse = mockMvc.perform(post(baseUrl.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        assertThat(actualResponse).isEqualTo(objectMapper.writeValueAsString(responseBody));
    }
}