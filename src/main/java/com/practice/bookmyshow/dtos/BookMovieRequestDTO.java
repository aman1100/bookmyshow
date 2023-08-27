package com.practice.bookmyshow.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookMovieRequestDTO {

    private Long userId;
    private List<Long> seatIds;
    private Long showId;


}
