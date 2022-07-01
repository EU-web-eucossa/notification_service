package com.eucossa.notification_service.mappers;

import com.eucossa.notification_service.dtos.EmailWithAttachmentDto;
import com.eucossa.notification_service.dtos.EmailWithAttachmentResponse;
import com.eucossa.notification_service.entities.EmailWithAttachment;
import com.eucossa.notification_service.models.EmailObjectFromBroker;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */

@Mapper(componentModel = "spring")
@DecoratedWith(EmailWithAttachmentMapperDecorator.class)
public interface EmailWithAttachmentMapper {

    EmailWithAttachmentMapper INSTANCE = Mappers.getMapper(EmailWithAttachmentMapper.class);

    /**
     * This method maps an email dto into its equivalent email entity
     *
     * @param emailWithAttachmentDto - the  email dto to be mapped
     * @return the mapped  email entity
     */
    @Mappings(value = {
            @Mapping(target = "emailTo", ignore = true),
            @Mapping(target = "cc", ignore = true),
            @Mapping(target = "bcc", ignore = true),
            @Mapping(target = "attachments", ignore = true),
    })
    EmailWithAttachment toEntity(EmailWithAttachmentDto emailWithAttachmentDto);

    /**
     * This method maps an email entity into its equivalent email dto
     *
     * @param emailWithAttachment - the  email entity to be mapped
     * @return the mapped  email dto
     */
    @Mappings(value = {
            @Mapping(target = "emailTo", ignore = true),
            @Mapping(target = "cc", ignore = true),
            @Mapping(target = "bcc", ignore = true),
            @Mapping(target = "attachments", ignore = true),
    })
    EmailWithAttachmentResponse toDto(EmailWithAttachment emailWithAttachment);

    @Mappings(value = {
            @Mapping(target = "emailTo", ignore = true),
            @Mapping(target = "cc", ignore = true),
            @Mapping(target = "bcc", ignore = true),
            @Mapping(target = "attachments", ignore = true),
    })
    EmailWithAttachment emailDtoFromBrokerToEntity(EmailObjectFromBroker emailObjectFromBroker);
}
