package com.eucossa.notification_service.services.interfaces;

import com.eucossa.notification_service.models.OrderFromOrderConfirmationQueue;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
public interface AmqpService {

    /**
     * This method listens for posted confirmed orders and notifies the clients
     *
     * @param orderFromOrderConfirmationQueue - the posted confirmed order
     */
    void listenToOrderConfirmationQueueAndSendNotification(OrderFromOrderConfirmationQueue orderFromOrderConfirmationQueue);

    void sendSampleOrderConfirmationObject();
}
