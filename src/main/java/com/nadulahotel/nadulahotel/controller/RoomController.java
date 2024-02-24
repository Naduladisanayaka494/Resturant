package com.nadulahotel.nadulahotel.controller;

import com.nadulahotel.nadulahotel.exception.PhotoRetrievalException;
import com.nadulahotel.nadulahotel.model.BookRoom;
import com.nadulahotel.nadulahotel.model.Room;
import com.nadulahotel.nadulahotel.response.RoomResponse;
import com.nadulahotel.nadulahotel.service.BookingService;
import com.nadulahotel.nadulahotel.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomServiceImpl roomService;
    private final BookingService bookingService;

    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addNewRoom(@RequestParam("photo") MultipartFile photo, @RequestParam("roomType") String roomType, @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/room/types")
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        try {
            List<Room> rooms = roomService.getAllRooms();
            List<RoomResponse> roomResponses = new ArrayList<>();
            for (Room room : rooms) {
                byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
                if (photoBytes != null && photoBytes.length > 0) {
                    String base64Photo = Base64.encodeBase64String(photoBytes);
                    RoomResponse roomResponse = getRoomResponse(room, base64Photo);
                    roomResponses.add(roomResponse);
                }
            }
            return ResponseEntity.ok(roomResponses);
        } catch (SQLException | PhotoRetrievalException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private RoomResponse getRoomResponse(Room room, String base64Photo) throws PhotoRetrievalException {
        List<BookRoom> bookings = getAllBookingsByRoomId(room.getId());
        byte[] photoBytes = (base64Photo != null) ? Base64.decodeBase64(base64Photo) : null;
        return new RoomResponse(room.getId(), room.getRoomType(), room.getRoomPrice(), room.isBooked(), bookings, photoBytes);
    }

    private List<BookRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);
    }
}
