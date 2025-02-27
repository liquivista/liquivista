package user_management_service.service;

import user_management_service.dto.UserRequestDto;
import user_management_service.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    String registerUser(UserRequestDto userRequestDto);

    UserResponseDto getUserById(Long userId);

    boolean deleteUser(Long userId);

    String updateUser(Long userId, UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();
}
