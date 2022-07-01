package com.eucossa.notification_service.services.implementations;

import com.eucossa.notification_service.configs.AmqpConfig;
import com.eucossa.notification_service.configs.EmailTemplates;
import com.eucossa.notification_service.entities.EmailAttachment;
import com.eucossa.notification_service.models.EmailObjectFromBroker;
import com.eucossa.notification_service.services.interfaces.AmqpService;
import com.eucossa.notification_service.services.interfaces.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AmqpServiceImpl implements AmqpService {

    private final RabbitTemplate rabbitTemplate;
    private final EmailSenderService emailSenderService;
    String emailBody = EmailTemplates.HTML_BEGIN_TAGS +
            EmailTemplates.ORDER_CONFIRMATION_HEADER +
            EmailTemplates.ORDER_CONFIRMATION_BODY +
            EmailTemplates.ORDER_CONFIRMATION_FOOTER +
            EmailTemplates.HTML_END_TAGS;
    EmailObjectFromBroker emailObjectFromBroker = EmailObjectFromBroker.builder()
            .subject("ORDER CONFIRMATION NOTIFICATION")
            .messageInHtml(emailBody)
            .emailTo(new String[]{"otienochris98@gmail.com"})
            .id(BigDecimal.ONE)
            .attachments(List.of(EmailAttachment.builder().contentInBytes("Content".getBytes()).originalFileName("fileA.pdf").build()))
            .build();

    @Override
    @RabbitListener(queues = {AmqpConfig.NOTIFICATION_QUEUE})
    public void listenToOrderConfirmationQueueAndSendNotification(EmailObjectFromBroker emailObjectFromBroker) {
        log.info("Sending notifications");
        System.out.println(emailObjectFromBroker);
        emailSenderService.sendEmailWithAttachmentsFromBroker(emailObjectFromBroker);
        log.info("Notification sent successfully");
    }

    @Override
    @RabbitListener(queues = {AmqpConfig.PROMOTIONAL_NOTIFICATION_QUEUE})
    public void listenToPromotionalNotificationsQueue(EmailObjectFromBroker emailObjectFromBroker) {
        log.info("Sending Promotional Message");
        emailSenderService.sendEmailWithAttachmentsFromBroker(emailObjectFromBroker);
    }

}
