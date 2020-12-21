package Hometask7.Services.impl;


import Hometask7.Entities.Roles;
import Hometask7.Entities.Users;
import Hometask7.Repositories.RoleRepository;
import Hometask7.Repositories.UsersRepository;
import Hometask7.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Users user = usersRepository.findByEmail(s);

        if (user!=null){
            User secUser = new User(user.getEmail(),user.getPassword(),user.getRoles());
            return secUser;
        }

        throw new UsernameNotFoundException("User Not Found");

    }

    @Override
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Users createUser(Users user) {
        Users checkUser = usersRepository.findByEmail(user.getEmail());
        if (checkUser==null){
            Roles role = roleRepository.findByRole("ROLE_USER");
            if (role!=null){
                ArrayList<Roles> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return usersRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public boolean updatePassword(Users user, String password, String oldPassword,boolean success) {
        if(success || passwordEncoder.matches(oldPassword,user.getPassword())){
            user.setPassword(passwordEncoder.encode(password));
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Users getUserById(Long id) {
        return usersRepository.getOne(id);
    }
}
