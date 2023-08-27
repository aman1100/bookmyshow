package com.practice.bookmyshow.services;

import com.practice.bookmyshow.models.Show;
import com.practice.bookmyshow.models.ShowSeat;
import com.practice.bookmyshow.models.ShowSeatType;
import com.practice.bookmyshow.repositories.ShowSeatTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PriceCalculatorService {

    private ShowSeatTypeRepository showSeatTypeRepository;

    public int calculatePrice(List<ShowSeat> showSeats, Show show){
        List<ShowSeatType> showSeatTypeList = showSeatTypeRepository.findAllByShow(show);

        int amount = 0;
        for(ShowSeat showSeat : showSeats){
            for(ShowSeatType showSeatType : showSeatTypeList){
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    amount += showSeatType.getPrice();
                    break;
                }
            }
        }

        return amount;
    }
}
