package com.eucossa.notification_service.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class SimpleEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigDecimal id;
    private String subject;
    private String emailTo;
    private String cc;
    private String bcc;
    private String message;
    @Version
    private Integer version;
    @CreationTimestamp
    private Timestamp dateSent;
    @UpdateTimestamp
    private Timestamp dateUpdated;
}
