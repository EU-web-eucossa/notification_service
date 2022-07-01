package com.eucossa.notification_service.mappers;

import com.eucossa.notification_service.dtos.EmailWithAttachmentDto;
import com.eucossa.notification_service.dtos.EmailWithAttachmentResponse;
import com.eucossa.notification_service.entities.EmailAttachment;
import com.eucossa.notification_service.entities.EmailWithAttachment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class EmailWithAttachmentMapperDecoratorTest {

    private final LocalDateTime time = LocalDateTime.now();
    @Mock
    private EmailWithAttachmentMapper emailWithAttachmentMapper;
    @InjectMocks
    private EmailWithAttachmentMapperDecorator emailWithAttachmentMapperDecorator;
    private EmailWithAttachmentDto emailWithAttachmentDto;
    private EmailWithAttachment emailWithAttachmentEntity;
    private final List<EmailAttachment> emailAttachmentObjects = new ArrayList<>();
    private EmailWithAttachment partialEmailWithAttachmentEntity;
    private EmailWithAttachmentResponse emailWithAttachmentResponse;
    private final List<String> attachmentFileNames = new ArrayList<>();
    private final List<MultipartFile> multipartAttachments = new ArrayList<>();
    private EmailWithAttachmentResponse partialEmailWithAttachmentResponse;

    private final List<?> list = new ArrayList<>();
    private EmailWithAttachmentDto partialEmailWithAttachmentDto;

    @BeforeEach
    void setUp() {
        String[] to = {"abc1@gmail.com", "abc2@gmail.com"};
        String[] cc = {"abc3@gmail.com", "abc4@gmail.com"};
        String[] bcc = {"abc5@gmail.com", "abc6@gmail.com"};
        String testSubject = "Test Subject";
        String sampleMessage = "Sample Message";
        BigDecimal id = BigDecimal.valueOf(2);

        String fileName1 = "fileName1.pdf";
        String content1 = "content1";
        String fileName2 = "fileName2.pdf";
        String content2 = "content2";

        EmailAttachment emailAttachment1 = EmailAttachment.builder().fileName(fileName1).content(content1.getBytes()).build();
        EmailAttachment emailAttachment2 = EmailAttachment.builder().fileName(fileName2).content(content2.getBytes()).build();

        MultipartFile multipartAttachmentFile1 = new MockMultipartFile(fileName1, fileName1, null, content1.getBytes());
        MultipartFile multipartAttachmentFile2 = new MockMultipartFile(fileName2, fileName2, null, content2.getBytes());

        attachmentFileNames.addAll(List.of(emailAttachment1.getOriginalFileName(), emailAttachment2.getOriginalFileName()));
        emailAttachmentObjects.addAll(List.of(emailAttachment1, emailAttachment2));
        multipartAttachments.addAll(List.of(multipartAttachmentFile1, multipartAttachmentFile2));


        partialEmailWithAttachmentDto = EmailWithAttachmentDto.builder()
                .id(id)
                .cc(null)
                .bcc(null)
                .emailTo(to)
                .subject(testSubject)
                .message(sampleMessage)
                .attachments((List<MultipartFile>) list)
                .version(2)
                .build();
        emailWithAttachmentDto = EmailWithAttachmentDto.builder()
                .id(id)
                .cc(cc)
                .bcc(bcc)
                .emailTo(to)
                .subject(testSubject)
                .message(sampleMessage)
                .attachments(multipartAttachments)
                .version(2)
                .build();

        partialEmailWithAttachmentResponse = EmailWithAttachmentResponse.builder()
                .id(id)
                .cc(null)
                .bcc(null)
                .emailTo(to)
                .message(sampleMessage)
                .attachments((List<String>) list)
                .subject(testSubject)
                .version(emailWithAttachmentDto.getVersion())
                .build();
        emailWithAttachmentResponse = EmailWithAttachmentResponse.builder()
                .id(id)
                .cc(cc)
                .bcc(bcc)
                .emailTo(to)
                .message(sampleMessage)
                .attachments(attachmentFileNames)
                .subject(testSubject)
                .version(emailWithAttachmentDto.getVersion())
                .build();

        partialEmailWithAttachmentEntity = EmailWithAttachment.builder()
                .id(id)
                .cc(null)
                .bcc(null)
                .emailTo(null)
                .attachments((List<EmailAttachment>) list)
                .dateSent(Timestamp.valueOf(time))
                .dateUpdated(Timestamp.valueOf(time))
                .message(sampleMessage)
                .subject(testSubject)
                .version(2)
                .build();
        emailWithAttachmentEntity = EmailWithAttachment.builder()
                .id(id)
                .attachments(emailAttachmentObjects)
                .emailTo(to[0] + "," + to[1])
                .bcc(bcc[0] + "," + bcc[1])
                .cc(cc[0] + "," + cc[1])
                .dateSent(Timestamp.valueOf(time))
                .dateUpdated(Timestamp.valueOf(time))
                .message(sampleMessage)
                .subject(testSubject)
                .version(2)
                .build();
    }

    @Test
    void toEntity() {
        given(emailWithAttachmentMapper.toEntity(any(EmailWithAttachmentDto.class))).willReturn(partialEmailWithAttachmentEntity);

        EmailWithAttachment actualResponse = emailWithAttachmentMapperDecorator.toEntity(emailWithAttachmentDto);

        assertThat(actualResponse.toString()).isEqualTo(emailWithAttachmentEntity.toString());
    }

    @Test
    void toDto() {
        given(emailWithAttachmentMapper.toDto(any(EmailWithAttachment.class))).willReturn(partialEmailWithAttachmentResponse);

        EmailWithAttachmentResponse actualEmailWithAttachmentResponse = emailWithAttachmentMapperDecorator.toDto(emailWithAttachmentEntity);

        assertThat(actualEmailWithAttachmentResponse).isEqualTo(emailWithAttachmentResponse);
    }

    @Test
    void toDto_skipsNullDependencies() {
        given(emailWithAttachmentMapper.toDto(any(EmailWithAttachment.class))).willReturn(partialEmailWithAttachmentResponse);

        EmailWithAttachmentResponse actualResponse = emailWithAttachmentMapperDecorator.toDto(partialEmailWithAttachmentEntity);

        assertThat(actualResponse).isEqualTo(partialEmailWithAttachmentResponse);
    }

    @Test
    void toEntity_skipsNullDependencies() {
        given(emailWithAttachmentMapper.toEntity(any(EmailWithAttachmentDto.class))).willReturn(partialEmailWithAttachmentEntity);

        EmailWithAttachment actualResponse = emailWithAttachmentMapperDecorator.toEntity(partialEmailWithAttachmentDto);

        assertThat(actualResponse.toString()).isEqualTo(partialEmailWithAttachmentEntity.toString());
    }
}