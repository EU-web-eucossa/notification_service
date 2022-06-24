package com.eucossa.notification_service.services.implementations;

import com.eucossa.notification_service.dtos.SimpleEmailDto;
import com.eucossa.notification_service.entities.SimpleEmail;
import com.eucossa.notification_service.mappers.SimpleEmailMapper;
import com.eucossa.notification_service.repositories.SimpleEmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceImplTest {

    @Mock
    private SimpleEmailRepository simpleEmailRepository;
    @Mock
    private SimpleEmailMapper simpleEmailMapper;
    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailSenderServiceImpl emailSenderService;
    private SimpleEmailDto newSimpleEmailDto;
    private SimpleEmailDto expectedResponse;
    private SimpleEmail simpleEmailEntity;

    @BeforeEach
    void setUp() {
        String[] to = {"abc1@gmail.com", "abc2@gmail.com"};
        String[] cc = {"abc3@gmail.com", "abc4@gmail.com"};
        String[] bcc = {"abc5@gmail.com", "abc6@gmail.com"};
        String testSubject = "Test Subject";
        String sampleMessage = "Sample Message";
        BigDecimal id = BigDecimal.valueOf(2);

        newSimpleEmailDto = SimpleEmailDto.builder()
                .id(null)
                .emailTo(to)
                .bcc(bcc)
                .cc(cc)
                .message(sampleMessage)
                .subject(testSubject)
                .build();

        expectedResponse = SimpleEmailDto.builder()
                .id(id)
                .emailTo(to)
                .bcc(bcc)
                .cc(cc)
                .message(sampleMessage)
                .subject(testSubject)
                .build();

        simpleEmailEntity = SimpleEmail.builder()
                .id(id)
                .emailTo(to[0] + "," + to[1])
                .bcc(bcc[0] + "," + bcc[1])
                .cc(cc[0] + "," + cc[1])
                .subject(testSubject)
                .message(sampleMessage)
                .build();
    }

    @Test
    void sendSimpleEmail() {
        given(simpleEmailMapper.toEntity(any(SimpleEmailDto.class))).willReturn(simpleEmailEntity);
        given(simpleEmailRepository.save(any())).willReturn(simpleEmailEntity);
        given(simpleEmailMapper.toDto(any(SimpleEmail.class))).willReturn(expectedResponse);

        SimpleEmailDto actualResponse = emailSenderService.sendSimpleEmail(newSimpleEmailDto);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}