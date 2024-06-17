package com.nadulahotel.nadulahotel.response;

import com.nadulahotel.nadulahotel.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BookRoomResponse {



    private Long bookingId;


    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String guestFullName;

    private String guestEmail;

    private int NumOfAdults;

    private int NumOfChildren;

    private int totalNumOfGuest;

    private String bookingConfirmationCode;

    private Room room;

    public BookRoomResponse(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String bookingConfirmationCode) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public BookRoomResponse(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestFullName, String guestEmail, int numOfAdults, String bookingConfirmationCode, RoomResponse room) {
    }
}
