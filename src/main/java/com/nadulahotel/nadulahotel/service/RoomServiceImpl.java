package com.nadulahotel.nadulahotel.service;

import com.nadulahotel.nadulahotel.exception.InternalServerException;
import com.nadulahotel.nadulahotel.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoomServiceImpl  {

   Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException;

   List<String> getAllRoomTypes();

   List<Room> getAllRooms();

   byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

   void deleteRoom(Long roomId);

   Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoByetes) throws InternalServerException;

   Optional<Room> getRoomById(Long roomId);
}
