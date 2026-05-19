package com.order.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommonApiResponse {

    private boolean success;

    private String code;

    private String message;

    private Object data;

    private LocalDateTime timestamp;
}