package uz.pdp.hoteltask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hoteltask.entity.Hotel;
import uz.pdp.hoteltask.repository.HotelRepository;
import uz.pdp.hoteltask.repository.RoomRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomRepository roomRepository;

    @GetMapping
    public List<Hotel> get() {
        return hotelRepository.findAll();
    }

    @PostMapping
    public String add(@RequestBody Hotel hotel) {
        if (hotelRepository.existsByName(hotel.getName()))
            return "This hotel already existed";

        hotelRepository.save(Hotel.builder().name(hotel.getName()).build());
        return "Hotel added";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody Hotel hotel){
        if (!hotelRepository.existsById(id))
            return "Hotel not found";

        if (hotelRepository.existsByNameAndIdNot(hotel.getName(),id))
            return "This hotel already existed";

        Hotel editingHotel = hotelRepository.getById(id);
        editingHotel.setName(hotel.getName());

        hotelRepository.save(editingHotel);
        return "Hotel edited";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        if (!hotelRepository.existsById(id))
            return "Hotel not found";

        roomRepository.deleteAllByHotelId(id);
        hotelRepository.deleteById(id);
        return "Hotel deleted";
    }
}
