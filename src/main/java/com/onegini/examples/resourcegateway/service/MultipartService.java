package com.onegini.examples.resourcegateway.service;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.onegini.examples.resourcegateway.model.Attachment;
import com.onegini.examples.resourcegateway.model.FormDataWithFiles;
import com.onegini.examples.resourcegateway.model.MultipartResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MultipartService {
  public MultipartResponse parse(final FormDataWithFiles formDataWithFiles) {
    final List<Attachment> attachmentList = formDataWithFiles.getAttachments()
        .stream()
        .map(attachment -> {
          try {
            return Attachment.builder()
                .fileName(attachment.getOriginalFilename())
                .contentType(attachment.getContentType())
                .fileSize(attachment.getResource().contentLength())
                .body(Base64.getEncoder().encode(attachment.getBytes()))
                .build();
          } catch (final Exception e) {
            log.error("Could not read uploaded file", e);
            return null;
          }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    return MultipartResponse.builder()
        .name(formDataWithFiles.getName())
        .email(formDataWithFiles.getEmail())
        .attachments(attachmentList)
        .build();
  }
}
