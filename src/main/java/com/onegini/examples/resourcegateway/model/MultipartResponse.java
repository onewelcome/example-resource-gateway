package com.onegini.examples.resourcegateway.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MultipartResponse {
  private String name;
  private String email;
  @Builder.Default
  private List<Attachment> attachments = new ArrayList<>();
}
