package com.example.redisdemo.service;

import com.example.redisdemo.dto.CreateUserDto;
import com.example.redisdemo.dto.UpdateUserDto;
import com.example.redisdemo.model.User;
import com.example.redisdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //CacheEvict her çalışmasında silinecek dataların adları burada belirtilir bu fonksiyon her çalıştığında cache yeniden doldurulur
    @CacheEvict(value = {"users", "users_id"}, allEntries = true)
    public User createUser(CreateUserDto createUserDto) {
        var user = userRepository.save(createUserDto.toEntity(createUserDto)); //db ye kaydediyoruz
        return user;
    }
    //sürekli veritabanına gidip gidip sormasın diye cache e atacağız.
    //key-value ilişkisi
    //method adı key
    //unless ile eğer veri yoksa cache koyma
    @Cacheable(value = "users", key = "#root.methodName", unless = "#result == null")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Cacheable(cacheNames = "user_id", key = "#root.methodName + #id", unless = "#result == null")
    public User getUsersById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @CachePut(cacheNames = "user_id", key = "#getUsersById + #dto.id", unless = "#result == null")
    public User updateUser(UpdateUserDto dto) {
        Optional<User> user = userRepository.findById(dto.getId());
        if(user.isPresent()) {
            //Eğer kullanıcı var ise onu bir bul bilgileri güncelle şifre aynı kalsın
            User userEntity = user.get();
            userEntity.setPassword(dto.getPassword());
            return userRepository.save(userEntity);
        }
        else {
            return null;
        }
    }

    public String deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.delete(user.get());
            return "success";
        }
        else {
            return "";
        }
    }
}