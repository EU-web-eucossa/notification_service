package com.eucossa.notification_service.repositories;

import com.eucossa.notification_service.entities.EmailWithAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
public interface EmailWithAttachmentRepository extends JpaRepository<EmailWithAttachment, BigDecimal> {
}
