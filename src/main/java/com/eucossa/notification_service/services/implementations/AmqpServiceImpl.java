package com.eucossa.notification_service.services.implementations;

import com.eucossa.notification_service.services.interfaces.AmqpService;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
public class AmqpServiceImpl implements AmqpService {
    @Override
    public void listenToOrderConfirmationQueueAndSendNotification(OrderFromOrderConfirmationQueue orderFromOrderConfirmationQueue) {

    }
}
