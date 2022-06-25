package com.eucossa.notification_service.mappers;

import com.eucossa.notification_service.dtos.EmailWithAttachmentDto;
import com.eucossa.notification_service.dtos.EmailWithAttachmentResponse;
import com.eucossa.notification_service.entities.EmailAttachment;
import com.eucossa.notification_service.entities.EmailWithAttachment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
@Slf4j
public class EmailWithAttachmentMapperDecorator implements EmailWithAttachmentMapper {
    
    @Autowired
    @Qualifier("delegate")
    private EmailWithAttachmentMapper emailWithAttachmentMapper;
    @Override
    public EmailWithAttachment toEntity(EmailWithAttachmentDto emailWithAttachmentDto) {
        EmailWithAttachment mappedEmailWithAttachment = emailWithAttachmentMapper.toEntity(emailWithAttachmentDto);
        
        // set to
        String[] to = emailWithAttachmentDto.getEmailTo();
        if (to != null && to.length > 0) {
            StringBuilder string = new StringBuilder();
            for (String toAddress :
                    to) {
                string.append(toAddress).append(",");
            }
            mappedEmailWithAttachment.setEmailTo(string.substring(0, string.length() - 1));
        }

        // set cc
        String[] cc = emailWithAttachmentDto.getCc();
        if (cc != null && cc.length > 0) {
            StringBuilder string = new StringBuilder();
            for (String toAddress :
                    cc) {
                string.append(toAddress).append(",");
            }
            mappedEmailWithAttachment.setCc(string.substring(0, string.length() - 1));
        }

        // set bcc
        String[] bcc = emailWithAttachmentDto.getBcc();
        if (bcc != null && bcc.length > 0) {
            StringBuilder string = new StringBuilder();
            for (String toAddress :
                    bcc) {
                string.append(toAddress).append(",");
            }
            mappedEmailWithAttachment.setBcc(string.substring(0, string.length() - 1));
        }

        // set attachments
        List<MultipartFile> attachments = emailWithAttachmentDto.getAttachments();
        if (attachments != null && attachments.size() != 0){
            List<EmailAttachment> emailAttachments = new ArrayList<>();

            attachments.forEach(attachment -> {
            try {
                emailAttachments.add(EmailAttachment.builder().fileName(attachment.getOriginalFilename()).content(attachment.getBytes()).build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            });
            mappedEmailWithAttachment.setAttachments(emailAttachments);
        }
        return mappedEmailWithAttachment;
    }


    @Override
    public EmailWithAttachmentResponse toDto(EmailWithAttachment emailWithAttachment) {

        EmailWithAttachmentResponse mappedEmailWithAttachmentDto = emailWithAttachmentMapper.toDto(emailWithAttachment);

        String to = emailWithAttachment.getEmailTo();
        if (to != null && !to.isEmpty())
            mappedEmailWithAttachmentDto.setEmailTo(to.split(","));

        String cc = emailWithAttachment.getCc();
        if (cc != null && !cc.isEmpty())
            mappedEmailWithAttachmentDto.setCc(cc.split(","));

        String bcc = emailWithAttachment.getBcc();
        if (bcc != null && !bcc.isEmpty())
            mappedEmailWithAttachmentDto.setBcc(bcc.split(","));

        List<EmailAttachment> attachments = emailWithAttachment.getAttachments();
        List<String> emailAttachments = new ArrayList<>();
        if (attachments != null && attachments.size() != 0){
            attachments.forEach(emailAttachment -> emailAttachments.add(emailAttachment.getFileName()));
            mappedEmailWithAttachmentDto.setAttachments(emailAttachments);
        }
        return mappedEmailWithAttachmentDto;
    }
}
