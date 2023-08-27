package com.practice.bookmyshow.dtos;

import com.practice.bookmyshow.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDTO {
    private ResponseStatus responseStatus;
    private int amount;
    private Long bookingId;
}
