package com.meraphorce.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Data Transfer Object to represent bulk operation status.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class StatusResponse {
    /**
     * True when the operation is successful.
     */
    private Boolean successful;

    /**
     * Bulk operation status details.
     */
    private String message;
}
