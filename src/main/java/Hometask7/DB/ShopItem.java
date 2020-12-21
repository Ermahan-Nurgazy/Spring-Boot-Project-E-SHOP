package Hometask7.DB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopItem {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int amouunt;
    private int stars;
    private String url;
}
