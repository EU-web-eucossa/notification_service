package com.eucossa.notification_service.services.implementations;

import com.eucossa.notification_service.configs.AmqpConfig;
import com.eucossa.notification_service.configs.EmailTemplates;
import com.eucossa.notification_service.dtos.EmailWithAttachmentDto;
import com.eucossa.notification_service.models.Buyer;
import com.eucossa.notification_service.models.OrderFromOrderConfirmationQueue;
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

    private final Buyer buyer = Buyer.builder()
            .city("Nairobi")
            .email("otienochris98@gmail.com")
            .phoneNumber("0742887480")
            .username("Wajahckoyah")
            .zipCode("00100")
            .build();

    @Override
    @RabbitListener(queues = {AmqpConfig.ORDER_CONFIRMATION_QUEUE})
    public void listenToOrderConfirmationQueueAndSendNotification(OrderFromOrderConfirmationQueue orderFromOrderConfirmationQueue) {
        log.info("Notifying Customer of order confirmation");
        EmailWithAttachmentDto email = EmailWithAttachmentDto.builder()
                .subject("ORDER CONFIRMATION")
                .emailTo(orderFromOrderConfirmationQueue.getEmailTo())
                .message(orderFromOrderConfirmationQueue.getOrderInHtmlFormat())
                .build();
        emailSenderService.sendEmailWithAttachments(email);
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void sendSampleOrderConfirmationObject() {
        log.info("Posting a new Order to the queue");
        String emailBody = EmailTemplates.HTML_BEGIN_TAGS +
                EmailTemplates.ORDER_CONFIRMATION_HEADER +
                EmailTemplates.ORDER_CONFIRMATION_BODY +
                EmailTemplates.ORDER_CONFIRMATION_FOOTER +
                EmailTemplates.HTML_END_TAGS;
        OrderFromOrderConfirmationQueue order = OrderFromOrderConfirmationQueue.builder()
                .orderInHtmlFormat(emailBody)
                .emailTo(new String[]{buyer.getEmail(), "oduorfrancis134@gmail.com"})
                .id(UUID.randomUUID())
                .build();
        rabbitTemplate.convertAndSend(AmqpConfig.ORDER_CONFIRMATION_QUEUE, order);
    }
}
