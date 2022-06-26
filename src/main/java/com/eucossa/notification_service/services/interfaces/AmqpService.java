package com.eucossa.notification_service.services.interfaces;

import com.eucossa.notification_service.models.EmailObject;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
public interface AmqpService {

    /**
     * This method listens for posted confirmed orders and notifies the clients
     *
     * @param emailObject - the posted confirmed order email to be sent
     */
    void listenToOrderConfirmationQueueAndSendNotification(EmailObject emailObject);

    /**
     * This method listens to posted email objects from the promotional notification queue
     *
     * @param emailObject - the posted email to be sent
     */
    void listenToPromotionalNotificationsQueue(EmailObject emailObject);
}
