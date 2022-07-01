package com.eucossa.notification_service.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "email_attachments")
public class EmailAttachment {

    @Id
    @GeneratedValue
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private BigDecimal code;

    private String originalFileName;

    @ToString.Exclude
    @Lob
    @Basic(fetch = LAZY)
    private byte[] contentInBytes;
}