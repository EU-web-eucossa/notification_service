package com.eucossa.notification_service.dtos;

import com.eucossa.notification_service.enums.YesOrNo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Email With Attachment", description = "Email with Attachments  details")
public class EmailWithAttachmentDto {

    @Null
    @ApiModelProperty(name = "id", example = "1", notes = "Id of an email object.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
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

    @ApiModelProperty(name = "attachments", notes = "The attachment files to be bundled within the email")
    private List<MultipartFile> attachments = new ArrayList<>();

    @ApiModelProperty(name = "version", example = "1", notes = "Record version to avoid stale records.")
    private Integer version;

}
