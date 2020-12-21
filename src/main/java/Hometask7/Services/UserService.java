package Hometask7.Services;

import Hometask7.Entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Users getUserByEmail(String email);
    Users createUser(Users user);
    Users saveUser(Users user);
    Users getUserById(Long id);
    boolean updatePassword(Users user, String password, String oldPassword, boolean success);
}
