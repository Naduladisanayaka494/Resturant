package com.nadulahotel.nadulahotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nadulahotel.nadulahotel.response.BlobSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked=false;
    @OneToMany(mappedBy = "room",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookRoom> bookings;

    @Lob
    @JsonSerialize(using = BlobSerializer.class)
    private Blob photo;
    public Room() {
        this.bookings= new ArrayList<>();
    }

    public void addBooking(BookRoom booking){
        if(bookings==null){
            bookings=new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked=true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        booking.setBookingConfirmationCode(bookingCode);
    }
}
