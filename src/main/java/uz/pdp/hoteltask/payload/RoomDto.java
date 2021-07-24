package uz.pdp.hoteltask.payload;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private String number;

    private Integer floor;

    private Double size;

    private Integer hotelId;
}
