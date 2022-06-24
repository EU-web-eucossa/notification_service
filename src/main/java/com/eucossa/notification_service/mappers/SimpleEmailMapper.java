package com.eucossa.notification_service.mappers;

import com.eucossa.notification_service.dtos.SimpleEmailDto;
import com.eucossa.notification_service.entities.SimpleEmail;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */

@Mapper(componentModel = "spring")
@DecoratedWith(SimpleEmailMapperDecorator.class)
public interface SimpleEmailMapper {

    SimpleEmailMapper INSTANCE = Mappers.getMapper(SimpleEmailMapper.class);

    /**
     * This method maps a simple email dto into its equivalent simple email entity
     *
     * @param simpleEmailDto - the simple email dto to be mapped
     * @return the mapped simple email entity
     */
    @Mappings(value = {
            @Mapping(target = "emailTo", ignore = true),
            @Mapping(target = "cc", ignore = true),
            @Mapping(target = "bcc", ignore = true),
    })
    SimpleEmail toEntity(SimpleEmailDto simpleEmailDto);

    /**
     * This method maps a simple email entity into its equivalent simple email dto
     *
     * @param simpleEmail - the simple email entity to be mapped
     * @return the mapped simple email dto
     */
    @Mappings(value = {
            @Mapping(target = "emailTo", ignore = true),
            @Mapping(target = "cc", ignore = true),
            @Mapping(target = "bcc", ignore = true),
    })
    SimpleEmailDto toDto(SimpleEmail simpleEmail);
}
