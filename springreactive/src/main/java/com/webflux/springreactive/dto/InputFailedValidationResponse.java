package com.webflux.springreactive.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class InputFailedValidationResponse {
    private int errorCode;
    private int input;
    private String message;
}
