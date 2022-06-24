package com.eucossa.notification_service.services.interfaces;

import com.eucossa.notification_service.dtos.SimpleEmailDto;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */
public interface EmailSenderService {

    /**
     * This method sends a simple email that lacks attachments and have simple text as its content/message/body
     *
     * @param simpleEmailDto - the email object having details as email subject and the recipients (cc, to, bcc)
     * @return the sent email object
     */
    SimpleEmailDto sendSimpleEmail(SimpleEmailDto simpleEmailDto);
}
