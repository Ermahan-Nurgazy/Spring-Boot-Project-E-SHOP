package Hometask7.Services.impl;

import Hometask7.Entities.*;
import Hometask7.Repositories.*;
import Hometask7.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ShopItemsRepository shopItemsRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private BrandsRepository brandsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PicturesRepository picturesRepository;

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public List<ShopItems> getAllItems() {
        return shopItemsRepository.findAll();
    }

    @Override
    public ShopItems addItem(ShopItems shopItems) {
        return shopItemsRepository.save(shopItems);
    }

    @Override
    public ShopItems saveItem(ShopItems shopItems) {
        return shopItemsRepository.save(shopItems);
    }

    @Override
    public ShopItems getItem(Long id) {
        return shopItemsRepository.getOne(id);
    }

    @Override
    public List<ShopItems> getAllItemsByName(String name) {
        return shopItemsRepository.findAllByNameStartsWith(name);
    }

    @Override
    public List<ShopItems> getAllItemsByFilter(String name, Brands brands, double priceFrom, double priceTo) {
        return shopItemsRepository.findAllByNameStartsWithAndBrandAndPriceBetween(name,brands,priceFrom,priceTo);
    }

    @Override
    public List<ShopItems> getAllItemsByBrands(Brands brands) {
        return shopItemsRepository.findAllByBrand(brands);
    }

    @Override
    public List<ShopItems> getAllItemsByCategory(Categories categories) {
        return shopItemsRepository.findAllByCategories(categories);
    }

    @Override
    public void deleteItem(ShopItems shopItems) {
        shopItemsRepository.delete(shopItems);
    }

    @Override
    public List<Countries> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Countries addCountry(Countries countries) {
        return countryRepository.save(countries);
    }

    @Override
    public Countries saveCountry(Countries countries) {
        return countryRepository.save(countries);
    }

    @Override
    public Countries getCountry(Long id) {
        return countryRepository.getOne(id);
    }

    @Override
    public void deleteCountry(Countries countries) {
        countryRepository.delete(countries);
    }

    @Override
    public List<Brands> getAllBrands() {
        return brandsRepository.findAll();
    }

    @Override
    public Brands addBrand(Brands brands) {
        return brandsRepository.save(brands);
    }

    @Override
    public Brands saveBrand(Brands brands) {
        return brandsRepository.save(brands);
    }

    @Override
    public Brands getBrand(Long id) {
        return brandsRepository.getOne(id);
    }

    @Override
    public void deleteBrand(Brands brands) {
        brandsRepository.delete(brands);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users addUser(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public Users getUser(Long id) {
        return usersRepository.getOne(id);
    }

    @Override
    public void deleteUser(Users user) {
        usersRepository.delete(user);
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories addCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    @Override
    public Categories saveCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    @Override
    public Categories getCategory(Long id) {
        return categoriesRepository.getOne(id);
    }

    @Override
    public void deleteCategory(Categories category) {
        categoriesRepository.delete(category);
    }

    @Override
    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Roles addRole(Roles role) {
        return roleRepository.save(role);
    }

    @Override
    public Roles saveRole(Roles role) {
        return roleRepository.save(role);
    }

    @Override
    public Roles getRole(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public void deleteRole(Roles role) {
        roleRepository.delete(role);
    }

    @Override
    public List<Pictures> getAllPictures() {
        return picturesRepository.findAll();
    }

    @Override
    public Pictures addPicture(Pictures picture) {
        return picturesRepository.save(picture);
    }

    @Override
    public Pictures savePicture(Pictures picture) {
        return picturesRepository.save(picture);
    }

    @Override
    public Pictures getPicture(Long id) {
        return picturesRepository.getOne(id);
    }

    @Override
    public List<Pictures> getAllPicturesByShopItems(ShopItems shopItems) {
        return picturesRepository.findByShopItem(shopItems);
    }


    @Override
    public Pictures getPictureByShopItem(ShopItems shopItem) {
        return picturesRepository.getByShopItem(shopItem);
    }

    @Override
    public void deletePicture(Pictures picture) {
        picturesRepository.delete(picture);
    }

    @Override
    public List<Payments> getAllPayments() {
        return paymentsRepository.findAll();
    }

    @Override
    public Payments addPayment(Payments payment) {
        return paymentsRepository.save(payment);
    }

    @Override
    public Payments savePayment(Payments payment) {
        return paymentsRepository.save(payment);
    }

    @Override
    public Payments getPayment(Long id) {
        return paymentsRepository.getOne(id);
    }

    @Override
    public void deletePayments(Payments payment) {
        paymentsRepository.delete(payment);
    }

    @Override
    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    @Override
    public Comments addComment(Comments comment) {
        return commentsRepository.save(comment);
    }

    @Override
    public Comments saveComment(Comments comment) {
        return commentsRepository.save(comment);
    }

    @Override
    public Comments getComment(Long id) {
        return commentsRepository.getOne(id);
    }

    @Override
    public void deleteComments(Comments comment) {
        commentsRepository.delete(comment);
    }
}
