package com.eucossa.notification_service.mappers;

import com.eucossa.notification_service.dtos.SimpleEmailDto;
import com.eucossa.notification_service.entities.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */
public class SimpleEmailMapperDecorator implements SimpleEmailMapper {

    @Autowired
    @Qualifier("delegate")
    private SimpleEmailMapper simpleEmailMapper;

    @Override
    public SimpleEmail toEntity(SimpleEmailDto simpleEmailDto) {
        SimpleEmail mappedSimpleEmail = simpleEmailMapper.toEntity(simpleEmailDto);


        String[] to = simpleEmailDto.getEmailTo();
        if (to != null && to.length > 0) {
            StringBuilder string = new StringBuilder();
            for (String toAddress :
                    to) {
                string.append(toAddress).append(",");
            }
            mappedSimpleEmail.setEmailTo(string.substring(0, string.length() - 1));
        }

        String[] cc = simpleEmailDto.getCc();
        if (cc != null && cc.length > 0) {
            StringBuilder string = new StringBuilder();
            for (String toAddress :
                    cc) {
                string.append(toAddress).append(",");
            }
            mappedSimpleEmail.setCc(string.substring(0, string.length() - 1));
        }

        String[] bcc = simpleEmailDto.getBcc();
        if (bcc != null && bcc.length > 0) {
            StringBuilder string = new StringBuilder();
            for (String toAddress :
                    bcc) {
                string.append(toAddress).append(",");
            }
            mappedSimpleEmail.setBcc(string.substring(0, string.length() - 1));
        }
        return mappedSimpleEmail;
    }

    @Override
    public SimpleEmailDto toDto(SimpleEmail simpleEmail) {
        SimpleEmailDto mappedSimpleEmailDto = simpleEmailMapper.toDto(simpleEmail);

        String to = simpleEmail.getEmailTo();
        if (to != null && !to.isEmpty())
            mappedSimpleEmailDto.setEmailTo(to.split(","));

        String cc = simpleEmail.getCc();
        if (cc != null && !cc.isEmpty())
            mappedSimpleEmailDto.setCc(cc.split(","));

        String bcc = simpleEmail.getBcc();
        if (bcc != null && !bcc.isEmpty())
            mappedSimpleEmailDto.setBcc(bcc.split(","));

        return mappedSimpleEmailDto;
    }
}
