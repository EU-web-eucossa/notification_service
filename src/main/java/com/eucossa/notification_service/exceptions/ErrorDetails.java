package com.eucossa.notification_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

    private String message;
    private String details;
    private Date timestamp;
}
