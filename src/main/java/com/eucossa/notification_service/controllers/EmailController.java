package com.eucossa.notification_service.controllers;

import com.eucossa.notification_service.dtos.EmailWithAttachmentDto;
import com.eucossa.notification_service.dtos.EmailWithAttachmentResponse;
import com.eucossa.notification_service.dtos.SimpleEmailDto;
import com.eucossa.notification_service.services.interfaces.EmailSenderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 24/06/2022
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailSenderService emailSenderService;

    @CrossOrigin
    @PostMapping(value = "/send/simple-email", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "This end point send a simple email.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Email has been Successfully sent."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Resource Not Found"),
    })
    public ResponseEntity<SimpleEmailDto> sendEmail(@RequestBody @Validated SimpleEmailDto simpleEmailDto) {
        SimpleEmailDto sentEmail = emailSenderService.sendSimpleEmail(simpleEmailDto);
        String location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + sentEmail.getId()).toUriString();
        return ResponseEntity.created(URI.create(location)).body(sentEmail);
    }

    @CrossOrigin
    @PostMapping(value = "/send/email-with-attachment", consumes = "multipart/form-data", produces = "application/json")
    @ApiOperation(value = "This end point send a an email that has attachments.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Email has been Successfully sent."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Resource Not Found"),
    })
    public ResponseEntity<EmailWithAttachmentResponse> sendEmailWithAttachment(EmailWithAttachmentDto simpleEmailDto) {
        EmailWithAttachmentResponse emailWithAttachmentResponse = emailSenderService.sendEmailWithAttachments(simpleEmailDto);
        String location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + emailWithAttachmentResponse.getId()).toUriString();
        return ResponseEntity.created(URI.create(location)).header("Access-Control-Allow-Origin", "*").body(emailWithAttachmentResponse);
    }
}
