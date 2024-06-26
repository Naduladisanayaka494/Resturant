package com.nadulahotel.nadulahotel.service;

import com.nadulahotel.nadulahotel.exception.InternalServerException;
import com.nadulahotel.nadulahotel.exception.ResourceNotFoundException;
import com.nadulahotel.nadulahotel.model.Room;
import com.nadulahotel.nadulahotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService implements RoomServiceImpl {


    private final RoomRepository roomRepository;

    @Override
    public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!photo.isEmpty()) {
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
            room.setPhoto(photoBlob);

        }
        return roomRepository.save(room);

    }


    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<Room> theRoom = roomRepository.findById(roomId);

        if (theRoom.isEmpty()) {
            throw new ResourceNotFoundException("Sorry,Room not found");
        }
        Blob photoBlob = theRoom.get().getPhoto();
        if (photoBlob != null) {
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;

    }

    @Override
    public void deleteRoom(Long roomId) {
        Optional<Room> theRoom = roomRepository.findById(roomId);
        if(theRoom.isPresent()){
            roomRepository.deleteById(roomId);
        }

    }

    @Override
    public Room updateRoom(Long roomId, String roomType,BigDecimal roomPrice, byte[] photoByetes) throws InternalServerException {
        Room room= roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        if(roomType !=null) room.setRoomType((roomType));
        if(roomPrice!=null) room.setRoomPrice(roomPrice);
        if(photoByetes!=null && photoByetes.length>0){
                       try{
                           room.setPhoto(new SerialBlob(photoByetes));

                       } catch (SerialException e) {
                           throw new RuntimeException(e);
                       } catch (SQLException e) {
                           throw new InternalServerException("Error updating room");
                       }
        }

        return roomRepository.save(room);
    }

    @Override

    public Optional<Room> getRoomById(Long roomId) {
        return Optional.of(roomRepository.findById(roomId).get());
    }
}
