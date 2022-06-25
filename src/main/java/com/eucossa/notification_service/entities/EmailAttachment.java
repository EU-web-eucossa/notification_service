package com.eucossa.notification_service.entities;

import lombok.*;

import javax.persistence.*;

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
    private String fileName;

    @ToString.Exclude
    @Lob @Basic(fetch=LAZY)
    private byte[] content;
}