package com.eucossa.notification_service.services.implementations;

import com.eucossa.notification_service.dtos.SimpleEmailDto;
import com.eucossa.notification_service.entities.SimpleEmail;
import com.eucossa.notification_service.exceptions.EmptyEmailRecipientAddressException;
import com.eucossa.notification_service.mappers.SimpleEmailMapper;
import com.eucossa.notification_service.repositories.SimpleEmailRepository;
import com.eucossa.notification_service.services.interfaces.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

        simpleMailMessage.setText(simpleEmailDto.getMessage());

        // send
        log.info("Sending an email to: " + Arrays.toString(simpleEmailDto.getEmailTo()));
        javaMailSender.send(simpleMailMessage);

        // save the delivered email
        log.info("Persisting sent email.");
        SimpleEmail savedSimpleEmail = simpleEmailRepository.save(simpleEmailMapper.toEntity(simpleEmailDto));
        return simpleEmailMapper.toDto(savedSimpleEmail);
    }
}
