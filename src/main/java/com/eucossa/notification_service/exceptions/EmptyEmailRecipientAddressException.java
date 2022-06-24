package com.eucossa.notification_service.exceptions;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */
public class EmptyEmailRecipientAddressException extends RuntimeException {
    public EmptyEmailRecipientAddressException(String message) {
        super(message);
    }
}
