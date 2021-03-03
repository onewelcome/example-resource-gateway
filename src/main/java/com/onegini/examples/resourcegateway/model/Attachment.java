package com.onegini.examples.resourcegateway.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Attachment {
  private String fileName;
  private String contentType;
  private long fileSize;
  private byte[] body;
}
