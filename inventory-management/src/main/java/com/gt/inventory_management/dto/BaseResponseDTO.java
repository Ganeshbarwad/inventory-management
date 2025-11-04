package com.gt.inventory_management.dto;

import lombok.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponseDTO<T> {
    private String status;    // "success" | "error"
    private String message;
    private T data;
    private Instant timestamp;

    public static <T> BaseResponseDTO<T> success(String message, T data) {
        return BaseResponseDTO.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    public static <T> BaseResponseDTO<T> error(String message) {
        return BaseResponseDTO.<T>builder()
                .status("error")
                .message(message)
                .data(null)
                .timestamp(Instant.now())
                .build();
    }
}
