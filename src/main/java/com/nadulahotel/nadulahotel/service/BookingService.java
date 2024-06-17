package com.nadulahotel.nadulahotel.service;

import com.nadulahotel.nadulahotel.exception.InvalidBookingRequestexception;
import com.nadulahotel.nadulahotel.model.BookRoom;
import com.nadulahotel.nadulahotel.model.Room;
import com.nadulahotel.nadulahotel.repository.BookedRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{
    private final BookedRoomRepository bookedRoomRepository;
    private final RoomServiceImpl roomservice;




    public List<BookRoom> getAllBookingsByRoomId(Long roomId) {

        return bookedRoomRepository.findByRoomId(roomId);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookedRoomRepository.deleteById(bookingId);
    }

    @Override
    public String saveBooking(Long roomId, BookRoom bookingRequest) {
        if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestexception("Check-in date must come before check out date");
        }

        Room room= roomservice.getRoomById(roomId).get();
        List<BookRoom> existingBookings= room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest , existingBookings);
        if( roomIsAvailable){
            room.addBooking(bookingRequest);
            bookedRoomRepository.save(bookingRequest);
        }else{
            throw new InvalidBookingRequestexception("sorry, this room is not available for the selected  dates");
        }
        return bookingRequest.getBookingConfirmationCode();

    }



    @Override
    public BookRoom findByBooingConfirmationCode(String confirmationCode) {
        return bookedRoomRepository.findByBookingConfirmationCode(confirmationCode);
    }

    private boolean roomIsAvailable(BookRoom bookingRequest, List<BookRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()) &&
                                bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate())
                );
    }

    @Override
    public List<BookRoom> getAllBookings() {

        return bookedRoomRepository.findAll();
    }

    @Override
    public BookRoom findByBookingConfirmationCode(String confirmationCode) {
        return null;
    }
}
