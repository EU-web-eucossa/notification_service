package com.eucossa.notification_service.models;

import com.eucossa.notification_service.entities.EmailAttachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailObject {
    private BigDecimal id;
    private String[] cc;
    private String[] bcc;
    private String[] emailTo;
    private String subject;
    private String messageInHtml;
    private List<EmailAttachment> attachments;
}
