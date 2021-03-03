package com.onegini.examples.resourcegateway.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDataWithFiles {
  private String name;
  private String email;
  @Builder.Default
  private List<MultipartFile> attachments = new ArrayList<>();
}
