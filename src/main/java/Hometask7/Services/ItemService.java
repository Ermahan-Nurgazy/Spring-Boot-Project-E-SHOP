package Hometask7.Services;

import Hometask7.Entities.*;

import java.util.List;

public interface ItemService {

    List<ShopItems> getAllItems();
    ShopItems addItem(ShopItems shopItems);
    ShopItems saveItem(ShopItems shopItems);
    ShopItems getItem(Long id);
    List<ShopItems> getAllItemsByName(String name);
    List<ShopItems> getAllItemsByFilter(String name, Brands brands, double priceFrom, double priceTo);
    List<ShopItems> getAllItemsByBrands(Brands brands);
    List<ShopItems> getAllItemsByCategory(Categories categories);
    void deleteItem(ShopItems shopItems);

    List<Countries> getAllCountries();
    Countries addCountry(Countries countries);
    Countries saveCountry(Countries countries);
    Countries getCountry(Long id);
    void deleteCountry(Countries countries);

    List<Brands> getAllBrands();
    Brands addBrand(Brands brands);
    Brands saveBrand(Brands brands);
    Brands getBrand(Long id);
    void deleteBrand(Brands brands);

    List<Users> getAllUsers();
    Users addUser(Users user);
    Users saveUser(Users user);
    Users getUser(Long id);
    void deleteUser(Users user);

    List<Categories> getAllCategories();
    Categories addCategory(Categories category);
    Categories saveCategory(Categories category);
    Categories getCategory(Long id);
    void deleteCategory(Categories category);

    List<Roles> getAllRoles();
    Roles addRole(Roles role);
    Roles saveRole(Roles role);
    Roles getRole(Long id);
    void deleteRole(Roles role);

    List<Pictures> getAllPictures();
    Pictures addPicture(Pictures picture);
    Pictures savePicture(Pictures picture);
    Pictures getPicture(Long id);
    List<Pictures> getAllPicturesByShopItems(ShopItems shopItem);
    Pictures getPictureByShopItem(ShopItems shopItem);
    void deletePicture(Pictures picture);

    List<Payments> getAllPayments();
    Payments addPayment(Payments payment);
    Payments savePayment(Payments payment);
    Payments getPayment(Long id);
    void deletePayments(Payments payment);

    List<Comments> getAllComments();
    Comments addComment(Comments comment);
    Comments saveComment(Comments comment);
    Comments getComment(Long id);
    void deleteComments(Comments comment);
}
