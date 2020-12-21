package Hometask7.Controllers;

import Hometask7.DB.DBManager;
import Hometask7.DB.ShopItem;
import Hometask7.Entities.*;
import Hometask7.Services.ItemService;
import Hometask7.Services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Value("${file.avatar.viewPath}")
    private String viewPath;

    @Value("${file.avatar.uploadPath}")
    private String uploadPath;

    @Value("${file.avatar.defaultPicture}")
    private String defaultPicture;

    @Value("${file.picture.viewPath}")
    private String viewPathPicture;

    @Value("${file.picture.uploadPath}")
    private String uploadPathPicture;

    @Value("${file.picture.defaultPicture}")
    private String defaultItemPicture;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request){
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        ArrayList<Integer> amount = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())) {
                    amount.add(Integer.parseInt(c.getValue()));
                }
            }
        }
        int total_amount = 0;
        for (int i = 0; i < amount.size(); i++){
            total_amount += amount.get(i);
        }
        model.addAttribute("total_amount",total_amount);
        return "index";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(Model model){
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        List<Payments> payments = itemService.getAllPayments();
        model.addAttribute("payments",payments);
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "admin";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String moderator(Model model){
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("brands",brands);
        model.addAttribute("curUser",getUserData());
        return "moderator";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
        List<Brands> brands = itemService.getAllBrands();
        List<Countries> countries = itemService.getAllCountries();
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("categories",categories);
        ArrayList<Integer> amount = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())) {
                    amount.add(Integer.parseInt(c.getValue()));
                }
            }
        }
        int total_amount = 0;
        for (int i = 0; i < amount.size(); i++){
            total_amount += amount.get(i);
        }
        model.addAttribute("total_amount",total_amount);
        return "login";
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public String logout(Model model){
        List<Brands> brands = itemService.getAllBrands();
        List<Countries> countries = itemService.getAllCountries();
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("categories",categories);
        return "index";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable(name = "id") Long id, HttpServletRequest request){
        ShopItems item = itemService.getItem(id);
        model.addAttribute("item",item);
        List<Brands> brands = itemService.getAllBrands();
        List<Countries> countries = itemService.getAllCountries();
        List<Categories> categories = itemService.getAllCategories();
        List<Pictures> pictures = itemService.getAllPicturesByShopItems(item);
        List<Comments> comments = itemService.getAllComments();
        model.addAttribute("comments",comments);
        model.addAttribute("pictures",pictures);
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        ArrayList<Integer> amount = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())) {
                    amount.add(Integer.parseInt(c.getValue()));
                }
            }
        }
        int total_amount = 0;
        for (int i = 0; i < amount.size(); i++){
            total_amount += amount.get(i);
        }
        model.addAttribute("total_amount",total_amount);
        return "details";
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String AddUser(Model model, @RequestParam(name = "email") String email,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "fullName") String fullName){
        Users users = new Users();
        users.setEmail(email);
        users.setPassword(password);
        users.setFullName(fullName);
        itemService.addUser(users);
        List<Users> listUsers = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", listUsers);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/editUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editUser(Model model, @RequestParam(name = "user_id") Long id,
                          @RequestParam(name = "user_email") String email,
                          @RequestParam(name = "user_password") String password,
                          @RequestParam(name = "user_fullName") String fullName){
        Users users = itemService.getUser(id);
        users.setEmail(email);
        users.setPassword(password);
        users.setFullName(fullName);
        itemService.saveUser(users);
        List<Users> listUsers = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", listUsers);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(Model model, @RequestParam(name = "user_id") Long id,
                           @RequestParam(name = "email") String email,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "fullName") String fullName){
        Users users = itemService.getUser(id);
        users.setEmail(email);
        users.setPassword(password);
        users.setFullName(fullName);
        itemService.saveUser(users);
        List<Brands> brands = itemService.getAllBrands();
        List<Countries> countries = itemService.getAllCountries();
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "redirect:/";
    }

    @PostMapping(value = "/updatePassword")
    public String updatePassword(@RequestParam(name = "oldPassword") String oldPassword,
                                 @RequestParam(name = "id") Long id,
                                 @RequestParam(name = "password") String password,
                                 @RequestParam(name = "rePassword") String rePassword) {
        Users checkUser = userService.getUserById(id);
        if (checkUser != null) {
            if (password.equals(rePassword)) {
                if(userService.updatePassword(checkUser,password,oldPassword,false)){
                    return "redirect:/profile?success";
                }
            }
        }
        return "redirect:/profile?error";
    }

    @PostMapping("/uploadAvatar")
    @PreAuthorize("isAuthenticated()")
    public String uploadAvatar(@RequestParam(name = "user_avatar") MultipartFile file){
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
            try {
                Users curUser = getUserData();
                String picName = DigestUtils.sha1Hex("profile_picture_" + curUser.getId());
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadPath+picName+".jpg");
                Files.write(path, bytes);

                curUser.setPictureURL(picName);
                userService.saveUser(curUser);

                return "redirect:/profile?success";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }

    @GetMapping(value = "/viewPhoto/{url}",produces = {MediaType.IMAGE_JPEG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] viewProfilePhoto(@PathVariable(name = "url") String url) throws IOException {
        String pictureUrl = viewPath + defaultPicture;
        if (url!=null&&!url.equals("null")){
            pictureUrl=viewPath+url+".jpg";
        }
        InputStream in;
        try {
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();

        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(viewPath+defaultPicture);
            in = resource.getInputStream();
            e.printStackTrace();
        }
        return IOUtils.toByteArray(in);
    }

    @PostMapping("/deleteUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(Model model, @RequestParam(name = "user_id") Long id){
        Users users = itemService.getUser(id);
        itemService.deleteUser(users);
        List<Users> listUsers = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", listUsers);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/addCountry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String AddCountry(Model model, @RequestParam(name = "name") String name,
                          @RequestParam(name = "code") String code){
        Countries country = new Countries();
        country.setName(name);
        country.setCode(code);
        itemService.addCountry(country);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/editCountry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCountry(Model model, @RequestParam(name = "country_id") Long id,
                              @RequestParam(name = "country_name") String name,
                              @RequestParam(name = "country_code") String code){
        Countries country = itemService.getCountry(id);
        country.setName(name);
        country.setCode(code);
        itemService.saveCountry(country);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/deleteCountry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCountry(Model model, @RequestParam(name = "country_id") Long id){
        Countries country = itemService.getCountry(id);
        itemService.deleteCountry(country);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/addBrand")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String AddBrand(Model model, @RequestParam(name = "name") String name,
                          @RequestParam(name = "country") Countries country){
        Brands brand = new Brands();
        brand.setName(name);
        brand.setCountry(country);
        itemService.addBrand(brand);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/editBrand")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editBrand(Model model, @RequestParam(name = "brand_id") Long id,
                              @RequestParam(name = "brand_name") String name,
                              @RequestParam(name = "brand_country") Countries country){
        Brands brand = itemService.getBrand(id);
        brand.setName(name);
        brand.setCountry(country);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/deleteBrand")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBrand(Model model, @RequestParam(name = "brand_id") Long id){
        Brands brand = itemService.getBrand(id);
        itemService.deleteBrand(brand);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/addCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String AddCategory( Model model, @RequestParam(name = "category_name") String name,
                              @RequestParam(name = "logo") String logoURL){
        Categories category = new Categories();
        category.setName(name);
        category.setLogoURL(logoURL);
        itemService.addCategory(category);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/editCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCategory( Model model, @RequestParam(name = "category_id") Long id,
                            @RequestParam(name = "category_name") String name,
                            @RequestParam(name = "logo") String logoURL){
        Categories category = itemService.getCategory(id);
        category.setName(name);
        category.setLogoURL(logoURL);
        itemService.saveCategory(category);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/deleteCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCategory(Model model, @RequestParam(name = "category_id") Long id){
        Categories category = itemService.getCategory(id);
        itemService.deleteCategory(category);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/addItem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String AddItem(Model model, @RequestParam(name = "name") String name,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "price") double price,
                          @RequestParam(name = "stars") int stars,
                          @RequestParam(name = "smallPicURL") String smallPicURL,
                          @RequestParam(name = "largePicURL") String largePicURL,
                          @RequestParam(name = "addedDate") String addedDate,
                          @RequestParam(name = "inTopPage") boolean inTopPage,
                          @RequestParam(name = "brand") Brands brand){
        ShopItems shopItems = new ShopItems();
        shopItems.setName(name);
        shopItems.setDescription(description);
        shopItems.setPrice(price);
        shopItems.setStars(stars);
        shopItems.setSmallPicURL(smallPicURL);
        shopItems.setLargePicURL(largePicURL);
        shopItems.setAddedDate(addedDate);
        shopItems.setInTopPage(inTopPage);
        shopItems.setBrand(brand);
        itemService.addItem(shopItems);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItem = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItem);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/addItemByModerator")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public String AddItemByModerator(Model model, @RequestParam(name = "name") String name,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "price") double price,
                          @RequestParam(name = "stars") int stars,
                          @RequestParam(name = "smallPicURL") String smallPicURL,
                          @RequestParam(name = "largePicURL") String largePicURL,
                          @RequestParam(name = "addedDate") String addedDate,
                          @RequestParam(name = "inTopPage") boolean inTopPage,
                          @RequestParam(name = "brand") Brands brand){
        ShopItems shopItems = new ShopItems();
        shopItems.setName(name);
        shopItems.setDescription(description);
        shopItems.setPrice(price);
        shopItems.setStars(stars);
        shopItems.setSmallPicURL(smallPicURL);
        shopItems.setLargePicURL(largePicURL);
        shopItems.setAddedDate(addedDate);
        shopItems.setInTopPage(inTopPage);
        shopItems.setBrand(brand);
        itemService.addItem(shopItems);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItem = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItem);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/moderator";
    }

    @PostMapping("/editItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String editItem(Model model, @RequestParam(name = "item_id") Long id,
                          @RequestParam(name = "item_name") String name,
                          @RequestParam(name = "item_description") String description,
                          @RequestParam(name = "item_price") double price,
                          @RequestParam(name = "item_stars") int stars,
                          @RequestParam(name = "item_smPicUrl") String smallPicURL,
                          @RequestParam(name = "item_lgPicUrl") String largePicURL,
                          @RequestParam(name = "item_date") String addedDate,
                          @RequestParam(name = "item_inTopPage") boolean inTopPage,
                          @RequestParam(name = "brand") Brands brand){
        ShopItems shopItems = itemService.getItem(id);
        shopItems.setName(name);
        shopItems.setDescription(description);
        shopItems.setPrice(price);
        shopItems.setStars(stars);
        shopItems.setSmallPicURL(smallPicURL);
        shopItems.setLargePicURL(largePicURL);
        shopItems.setAddedDate(addedDate);
        shopItems.setInTopPage(inTopPage);
        shopItems.setBrand(brand);
        itemService.saveItem(shopItems);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItem = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItem);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/editItemByModerator")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public String editItemByModerator(Model model, @RequestParam(name = "item_id") Long id,
                           @RequestParam(name = "item_name") String name,
                           @RequestParam(name = "item_description") String description,
                           @RequestParam(name = "item_price") double price,
                           @RequestParam(name = "item_stars") int stars,
                           @RequestParam(name = "item_smPicUrl") String smallPicURL,
                           @RequestParam(name = "item_lgPicUrl") String largePicURL,
                           @RequestParam(name = "item_date") String addedDate,
                           @RequestParam(name = "item_inTopPage") boolean inTopPage,
                           @RequestParam(name = "brand") Brands brand){
        ShopItems shopItems = itemService.getItem(id);
        shopItems.setName(name);
        shopItems.setDescription(description);
        shopItems.setPrice(price);
        shopItems.setStars(stars);
        shopItems.setSmallPicURL(smallPicURL);
        shopItems.setLargePicURL(largePicURL);
        shopItems.setAddedDate(addedDate);
        shopItems.setInTopPage(inTopPage);
        shopItems.setBrand(brand);
        itemService.saveItem(shopItems);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItem = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItem);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/deleteItem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteItem(Model model, @RequestParam(name = "item_id") Long id){
        ShopItems shopItem = itemService.getItem(id);
        itemService.deleteItem(shopItem);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/admin";
    }

    @PostMapping("/deleteItemByModerator")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public String deleteItemByModerator(Model model, @RequestParam(name = "item_id") Long id){
        ShopItems shopItem = itemService.getItem(id);
        itemService.deleteItem(shopItem);
        List<Users> users = itemService.getAllUsers();
        List<ShopItems> shopItems = itemService.getAllItems();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("shopItems",shopItems);
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        model.addAttribute("users", users);
        List<Roles> roles = itemService.getAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "/moderator";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam(name = "search") String search, HttpServletRequest request){
        List<ShopItems> shopItems = itemService.getAllItemsByName(search);
        model.addAttribute("shopItems",shopItems);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries",countries);
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        ArrayList<Integer> amount = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())) {
                    amount.add(Integer.parseInt(c.getValue()));
                }
            }
        }
        int total_amount = 0;
        for (int i = 0; i < amount.size(); i++){
            total_amount += amount.get(i);
        }
        model.addAttribute("total_amount",total_amount);
        return "/search";
    }

    @PostMapping("/filter")
    public String search(Model model, @RequestParam(name = "name") String name,
                         @RequestParam(name = "brand") Brands brand,
                         @RequestParam(name = "priceFrom") double priceFrom,
                         @RequestParam(name = "priceTo") double priceTo,
                         HttpServletRequest request){
        List<ShopItems> shopItems = itemService.getAllItemsByFilter(name,brand,priceFrom,priceTo);
        model.addAttribute("shopItems",shopItems);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries",countries);
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        ArrayList<Integer> amount = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())) {
                    amount.add(Integer.parseInt(c.getValue()));
                }
            }
        }
        int total_amount = 0;
        for (int i = 0; i < amount.size(); i++){
            total_amount += amount.get(i);
        }
        model.addAttribute("total_amount",total_amount);
        return "/search";
    }

    @GetMapping("/brands/{id}")
    public String brands(Model model, @PathVariable(name = "id") Long id, HttpServletRequest request){
        List<ShopItems> shopItems = itemService.getAllItemsByBrands(itemService.getBrand(id));
        model.addAttribute("shopItems",shopItems);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries",countries);
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        ArrayList<Integer> amount = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())) {
                    amount.add(Integer.parseInt(c.getValue()));
                }
            }
        }
        int total_amount = 0;
        for (int i = 0; i < amount.size(); i++){
            total_amount += amount.get(i);
        }
        model.addAttribute("total_amount",total_amount);
        return "index";
    }

    @GetMapping("/category/{id}")
    public String category(Model model, @PathVariable(name = "id") Long id, HttpServletRequest request){
        List<ShopItems> shopItems = itemService.getAllItemsByCategory(itemService.getCategory(id));
        model.addAttribute("shopItems",shopItems);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries",countries);
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        ArrayList<Integer> amount = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())) {
                    amount.add(Integer.parseInt(c.getValue()));
                }
            }
        }
        int total_amount = 0;
        for (int i = 0; i < amount.size(); i++){
            total_amount += amount.get(i);
        }
        model.addAttribute("total_amount",total_amount);
        return "index";
    }

    @GetMapping("/detailsItem/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String detailsItem(Model model, @PathVariable(name = "id") Long id){
        ShopItems item = itemService.getItem(id);
        model.addAttribute("item",item);
        List<Brands> brands = itemService.getAllBrands();
        List<Countries> countries = itemService.getAllCountries();
        List<Categories> categories = itemService.getAllCategories();
        categories.removeAll(item.getCategories());
        List<Pictures> pictures = itemService.getAllPicturesByShopItems(item);
        model.addAttribute("pictures",pictures);
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "detailsItem";
    }

    @GetMapping("/usersDetail/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String usersDetail(Model model, @PathVariable(name = "id") Long id){
        Users user = itemService.getUser(id);
        model.addAttribute("user",user);
        List<Roles> roles = itemService.getAllRoles();
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories",categories);
        model.addAttribute("roles",roles);
        model.addAttribute("curUser",getUserData());
        return "usersDetail";
    }

    @GetMapping("/detailsItemM/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public String detailsItemM(Model model, @PathVariable(name = "id") Long id){
        ShopItems item = itemService.getItem(id);
        model.addAttribute("item",item);
        List<Brands> brands = itemService.getAllBrands();
        List<Countries> countries = itemService.getAllCountries();
        List<Categories> categories = itemService.getAllCategories();
        categories.removeAll(item.getCategories());
        List<Pictures> pictures = itemService.getAllPicturesByShopItems(item);
        model.addAttribute("pictures",pictures);
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        return "detailsItemM";
    }

    @PostMapping("/assignCategory")
    public String assignCategory(@RequestParam(name = "item_id") Long itemId,
                                 @RequestParam(name = "category_id") Long categoryId){
        Categories cat = itemService.getCategory(categoryId);
        if (cat!=null){
            ShopItems item = itemService.getItem(itemId);
            if (item!=null){
                List<Categories> categories = item.getCategories();
                if (categories==null){
                    categories = new ArrayList<>();
                }
                categories.add(cat);
                itemService.saveItem(item);
                return "redirect:/detailsItem/"+itemId + "#categoryDiv";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/unassignCategory")
    public String unassignCategory(@RequestParam(name = "item_id") Long itemId,
                                 @RequestParam(name = "category_id") Long categoryId){
        Categories cat = itemService.getCategory(categoryId);
        if (cat!=null){
            ShopItems item = itemService.getItem(itemId);
            if (item!=null){
                List<Categories> categories = item.getCategories();
                if (categories==null){
                    categories = new ArrayList<>();
                }
                categories.remove(cat);
                itemService.saveItem(item);
                return "redirect:/detailsItem/"+itemId;
            }
        }
        return "redirect:/";
    }

    @PostMapping("/assignPicture")
    public String assignPicture(Model model, @RequestParam(name = "item_id") Long itemId,
                                @RequestParam(name = "item_picture") MultipartFile file){
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
            try {
                List<Pictures> pictures = itemService.getAllPictures();
                ShopItems shopItem = itemService.getItem(itemId);
                Long picId;
                if (pictures.isEmpty())
                    picId = 0L;
                else
                    picId = pictures.get(pictures.size()-1).getId()+1;
                String picName = DigestUtils.sha1Hex("item_" + shopItem.getId() + "_pictures_" + picId);
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadPathPicture+picName+".jpg");
                Files.write(path, bytes);
                Pictures picture = new Pictures();
                picture.setShopItem(shopItem);
                picture.setAddedDate(new Date(Calendar.getInstance().getTimeInMillis()));
                picture.setUrl(picName);
                picture.setShopItem(shopItem);
                itemService.savePicture(picture);
                model.addAttribute("pictures",pictures);
                model.addAttribute("item",shopItem);
                return "redirect:/detailsItem/"+itemId + "#pictureTable";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }

    @PostMapping("/unassignPicture")
    public String unassignPicture(Model model,@RequestParam(name = "item_id") Long itemId,
                                   @RequestParam(name = "picture_id") Long pictureId){
        Pictures picture = itemService.getPicture(pictureId);
        if (picture!=null){
            ShopItems item = itemService.getItem(itemId);
            if (item!=null){
                List<Pictures> pictures = itemService.getAllPicturesByShopItems(item);
                if (pictures==null){
                    pictures = new ArrayList<>();
                }
                pictures.remove(picture);
                itemService.saveItem(item);
                itemService.deletePicture(picture);
                model.addAttribute("pictures",pictures);
                model.addAttribute("item",item);
                return "redirect:/detailsItem/"+itemId;
            }
        }
        return "redirect:/";
    }

    @GetMapping(value = "/viewPicture/{pic_url}",produces = {MediaType.IMAGE_JPEG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] viewItemPicture(@PathVariable(name = "pic_url") String url) throws IOException {
        String pictureUrl = viewPathPicture + defaultItemPicture;
        if (url!=null&&!url.equals("null")){
            pictureUrl=viewPathPicture+url+".jpg";
        }
        InputStream in;
        try {
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();

        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(viewPathPicture+defaultItemPicture);
            in = resource.getInputStream();
            e.printStackTrace();
        }
        return IOUtils.toByteArray(in);
    }

    @PostMapping("/assignRole")
    public String assignRole(@RequestParam(name = "user_id") Long userId,
                                 @RequestParam(name = "role_id") Long roleId){
        Roles r = itemService.getRole(roleId);
        if (r!=null){
            Users user = itemService.getUser(userId);
            if (user!=null){
                List<Roles> roles = user.getRoles();
                if (roles==null){
                    roles = new ArrayList<>();
                }
                roles.add(r);
                itemService.saveUser(user);
                return "redirect:/usersDetail/"+userId + "#roleDiv";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/unassignRole")
    public String unassignRole(@RequestParam(name = "user_id") Long userId,
                               @RequestParam(name = "role_id") Long roleId){
        Roles r = itemService.getRole(roleId);
        if (r!=null){
            Users user = itemService.getUser(userId);
            if (user!=null){
                List<Roles> roles = user.getRoles();
                if (roles==null){
                    roles = new ArrayList<>();
                }
                roles.remove(r);
                itemService.saveUser(user);
                return "redirect:/usersDetail/"+userId;
            }
        }
        return "redirect:/";
    }

    @GetMapping("/403")
    public String accessDenied(Model model){
        model.addAttribute("curUser",getUserData());
        return "403";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request){
        List<Brands> brands = itemService.getAllBrands();
        List<Countries> countries = itemService.getAllCountries();
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        ArrayList<Integer> amount = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())) {
                    amount.add(Integer.parseInt(c.getValue()));
                }
            }
        }
        int total_amount = 0;
        for (int i = 0; i < amount.size(); i++){
            total_amount += amount.get(i);
        }
        model.addAttribute("total_amount",total_amount);
        return "profile";
    }

    @GetMapping("/register")
    public String register(Model model, HttpServletRequest request){
        List<Brands> brands = itemService.getAllBrands();
        List<Countries> countries = itemService.getAllCountries();
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("categories",categories);
        model.addAttribute("curUser",getUserData());
        ArrayList<Integer> amount = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())) {
                    amount.add(Integer.parseInt(c.getValue()));
                }
            }
        }
        int total_amount = 0;
        for (int i = 0; i < amount.size(); i++){
            total_amount += amount.get(i);
        }
        model.addAttribute("total_amount",total_amount);
        return "register";
    }
    @PostMapping("/register")
    public String toRegister(@RequestParam(name = "email") String email,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "rePassword") String rePassword,
                             @RequestParam(name = "fullName") String fullName){
        if (password.equals(rePassword)){
            Users user = new Users();
            user.setEmail(email);
            user.setPassword(password);
            user.setFullName(fullName);
            if (userService.createUser(user)!=null){
                return "redirect:/register?success";
            }

        }
        return "redirect:/register?error";
    }

    @GetMapping("/basket")
    public String basket(Model model, HttpServletRequest request, HttpServletResponse response){
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("brands",brands);
        model.addAttribute("categories",categories);
        Cookie[] cookies = request.getCookies();
        ArrayList<ShopItems> basket_items = new ArrayList<>();
        ArrayList<Integer> amount = new ArrayList<>();
        double total = 0;
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())) {
                    basket_items.add(itemService.getItem(Long.parseLong(c.getName())));
                    amount.add(Integer.parseInt(c.getValue()));
                    total += itemService.getItem(Long.parseLong(c.getName())).getPrice()*Integer.parseInt(c.getValue());
                }
            }
        }
        int total_amount = 0;
        for (int i = 0; i < amount.size(); i++){
            total_amount += amount.get(i);
        }
        model.addAttribute("basket_items",basket_items);
        model.addAttribute("amount",amount);
        model.addAttribute("total",total);
        model.addAttribute("total_amount",total_amount);
        model.addAttribute("curUser",getUserData());
        return "basket";
    }

    @PostMapping("/addBasket")
    public String addBasket(Model model,@RequestParam(name = "item_id") Long itemId,
                            HttpServletRequest request, HttpServletResponse response){
        boolean success = true;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && String.valueOf(itemId).equals(c.getName())){
                    c.setValue(String.valueOf(Long.parseLong(c.getValue())+1));
                    response.addCookie(c);
                    success=false;
                    break;
                }
            }
            if (success)
                response.addCookie(new Cookie(String.valueOf(itemId),"1"));
        }
        model.addAttribute("curUser",getUserData());
        return "redirect:/details/"+itemId;
    }

    @PostMapping("/addBasketInBasket")
    public String addBasketInBasket(Model model,@RequestParam(name = "item_id") Long itemId,
                            HttpServletRequest request, HttpServletResponse response){
        boolean success = true;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && String.valueOf(itemId).equals(c.getName())){
                    c.setValue(String.valueOf(Long.parseLong(c.getValue())+1));
                    response.addCookie(c);
                    success=false;
                    break;
                }
            }
            if (success)
                response.addCookie(new Cookie(String.valueOf(itemId),"1"));
        }
        model.addAttribute("curUser",getUserData());
        return "redirect:/basket";
    }

    private static boolean isNumber(final String str){
        if (str == null || str.length() == 0){
            return false;
        }
        for (char c : str.toCharArray()){
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    @PostMapping("/deleteBasket")
    public String deleteBasket(Model model, @RequestParam(name = "item_id") Long itemId,
                               HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && String.valueOf(itemId).equals(c.getName())){
                    if (c.getValue().equals("1")){
                        c.setMaxAge(0);
                    }
                    else {
                        c.setValue(String.valueOf(Long.parseLong(c.getValue())-1));
                    }
                    response.addCookie(c);
                    break;
                }
            }
        }
        model.addAttribute("curUser",getUserData());
        return "redirect:/basket";
    }

    @PostMapping("clearBasket")
    public String clearBasket(Model model, HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())){
                    c.setMaxAge(0);
                    response.addCookie(c);
                }
            }
        }
        model.addAttribute("curUser",getUserData());
        return "redirect:/basket";
    }

    @PostMapping("pay")
    public String pay(Model model, HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie c : cookies){
                if (isNumber(c.getName()) && isNumber(c.getValue())){
                    Payments payment = new Payments();
                    payment.setItemId(Long.parseLong(c.getName()));
                    payment.setAmount(Long.parseLong(c.getValue()));
                    payment.setAddedDate(new Date(Calendar.getInstance().getTimeInMillis()));
                    itemService.addPayment(payment);
                    c.setMaxAge(0);
                    response.addCookie(c);
                }
            }
        }
        model.addAttribute("curUser",getUserData());
        return "redirect:/basket";
    }

    @PostMapping("/addComment")
    @PreAuthorize("isAuthenticated()")
    public String addComment(Model model, @RequestParam(name = "item_id") Long itemId,
                             @RequestParam("user_id") Long userId,
                             @RequestParam("comment") String text){
        Comments comment = new Comments();
        comment.setAuthor(userService.getUserById(userId));
        comment.setComment(text);
        comment.setAddedDate(new Date(Calendar.getInstance().getTimeInMillis()));
        comment.setShopItem(itemService.getItem(itemId));
        itemService.addComment(comment);
        return "redirect:/details/" + itemId;
    }

    @PostMapping("/deleteComment")
    @PreAuthorize("isAuthenticated()")
    public String deleteComment(Model model, @RequestParam(name = "item_id") Long itemId,
                             @RequestParam("comment_id") Long commentId){
        itemService.deleteComments(itemService.getComment(commentId));
        return "redirect:/details/" + itemId;
    }

    @PostMapping("/editComment")
    @PreAuthorize("isAuthenticated()")
    public String editComment(Model model, @RequestParam(name = "item_id") Long itemId,
                             @RequestParam("comment_id") Long commentId,
                             @RequestParam("comment") String text){
        Comments comment = itemService.getComment(commentId);
        comment.setComment(text);
        itemService.saveComment(comment);
        return "redirect:/details/" + itemId;
    }

    private Users getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }
}
