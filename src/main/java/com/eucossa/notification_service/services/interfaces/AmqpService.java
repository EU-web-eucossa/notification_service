package com.eucossa.notification_service.services.interfaces;

import com.eucossa.notification_service.models.EmailObjectFromBroker;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
public interface AmqpService {

    /**
     * This method listens for posted confirmed orders and notifies the clients
     *
     * @param emailObjectFromBroker - the posted confirmed order email to be sent
     */
    void listenToOrderConfirmationQueueAndSendNotification(EmailObjectFromBroker emailObjectFromBroker);

    /**
     * This method listens to posted email objects from the promotional notification queue
     *
     * @param emailObjectFromBroker - the posted email to be sent
     */
    void listenToPromotionalNotificationsQueue(EmailObjectFromBroker emailObjectFromBroker);
}
