package com.example.carsharingservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Schema
public class RentalResponseDto {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "2023-06-10")
    private LocalDateTime rentalDate;
    @Schema(example = "2023-06-15")
    private LocalDateTime returnDate;
    @Schema(example = "2023-06-15")
    private LocalDateTime actualReturnDate;
    @Schema(example = "1")
    private Long carId;
    @Schema(example = "1")
    private Long userId;
}
