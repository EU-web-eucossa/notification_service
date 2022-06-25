package com.eucossa.notification_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class OrderFromOrderConfirmationQueue {
    private UUID id;
    private String[] emailTo;
    private String orderInHtmlFormat;
}
