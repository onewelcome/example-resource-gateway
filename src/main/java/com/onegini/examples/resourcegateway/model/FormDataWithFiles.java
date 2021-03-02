package com.onegini.examples.resourcegateway.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FormDataWithFiles {
  private String name;
  private String email;
  private List<MultipartFile> attachments = new ArrayList<>();
}
