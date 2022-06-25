package com.eucossa.notification_service.entities;

import com.eucossa.notification_service.enums.YesOrNo;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "emails_with_attachments")
public class EmailWithAttachment {

    @Id
    @GeneratedValue
    private BigDecimal id;

    private String subject;
    private String emailTo;
    private String cc;
    private String bcc;
    @Lob @Basic(fetch = FetchType.EAGER)
    private String message;

    @OneToMany(cascade = CascadeType.ALL)
    private List<EmailAttachment> attachments = new ArrayList<>();

    @Version
    private Integer version;
    @CreationTimestamp
    private Timestamp dateSent;
    @UpdateTimestamp
    private Timestamp dateUpdated;
}
