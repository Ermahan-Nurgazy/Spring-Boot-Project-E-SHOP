package Hometask7.DB;

import java.util.ArrayList;

public class DBManager {
    private static ArrayList<ShopItem> shopItems = new ArrayList<>();

    static {
        shopItems.add(new ShopItem(1L,"iPhone 12 Pro Max", "iPhone 12 Pro Max, 512GB, 5G Network, Display 6.7, Frequency 6 GHZ",2499, 50, 5,"https://www.edmtunes.com/wp-content/uploads/2020/04/3oqmv4bAtKt3uJFdPzwxch-1200-80.jpeg"));
        shopItems.add(new ShopItem(2L,"iPhone 12 Pro Max", "iPhone 12 Pro Max, 512GB, 5G Network, Display 6.7, Frequency 6 GHZ",2499, 50, 5,"https://www.edmtunes.com/wp-content/uploads/2020/04/3oqmv4bAtKt3uJFdPzwxch-1200-80.jpeg"));
        shopItems.add(new ShopItem(3L,"iPhone 12 Pro Max", "iPhone 12 Pro Max, 512GB, 5G Network, Display 6.7, Frequency 6 GHZ",2499, 50, 5,"https://www.edmtunes.com/wp-content/uploads/2020/04/3oqmv4bAtKt3uJFdPzwxch-1200-80.jpeg"));
    }

    private static Long id = 4L;

    public static ArrayList<ShopItem> getShopItems(){return shopItems;}

    public void addItem(ShopItem shopItem){
        shopItem.setId(id);
        shopItems.add(shopItem);
        id++;
    }

    public static ShopItem getShopItem(Long id){
        for (ShopItem it : shopItems){
            if (it.getId() == id){
                return it;
            }
        }
        return null;
    }

    public static void deleteItem(Long id){
        shopItems.removeIf(it -> it.getId().equals(id));
    }
}
