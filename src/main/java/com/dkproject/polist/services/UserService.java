package com.dkproject.polist.services;

import com.dkproject.polist.dtos.UserDto;
import com.dkproject.polist.entities.Role;
import com.dkproject.polist.entities.User;
import com.dkproject.polist.repos.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByName(login).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", login)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRoles()
        );
    }

    public ResponseEntity<?> createUserService(UserDto userDto){

        if(userRepo.findByName(userDto.getName()).isEmpty()){
            User user = new User(
                    userDto.getName(),
                    bCryptPasswordEncoder.encode(userDto.getPassword()),
                    Set.of(Role.ROLE_MODERATOR)
            );
            userRepo.save(user);
            return ResponseEntity.ok("Пользоваетль успешно создан");
        } else {
            return new ResponseEntity<>("Не удалось создать пользователя", HttpStatus.BAD_REQUEST);
        }

    }
}
