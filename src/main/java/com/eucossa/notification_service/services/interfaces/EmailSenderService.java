package com.eucossa.notification_service.services.interfaces;

import com.eucossa.notification_service.dtos.EmailWithAttachmentDto;
import com.eucossa.notification_service.dtos.EmailWithAttachmentResponse;
import com.eucossa.notification_service.dtos.SimpleEmailDto;
import com.eucossa.notification_service.models.EmailObjectFromBroker;

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
     * @return the delivered and persisted email object
     */
    SimpleEmailDto sendSimpleEmail(SimpleEmailDto simpleEmailDto);

    /**
     * This method sends an email that has attachments
     *
     * @param emailWithAttachmentDto - the email object having standard email details as subject, attachments and recipients
     * @return the delivered and persisted email object
     */
    EmailWithAttachmentResponse sendEmailWithAttachments(EmailWithAttachmentDto emailWithAttachmentDto);

    /**
     * This method sends an email from the broker
     *
     * @param emailObjectFromBroker - the email object having standard email details as subject, attachments and recipients
     * @return the delivered and persisted email object
     */
    EmailWithAttachmentResponse sendEmailWithAttachmentsFromBroker(EmailObjectFromBroker emailObjectFromBroker);
}
