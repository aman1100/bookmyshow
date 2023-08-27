package com.practice.bookmyshow.controllers;

import com.practice.bookmyshow.dtos.BookMovieRequestDTO;
import com.practice.bookmyshow.dtos.BookMovieResponseDTO;
import com.practice.bookmyshow.enums.ResponseStatus;
import com.practice.bookmyshow.models.Booking;
import com.practice.bookmyshow.services.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;

    public BookMovieResponseDTO bookMovie(BookMovieRequestDTO requestDTO){
        BookMovieResponseDTO bookMovieResponseDTO = new BookMovieResponseDTO();
        Booking booking;
        try{
            booking = bookingService.bookMovie(
                    requestDTO.getUserId(),
                    requestDTO.getSeatIds(),
                    requestDTO.getShowId()
            );
           bookMovieResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
           bookMovieResponseDTO.setBookingId(booking.getId());
           bookMovieResponseDTO.setAmount(booking.getAmount());
        }catch (Exception e){
            bookMovieResponseDTO.setResponseStatus(ResponseStatus.FAILIURE);
        }
        return bookMovieResponseDTO;
    }
}
