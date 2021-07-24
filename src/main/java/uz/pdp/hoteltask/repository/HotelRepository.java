package uz.pdp.hoteltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hoteltask.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
