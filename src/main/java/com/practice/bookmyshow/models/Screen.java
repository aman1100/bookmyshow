package com.practice.bookmyshow.models;

import com.practice.bookmyshow.enums.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel {
        private String name;
        @OneToMany
        private List<Seat> seats;
        @Enumerated(EnumType.ORDINAL)
        @ElementCollection // -> Mapping table
        private List<Feature> features;
}
