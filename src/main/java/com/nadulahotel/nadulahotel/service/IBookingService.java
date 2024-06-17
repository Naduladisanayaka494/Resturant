package com.nadulahotel.nadulahotel.service;

import com.nadulahotel.nadulahotel.model.BookRoom;

import java.util.List;

public interface IBookingService {
    void cancelBooking(Long bookingId);

    String saveBooking(Long roomId, BookRoom bookingRequest);

    BookRoom findByBooingConfirmationCode(String confirmationCode);

    List<BookRoom> getAllBookings();

    BookRoom findByBookingConfirmationCode(String confirmationCode);
}
