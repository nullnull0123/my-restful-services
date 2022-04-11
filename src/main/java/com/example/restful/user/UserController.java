package com.example.restful.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveAllUsers(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        return user;
    }

    @PostMapping(path = "/users")
    public ResponseEntity createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

        // 사용자에게 요청값 반환
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // 현재 가지고 있는 request 사용
                .path("/{id}") // 반환해주고 싶은 id 값 전달
                .buildAndExpand(savedUser.getId()) // 설정해준 가변 변수에 새롭게 만들어준 id 값 사용
                .toUri();// 위 모든걸 uri 형태로 변경
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping("/users")
    public void updateUser(@RequestBody User param){
        User user = service.updateOneById(param);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", param.getId()));
        }
    }
}
