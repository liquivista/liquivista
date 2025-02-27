package user_management_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user_management_service.dto.UserRequestDto;
import user_management_service.dto.UserResponseDto;
import user_management_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user-management")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user-register")
    public ResponseEntity<?> userRegister(@RequestBody UserRequestDto userRequestDto){
        String response = userService.registerUser(userRequestDto);

        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User registration failed. Please try again.", HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId){
        UserResponseDto user = userService.getUserById(userId);

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();

        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else if (users != null) {
            return new ResponseEntity<>("No users found.", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("An error occurred while fetching users.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId){
        boolean isDeleted = userService.deleteUser(userId);

        if(isDeleted) {
            return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("User deletion failed. Please try again.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/update-user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @RequestBody UserRequestDto userRequestDto){
        String response = userService.updateUser(userId, userRequestDto);

        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("User modification failed. Please try again.", HttpStatus.BAD_REQUEST);
    }


}
