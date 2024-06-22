package com.nadulahotel.nadulahotel.controller;

import com.nadulahotel.nadulahotel.exception.InvalidBookingRequestexception;
import com.nadulahotel.nadulahotel.exception.ResourceNotFoundException;
import com.nadulahotel.nadulahotel.exception.RoomNotFoundException;
import com.nadulahotel.nadulahotel.model.BookRoom;
import com.nadulahotel.nadulahotel.model.Room;
import com.nadulahotel.nadulahotel.response.BookRoomResponse;
import com.nadulahotel.nadulahotel.response.RoomResponse;
import com.nadulahotel.nadulahotel.service.IBookingService;
import com.nadulahotel.nadulahotel.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/bookings")
public class BookedRoomController {
    private final IBookingService bookingService;
    private final RoomServiceImpl roomService;
@GetMapping("/all-bookings")
    public ResponseEntity<List<BookRoomResponse>> getAllBookings() throws RoomNotFoundException {
        List<BookRoom>  bookings = bookingService.getAllBookings();
        List<BookRoomResponse> bookingResponses = new ArrayList<>();
        for(BookRoom booking:bookings){
            BookRoomResponse bookingResponse= getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return  ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        try {
            BookRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookRoomResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        } catch (ResourceNotFoundException | RoomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


@PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId, @RequestBody BookRoom  bookingRequest){
         try{
             String confirmationCode =bookingService.saveBooking(roomId,bookingRequest);
             return ResponseEntity.ok("Room Booked successfully! Your booking confirmation code is :"+confirmationCode);

         }catch(InvalidBookingRequestexception e){
             return ResponseEntity.badRequest().body(e.getMessage());

         }
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){

    bookingService.cancelBooking(bookingId);
    }


    private BookRoomResponse getBookingResponse(BookRoom booking) throws RoomNotFoundException {
        Optional<Room> optionalRoom = roomService.getRoomById(booking.getRoom().getId());

        if (!optionalRoom.isPresent()) {
            throw new RoomNotFoundException("Room with ID " + booking.getRoom().getId() + " not found");
        }

        Room theRoom = optionalRoom.get();
        RoomResponse room = new RoomResponse(theRoom.getId(), theRoom.getRoomType(), theRoom.getRoomPrice());
        return new BookRoomResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName(),
                booking.getGuestEmail(),
                booking.getNumOfAdults(),
                booking.getBookingConfirmationCode(),
                room
        );
    }

}
