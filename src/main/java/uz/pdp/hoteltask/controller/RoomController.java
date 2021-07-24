package uz.pdp.hoteltask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hoteltask.entity.Hotel;
import uz.pdp.hoteltask.entity.Room;
import uz.pdp.hoteltask.payload.RoomDto;
import uz.pdp.hoteltask.repository.HotelRepository;
import uz.pdp.hoteltask.repository.RoomRepository;

@RestController
@RequestMapping(value = "/room")
public class RoomController {
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomRepository roomRepository;

    @GetMapping("/{hotelId}")
    public Page<Room> get(@PathVariable Integer hotelId, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return roomRepository.findAllByHotelId(hotelId, pageable);
    }

    @PostMapping
    public String add(@RequestBody RoomDto dto) {
        Integer hotelId = dto.getHotelId();
        if (!hotelRepository.existsById(hotelId))
            return "Hotel not found";

        Integer floor = dto.getFloor();
        String number = dto.getNumber();
        Double size = dto.getSize();
        Hotel hotel = hotelRepository.getById(hotelId);

        roomRepository.save(Room.builder().floor(floor).number(number).size(size).hotel(hotel).build());
        return "Room added";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody RoomDto dto){
        if (!roomRepository.existsById(id))
            return "Room not found";
        Integer hotelId = dto.getHotelId();
        if (!hotelRepository.existsById(hotelId))
            return "Hotel not found";

        Room editingRoom = roomRepository.getById(id);

        Integer floor = dto.getFloor();
        String number = dto.getNumber();
        Double size = dto.getSize();
        Hotel hotel = hotelRepository.getById(hotelId);

        editingRoom.setFloor(floor);
        editingRoom.setHotel(hotel);
        editingRoom.setSize(size);
        editingRoom.setNumber(number);

        return "Room updated";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        if (!roomRepository.existsById(id))
            return "Room not found";

        roomRepository.deleteById(id);
        return "Room deleted";
    }

}
