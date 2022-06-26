package com.eucossa.notification_service.services.implementations;

import com.eucossa.notification_service.configs.AmqpConfig;
import com.eucossa.notification_service.configs.EmailTemplates;
import com.eucossa.notification_service.dtos.EmailWithAttachmentDto;
import com.eucossa.notification_service.models.Buyer;
import com.eucossa.notification_service.models.EmailObject;
import com.eucossa.notification_service.services.interfaces.AmqpService;
import com.eucossa.notification_service.services.interfaces.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;


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
    EmailObject order = EmailObject.builder()
            .subject("ORDER CONFIRMATION NOTIFICATION")
            .messageInHtml(emailBody)
            .emailTo(new String[]{"otienochris98@gmail.com"})
            .id(UUID.randomUUID())
            .build();

    @Override
    @RabbitListener(queues = {AmqpConfig.ORDER_CONFIRMATION_QUEUE})
    public void listenToOrderConfirmationQueueAndSendNotification(EmailObject emailObject) {
        log.info("Notifying Customer of order confirmation");
        EmailWithAttachmentDto email = EmailWithAttachmentDto.builder()
                .subject(order.getSubject())
                .emailTo(emailObject.getEmailTo())
                .cc(emailObject.getCc() == null ? new String[]{} : emailObject.getCc())
                .bcc(emailObject.getBcc() == null ? new String[]{} : emailObject.getBcc())
                .message(emailObject.getMessageInHtml())
                .build();
        emailSenderService.sendEmailWithAttachments(email);
    }

    @Override
    @RabbitListener(queues = {AmqpConfig.PROMOTIONAL_NOTIFICATION_QUEUE})
    public void listenToPromotionalNotificationsQueue(EmailObject emailObject) {
        log.info("Sending Promotional Message");
        EmailWithAttachmentDto email = EmailWithAttachmentDto.builder()
                .subject(emailObject.getSubject())
                .emailTo(emailObject.getEmailTo() == null ? new String[]{} : emailObject.getEmailTo())
                .cc(emailObject.getCc() == null ? new String[]{} : emailObject.getCc())
                .bcc(emailObject.getBcc() == null ? new String[]{} : emailObject.getBcc())
                .attachments(emailObject.getAttachments())
                .message(emailObject.getMessageInHtml())
                .build();

        emailSenderService.sendEmailWithAttachments(email);
    }

    @Scheduled(cron = "${cron.for.promotional.notifications}")
    public void sendPromotionalNotifications() {
        log.info("Posting a new Promotional Notification email");
        order.setSubject("PROMOTIONAL NOTIFICATION");
        rabbitTemplate.convertAndSend(AmqpConfig.PROMOTIONAL_NOTIFICATION_QUEUE, order);
    }

    @Scheduled(fixedRate = 60000)
    public void sendSampleOrderConfirmationObject() {
        log.info("Posting a new Confirmation Order.");
        rabbitTemplate.convertAndSend(AmqpConfig.ORDER_CONFIRMATION_QUEUE, order);
    }
}
