package com.eucossa.notification_service.services.implementations;

import com.eucossa.notification_service.dtos.EmailWithAttachmentDto;
import com.eucossa.notification_service.dtos.EmailWithAttachmentResponse;
import com.eucossa.notification_service.dtos.SimpleEmailDto;
import com.eucossa.notification_service.entities.EmailAttachment;
import com.eucossa.notification_service.entities.EmailWithAttachment;
import com.eucossa.notification_service.entities.SimpleEmail;
import com.eucossa.notification_service.enums.YesOrNo;
import com.eucossa.notification_service.mappers.EmailWithAttachmentMapper;
import com.eucossa.notification_service.mappers.SimpleEmailMapper;
import com.eucossa.notification_service.repositories.EmailWithAttachmentRepository;
import com.eucossa.notification_service.repositories.SimpleEmailRepository;
import org.apache.commons.configuration.AbstractFileConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceImplTest {

    @Mock
    private SimpleEmailRepository simpleEmailRepository;
    @Mock
    private EmailWithAttachmentMapper emailWithAttachmentMapper;
    @Mock
    private SimpleEmailMapper simpleEmailMapper;
    @Mock
    private EmailWithAttachmentRepository emailWithAttachmentRepository;
    @Mock
    private JavaMailSenderImpl javaMailSender;

    @InjectMocks
    private EmailSenderServiceImpl emailSenderService;
    private SimpleEmailDto newSimpleEmailDto;
    private SimpleEmailDto expectedResponse;
    private SimpleEmail simpleEmailEntity;
    private EmailWithAttachmentDto emailWithAttachmentDto;
    private EmailWithAttachmentResponse emailWithAttachmentResponse;
    private final List<MultipartFile> multipartAttachments = new ArrayList<>();
    private final List<String> attachmentFileNames = new ArrayList<>();
    private EmailWithAttachment emailWithAttachmentEntity;
    private final LocalDateTime time = LocalDateTime.now();
    private List<EmailAttachment> emailAttachments;

    @BeforeEach
    void setUp() {
        String[] to = {"abc1@gmail.com", "abc2@gmail.com"};
        String[] cc = {"abc3@gmail.com", "abc4@gmail.com"};
        String[] bcc = {"abc5@gmail.com", "abc6@gmail.com"};
        String testSubject = "Test Subject";
        String sampleMessage = "Sample Message";
        BigDecimal id = BigDecimal.valueOf(2);

        String fileName1 = "file1.pdf";
        String fileName2 = "file2.pdf";
        String content1 = "content1";
        String content2 = "content2";

        MultipartFile attachment1 = new MockMultipartFile(fileName1, fileName1, null, content1.getBytes());
        MultipartFile attachment2 = new MockMultipartFile(fileName2, fileName2, null, content2.getBytes());

        EmailAttachment emailAttachment1 = EmailAttachment.builder().originalFileName(fileName1).contentInBytes(content1.getBytes()).build();
        EmailAttachment emailAttachment2 = EmailAttachment.builder().originalFileName(fileName2).contentInBytes(content2.getBytes()).build();

        multipartAttachments.addAll(List.of(attachment1, attachment2));
        attachmentFileNames.addAll(List.of(fileName1, fileName2));
        emailAttachments = new ArrayList<>(List.of(emailAttachment1, emailAttachment2));

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

        // email with attachment
        emailWithAttachmentDto = EmailWithAttachmentDto.builder()
                .id(null)
                .cc(cc)
                .bcc(bcc)
                .emailTo(to)
                .message(sampleMessage)
                .attachments(multipartAttachments)
                .subject(testSubject)
                .build();

        emailWithAttachmentEntity = EmailWithAttachment.builder()
                .id(id)
                .emailTo(to[0] + "," + to[1])
                .bcc(bcc[0] + "," + bcc[1])
                .cc(cc[0] + "," + cc[1])
                .dateUpdated(Timestamp.valueOf(time))
                .dateSent(Timestamp.valueOf(time))
                .version(2)
                .subject(testSubject)
                .message(sampleMessage)
                .attachments(emailAttachments)
                .build();

        emailWithAttachmentResponse = EmailWithAttachmentResponse.builder()
                .id(id)
                .emailTo(to)
                .cc(cc)
                .bcc(bcc)
                .subject(testSubject)
                .message(sampleMessage)
                .attachments(attachmentFileNames)
                .version(2)
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
    @Test
    void sendEmailWithAttachments() {
        given(javaMailSender.createMimeMessage()).willCallRealMethod();
        given(emailWithAttachmentMapper.toEntity(any())).willReturn(emailWithAttachmentEntity);
        given(emailWithAttachmentRepository.save(any())).willReturn(emailWithAttachmentEntity);
        given(emailWithAttachmentMapper.toDto(emailWithAttachmentEntity)).willReturn(emailWithAttachmentResponse);

        EmailWithAttachmentResponse actualResponse = emailSenderService.sendEmailWithAttachments(emailWithAttachmentDto);

        assertThat(actualResponse).isEqualTo(emailWithAttachmentResponse);
    }
}