package user_management_service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import user_management_service.dto.UserRequestDto;
import user_management_service.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    String registerUser(UserRequestDto userRequestDto);

    UserResponseDto getUserById(Long userId);

    boolean deleteUser(Long userId);

    String updateUser(Long userId, UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    String uploadLegalDoc(Long userId, MultipartFile file);

    ResponseEntity<?> downloadDocument(String dmsId);
}
