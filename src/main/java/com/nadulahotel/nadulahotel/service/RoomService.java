package com.nadulahotel.nadulahotel.service;

import com.nadulahotel.nadulahotel.model.Room;
import com.nadulahotel.nadulahotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService implements RoomServiceImpl{


 private  final RoomRepository roomRepository;
    @Override
    public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if(!photo.isEmpty()){
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
            room.setPhoto(photoBlob);

        }
        return  roomRepository.save(room);

    }


    public List<String> getAllRoomTypes(){
        return roomRepository.findDistinctRoomTypes();
    }

}
