package com.nadulahotel.nadulahotel.response;

import com.nadulahotel.nadulahotel.model.BookRoom;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomResponse {

    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;

    private List<BookRoom> bookings;

    private String photo;

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, boolean isBooked, List<BookRoom> bookings, byte[] photoBytes) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.bookings = bookings;
        this.photo = (photoBytes != null) ? Base64.getEncoder().encodeToString(photoBytes) : null;
    }

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }
}
