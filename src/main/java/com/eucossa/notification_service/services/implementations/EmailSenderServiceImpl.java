package com.eucossa.notification_service.services.implementations;

import com.eucossa.notification_service.dtos.EmailWithAttachmentDto;
import com.eucossa.notification_service.dtos.EmailWithAttachmentResponse;
import com.eucossa.notification_service.dtos.SimpleEmailDto;
import com.eucossa.notification_service.entities.EmailAttachment;
import com.eucossa.notification_service.entities.EmailWithAttachment;
import com.eucossa.notification_service.entities.SimpleEmail;
import com.eucossa.notification_service.exceptions.EmptyEmailRecipientAddressException;
import com.eucossa.notification_service.mappers.EmailWithAttachmentMapper;
import com.eucossa.notification_service.mappers.SimpleEmailMapper;
import com.eucossa.notification_service.models.EmailObjectFromBroker;
import com.eucossa.notification_service.repositories.EmailWithAttachmentRepository;
import com.eucossa.notification_service.repositories.SimpleEmailRepository;
import com.eucossa.notification_service.services.interfaces.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final SimpleEmailRepository simpleEmailRepository;
    private final SimpleEmailMapper simpleEmailMapper;
    private final EmailWithAttachmentRepository emailWithAttachmentRepository;
    private final EmailWithAttachmentMapper emailWithAttachmentMapper;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Override
    public SimpleEmailDto sendSimpleEmail(SimpleEmailDto simpleEmailDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        String[] to = simpleEmailDto.getEmailTo();
        if (to == null || to.length < 1)
            throw new EmptyEmailRecipientAddressException("To address cannot be empty or null");
        else
            simpleMailMessage.setTo(to);

        String[] cc = simpleEmailDto.getCc();
        if (cc != null && cc.length > 0)
            simpleMailMessage.setCc(cc);

        String[] bcc = simpleEmailDto.getBcc();
        if (bcc != null && bcc.length > 0)
            simpleMailMessage.setBcc(bcc);

        simpleMailMessage.setSubject(simpleEmailDto.getSubject());
        simpleMailMessage.setText(simpleEmailDto.getMessage());

        // send
        log.info("Sending an email to: " + Arrays.toString(simpleEmailDto.getEmailTo()));
        javaMailSender.send(simpleMailMessage);

        // save the delivered email
        log.info("Persisting sent email.");
        SimpleEmail savedSimpleEmail = simpleEmailRepository.save(simpleEmailMapper.toEntity(simpleEmailDto));
        return simpleEmailMapper.toDto(savedSimpleEmail);
    }

    @Override
    public EmailWithAttachmentResponse sendEmailWithAttachments(EmailWithAttachmentDto emailWithAttachmentDto) {
        log.info("Sending email with attachment to: " + Arrays.toString(emailWithAttachmentDto.getEmailTo()));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = getMimeMessageHelper(mimeMessage, emailWithAttachmentDto.getEmailTo(), emailWithAttachmentDto.getCc(), emailWithAttachmentDto.getBcc(), emailWithAttachmentDto.getSubject(), emailWithAttachmentDto.getMessage());

            // set attachments when available
            List<MultipartFile> attachments = emailWithAttachmentDto.getAttachments();
            if (attachments != null && attachments.size() > 0)
                attachments.forEach(attachment -> {
                    try {
                        mimeMessageHelper.addAttachment(Objects.requireNonNull(attachment.getOriginalFilename()), new ByteArrayResource(attachment.getBytes()));
                    } catch (MessagingException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });

            // send the email
            javaMailSender.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // persist the delivered email
        EmailWithAttachment emailWithAttachment = emailWithAttachmentMapper.toEntity(emailWithAttachmentDto);
        EmailWithAttachment savedEmailWithAttachment = emailWithAttachmentRepository.save(emailWithAttachment);

        return emailWithAttachmentMapper.toDto(savedEmailWithAttachment);
    }

    @Override
    public EmailWithAttachmentResponse sendEmailWithAttachmentsFromBroker(EmailObjectFromBroker emailObjectFromBroker) {
        log.info("Sending email with attachment to: " + Arrays.toString(emailObjectFromBroker.getEmailTo()));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = getMimeMessageHelper(mimeMessage, emailObjectFromBroker.getEmailTo(), emailObjectFromBroker.getCc(), emailObjectFromBroker.getBcc(), emailObjectFromBroker.getSubject(), emailObjectFromBroker.getMessageInHtml());

            // set attachments when available
            List<EmailAttachment> attachments = emailObjectFromBroker.getAttachments();
            if (attachments != null && attachments.size() > 0)
                attachments.forEach(attachment -> {
                    try {
                        mimeMessageHelper.addAttachment(Objects.requireNonNull(attachment.getOriginalFileName()), new ByteArrayResource(attachment.getContentInBytes()));
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                });

            // send the email
            javaMailSender.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // persist the delivered email
        EmailWithAttachment emailWithAttachment = emailWithAttachmentMapper.emailDtoFromBrokerToEntity(emailObjectFromBroker);
        EmailWithAttachment savedEmailWithAttachment = emailWithAttachmentRepository.save(emailWithAttachment);
        return emailWithAttachmentMapper.toDto(savedEmailWithAttachment);
    }

    private MimeMessageHelper getMimeMessageHelper(MimeMessage mimeMessage, String[] emailObject, String[] emailObject1, String[] emailObject2, String emailObject3, String emailObject4) throws MessagingException, UnsupportedEncodingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // set to address when not null or empty
        String[] to = emailObject;
        if (to == null || to.length < 1)
            throw new EmptyEmailRecipientAddressException("To address cannot be empty or null");
        else
            mimeMessageHelper.setTo(to);

        // set cc address if not null or empty
        String[] cc = emailObject1;
        if (cc != null && cc.length > 0)
            mimeMessageHelper.setCc(cc);


        // set bcc address when not null or empty
        String[] bcc = emailObject2;
        if (bcc != null && bcc.length > 0)
            mimeMessageHelper.setBcc(bcc);

        // set from
        mimeMessageHelper.setFrom(emailFrom, "EUCOSSA - ONLINE SHOP");

        // set subject
        mimeMessageHelper.setSubject(emailObject3);

        // set body
        mimeMessageHelper.setText(emailObject4, true);
        return mimeMessageHelper;
    }
}
