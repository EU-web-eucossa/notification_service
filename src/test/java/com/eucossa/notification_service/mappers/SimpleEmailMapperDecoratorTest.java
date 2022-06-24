package com.eucossa.notification_service.mappers;

import com.eucossa.notification_service.dtos.SimpleEmailDto;
import com.eucossa.notification_service.entities.SimpleEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SimpleEmailMapperDecoratorTest {

    @Mock
    private SimpleEmailMapper simpleEmailMapper;

    @InjectMocks
    private SimpleEmailMapperDecorator simpleEmailMapperDecorator;
    private SimpleEmailDto simpleEmailDto;
    private SimpleEmail simpleEmailEntity;
    private SimpleEmail partialSimpleEmailEntity;
    private SimpleEmailDto partialSimpleEmailDto;

    @BeforeEach
    void setUp() {
        String[] to = {"abc1@gmail.com", "abc2@gmail.com"};
        String[] cc = {"abc3@gmail.com", "abc4@gmail.com"};
        String[] bcc = {"abc5@gmail.com", "abc6@gmail.com"};
        String testSubject = "Test Subject";
        String sampleMessage = "Sample Message";
        BigDecimal id = BigDecimal.valueOf(2);


        partialSimpleEmailDto = SimpleEmailDto.builder()
                .id(id)
                .emailTo(null)
                .bcc(null)
                .cc(null)
                .message(sampleMessage)
                .subject(testSubject)
                .build();
        simpleEmailDto = SimpleEmailDto.builder()
                .id(id)
                .emailTo(to)
                .bcc(bcc)
                .cc(cc)
                .message(sampleMessage)
                .subject(testSubject)
                .build();


        partialSimpleEmailEntity = SimpleEmail.builder()
                .id(id)
                .emailTo(null)
                .bcc(null)
                .cc(null)
                .subject(testSubject)
                .message(sampleMessage)
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
    void toEntity() {
        given(simpleEmailMapper.toEntity(any(SimpleEmailDto.class))).willReturn(partialSimpleEmailEntity);

        SimpleEmail actualResponse = simpleEmailMapperDecorator.toEntity(simpleEmailDto);

        assertThat(actualResponse.toString()).isEqualTo(simpleEmailEntity.toString());
    }

    @Test
    void toDto() {
        given(simpleEmailMapper.toDto(any(SimpleEmail.class))).willReturn(partialSimpleEmailDto);

        SimpleEmailDto actualResponse = simpleEmailMapperDecorator.toDto(simpleEmailEntity);

        assertThat(actualResponse).isEqualTo(simpleEmailDto);
    }

    @Test
    void toDto_skipsNullDependencies() {
        given(simpleEmailMapper.toDto(any())).willReturn(partialSimpleEmailDto);

        SimpleEmailDto actualResponse = simpleEmailMapperDecorator.toDto(partialSimpleEmailEntity);

        assertThat(actualResponse).isEqualTo(partialSimpleEmailDto);
    }

    @Test
    void toEntity_skipsNullDependencies() {
        given(simpleEmailMapper.toEntity(any(SimpleEmailDto.class))).willReturn(partialSimpleEmailEntity);

        SimpleEmail actualResponse = simpleEmailMapperDecorator.toEntity(partialSimpleEmailDto);

        assertThat(actualResponse.toString()).isEqualTo(partialSimpleEmailEntity.toString());
    }
}