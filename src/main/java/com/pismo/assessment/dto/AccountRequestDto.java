package com.pismo.assessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountRequestDto {
    @JsonProperty("document_number")
    private String documentNumber;
}
