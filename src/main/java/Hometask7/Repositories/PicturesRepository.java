package Hometask7.Repositories;


import Hometask7.Entities.Pictures;
import Hometask7.Entities.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PicturesRepository extends JpaRepository<Pictures, Long> {
    List<Pictures> findByShopItem(ShopItems shopItems);
    Pictures getByShopItem(ShopItems shopItem);
}
