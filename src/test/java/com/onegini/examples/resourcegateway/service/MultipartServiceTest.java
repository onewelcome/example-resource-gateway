package com.onegini.examples.resourcegateway.service;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.onegini.examples.resourcegateway.model.Attachment;
import com.onegini.examples.resourcegateway.model.FormDataWithFiles;
import com.onegini.examples.resourcegateway.model.MultipartResponse;
import lombok.SneakyThrows;

class MultipartServiceTest {

  private final MultipartService multipartService = new MultipartService();
  public static final String NAME = "Jane Doe";
  public static final String EMAIL = "jane@example.com";
  public static final String FILE_NAME = "test.txt";
  public static final String ORIGINAL_FILE_NAME = "test file.txt";
  public static final String CONTENT_TYPE = "text/plain";
  public static final byte[] CONTENT = "This is the file content".getBytes(UTF_8);

  @Test
  void should_handle_empty_request() {
    final MultipartResponse response = multipartService.parse(FormDataWithFiles.builder().build());

    assertThat(response.getName()).isNull();
    assertThat(response.getEmail()).isNull();
    assertThat(response.getAttachments()).isEmpty();
  }

  @Test
  void should_return_request_with_attachments() {
    final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, CONTENT);

    final FormDataWithFiles formDataWithFiles = FormDataWithFiles.builder()
        .name(NAME)
        .email(EMAIL)
        .attachments(List.of(mockMultipartFile))
        .build();

    final MultipartResponse response = multipartService.parse(formDataWithFiles);

    assertResponseWithAttachment(mockMultipartFile, response);
  }

  @Test
  @SneakyThrows
  void should_handle_ioexception_gracefully() {
    final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, CONTENT);

    final MultipartFile brokenFile = mock(MultipartFile.class);
    final Resource brokenResource = mock(Resource.class);
    when(brokenFile.getResource()).thenReturn(brokenResource);
    when(brokenResource.contentLength()).thenThrow(new IOException("Test"));

    final FormDataWithFiles formDataWithFiles = FormDataWithFiles.builder()
        .name(NAME)
        .email(EMAIL)
        .attachments(List.of(brokenFile, mockMultipartFile))
        .build();

    final MultipartResponse response = multipartService.parse(formDataWithFiles);

    assertResponseWithAttachment(mockMultipartFile, response);
  }

  private void assertResponseWithAttachment(final MockMultipartFile mockMultipartFile, final MultipartResponse response) {
    assertThat(response.getName()).isEqualTo(NAME);
    assertThat(response.getEmail()).isEqualTo(EMAIL);

    final List<Attachment> attachments = response.getAttachments();
    assertThat(attachments).hasSize(1);
    final Attachment attachment = attachments.get(0);
    assertThat(attachment.getFileName()).isEqualTo(ORIGINAL_FILE_NAME);
    assertThat(attachment.getContentType()).isEqualTo(CONTENT_TYPE);
    assertThat(attachment.getFileSize()).isEqualTo(mockMultipartFile.getSize());
    assertThat(Base64.getDecoder().decode(attachment.getBody())).isEqualTo(CONTENT);
  }
}