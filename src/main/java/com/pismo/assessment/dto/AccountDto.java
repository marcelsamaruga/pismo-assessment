package com.pismo.assessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDto {
    @JsonProperty("account_id")
    Long accountId;

    @JsonProperty("document_number")
    String documentNumber;

}
