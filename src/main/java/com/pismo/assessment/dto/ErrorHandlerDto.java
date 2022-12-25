package com.pismo.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ErrorHandlerDto {
    private Integer code;
    private String message;
}
