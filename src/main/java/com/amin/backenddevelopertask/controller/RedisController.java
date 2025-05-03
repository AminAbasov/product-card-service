package com.amin.backenddevelopertask.controller;

import com.amin.backenddevelopertask.service.redis.RedisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
@Validated
public class RedisController {

    private final RedisService redisService;

    @Operation(summary = "Cache user data",
            description = "Stores user email and full name in Redis for 10 minutes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User data cached successfully",
                    content = @Content(schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = "503", description = "Redis service unavailable",
                    content = @Content(schema = @Schema(implementation = CustomApiResponse.class)))
    })
    @PostMapping("/user")
    public ResponseEntity<CustomApiResponse> cacheUser(@RequestBody @Valid UserRequest userRequest) {
        try {
            redisService.cacheUser(userRequest.getEmail(), userRequest.getAdSoyad());
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new CustomApiResponse(true, "User data cached successfully", LocalDateTime.now())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                    new CustomApiResponse(false, "Failed to cache user data: " + e.getMessage(), LocalDateTime.now())
            );
        }
    }

    @Operation(summary = "Get cached user data",
            description = "Retrieves user full name from Redis by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data found",
                    content = @Content(schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "User data not found or expired",
                    content = @Content(schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = "503", description = "Redis service unavailable",
                    content = @Content(schema = @Schema(implementation = CustomApiResponse.class)))
    })
    @GetMapping("/user/{email}")
    public ResponseEntity<CustomApiResponse> getUser(
            @Parameter(description = "User's email address", required = true)
            @PathVariable @Email @NotBlank String email) {
        try {
            String adSoyad = redisService.getUserByEmail(email);
            if (adSoyad != null) {
                return ResponseEntity.ok(
                        new CustomApiResponse(true, adSoyad, LocalDateTime.now())
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new CustomApiResponse(false, "User not found", LocalDateTime.now())
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                    new CustomApiResponse(false, "Failed to retrieve user data: " + e.getMessage(), LocalDateTime.now())
            );
        }
    }

    @Data
    public static class UserRequest {
        @Email(message = "Email formatı yanlışdır")
        @NotBlank(message = "Email boş ola bilməz")
        private String email;

        @NotBlank(message = "Ad Soyad boş ola bilməz")
        @Size(min = 2, max = 100, message = "Ad Soyad 2-100 simvol aralığında olmalıdır")
        private String adSoyad;
    }

    @Data
    @AllArgsConstructor
    public static class CustomApiResponse {
        private boolean success;
        private String message;
        private LocalDateTime timestamp;
    }
}
