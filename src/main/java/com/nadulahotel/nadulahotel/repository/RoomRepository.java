package com.nadulahotel.nadulahotel.repository;

import com.nadulahotel.nadulahotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // You can define custom query methods here if needed
}
