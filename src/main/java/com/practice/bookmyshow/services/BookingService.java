package com.practice.bookmyshow.services;

import com.practice.bookmyshow.enums.BookingStatus;
import com.practice.bookmyshow.enums.ShowSeatStatus;
import com.practice.bookmyshow.exceptions.SeatAlreayBlockedException;
import com.practice.bookmyshow.exceptions.ShowNotFoundException;
import com.practice.bookmyshow.exceptions.ShowSeatNotFoundException;
import com.practice.bookmyshow.exceptions.UserNotFoundException;
import com.practice.bookmyshow.models.Booking;
import com.practice.bookmyshow.models.Show;
import com.practice.bookmyshow.models.ShowSeat;
import com.practice.bookmyshow.models.User;
import com.practice.bookmyshow.repositories.BookingRepository;
import com.practice.bookmyshow.repositories.ShowRepository;
import com.practice.bookmyshow.repositories.ShowSeatRepository;
import com.practice.bookmyshow.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculatorService priceCalculatorService;
    private BookingRepository bookingRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId,
                             List<Long> seatIds,
                             Long showId)
            throws UserNotFoundException, ShowNotFoundException, ShowSeatNotFoundException, SeatAlreayBlockedException {
        //1. get the user with user id
        //2. Get the show with the showId
        //------------START the transaction--------
        //3. Get the seats with the seats id
        //4. Check the availability of the seats.
        //5. If no return error
        //6. If yes, mark the status of the seats as locked.
        //7. Save the updated seat status as locked in DB.
        //------------END the transaction-----------
        //8. compute the amount
        //9. save the booking object
        //10. return booking.

        //To start transaction in between need more knowledge on spring boot. But we can make the whole bookMovie method as an transaction using the annotation.

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User bookedBy = userOptional.get();

        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new ShowNotFoundException();
        }
        Show bookedShow = showOptional.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(seatIds);
        if(showSeats.isEmpty() || showSeats.size() != seatIds.size()){
            throw  new ShowSeatNotFoundException();
        }

        for(ShowSeat showSeat : showSeats){
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) ||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                            Duration.between(showSeat.getBlockedAt().toInstant(), new Date().toInstant()).toMinutes() > 15)){
                    throw new SeatAlreayBlockedException();
            }
        }

        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
        }

        List<ShowSeat> updatedShowSeats = showSeatRepository.saveAll(showSeats);
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.IN_PROGRESS);
        booking.setShowSeats(updatedShowSeats);
        booking.setUser(bookedBy);
        booking.setBookedAt(new Date());
        booking.setShow(bookedShow);
        booking.setAmount(priceCalculatorService.calculatePrice(updatedShowSeats, bookedShow));

        booking = bookingRepository.save(booking);
        return booking;
    }
}
