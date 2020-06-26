package com.everon.carchargingsession.dto;

import lombok.*;

/** Dto for managing errorCode and Error Messages */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericErrorDto {

  private String errorCode;
  private String errorMessage;
}
