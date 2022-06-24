package com.eucossa.notification_service.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Simple Email", description = "Simple Email Details")
public class SimpleEmailDto {

    @ApiModelProperty(name = "id", example = "1", notes = "Id of an email object.")
    private BigDecimal id;

    @ApiModelProperty(name = "subject", example = "Shipment Approval", notes = "This field holds an emails subject.")
    private String subject;

    @ApiModelProperty(name = "to", example = "[abc@xyz.com, efg@mno.com]", notes = "A list of recipients to receive the email.")
    private String[] emailTo;

    @ApiModelProperty(name = "cc", example = "[abc@xyz.com, efg@mno.com]", notes = "A list of carbon copied recipients to receive the email.")
    private String[] cc;

    @ApiModelProperty(name = "cc", example = "[abc@xyz.com, efg@mno.com]", notes = "A list of blind carbon copied recipients to receive the email.")
    private String[] bcc;

    @ApiModelProperty(name = "message", example = "Dear xyz, ...", notes = "the body of the email.")
    private String message;

    @ApiModelProperty(name = "version", example = "1", notes = "Record version to avoid stale records.")
    private Integer version;
}
