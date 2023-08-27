package com.practice.bookmyshow.services;

import com.practice.bookmyshow.models.Booking;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class BookingService {

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId,
                             List<Long> seatIds,
                             Long showId){
        //1. get the user with user id
        //2. Get the show with the showId
        //------------START the transaction--------
        //3. Get the seats with the seats id
        //4. Check the availability of the seats.
        //5. If no return error
        //6. If yes, mark the status of the seats as locked.
        //7. Save the updated seat status as locked in DB.
        //------------END the transaction-----------
        //8. return success.

        //To start transaction in between need more knowledge on spring boot. But we can make the whole bookMovie method as an transaction using the annotation.
        return null;
    }
}
