package Hometask7.Repositories;

import Hometask7.Entities.Brands;
import Hometask7.Entities.Categories;
import Hometask7.Entities.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ShopItemsRepository extends JpaRepository<ShopItems, Long> {
    List<ShopItems> findAllByNameStartsWith(String name);
    List<ShopItems> findAllByNameStartsWithAndBrandAndPriceBetween(String name, Brands brands, double priceFrom, double priceTo);
    List<ShopItems> findAllByBrand(Brands brands);
    List<ShopItems> findAllByCategories(Categories categories);
}
