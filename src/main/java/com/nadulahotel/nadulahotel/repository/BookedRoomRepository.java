package com.nadulahotel.nadulahotel.repository;

import com.nadulahotel.nadulahotel.model.BookRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedRoomRepository extends JpaRepository<BookRoom, Long> {




    List<BookRoom> findByRoomId(Long roomId);

    BookRoom findByBookingConfirmationCode(String confirmationCode);
}
