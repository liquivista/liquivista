package user_management_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import user_management_service.dto.UserRequestDto;
import user_management_service.dto.UserResponseDto;
import user_management_service.model.UserModel;
import user_management_service.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public String registerUser(UserRequestDto userRequestDto) {
        try {
            UserModel userModel = UserModel.builder()
                    .userFirstName(userRequestDto.userFirstName())
                    .userLastName(userRequestDto.userLastName())
                    .userEmail(userRequestDto.userEmail())
                    .userPhoneNumber(userRequestDto.userPhoneNumber())
                    .userGender(userRequestDto.userGender())
                    .userDateOfBirth(userRequestDto.userDateOfBirth())
                    .userStatus("active")
                    .build();
            UserModel response = userRepository.save(userModel);
            log.info("User Registered Successfully With User Id: {}", response.getUserId());
            return "User Registered Successfully With User Id: {}" + response.getUserId();
        }catch (Exception e){
            log.error("User Not Registered Got Exception : {}",e.getMessage());
            return null;
        }
    }
    
    @Override
    public UserResponseDto getUserById(Long userId) {
        try {
            // Find user by ID
            Optional<UserModel> userModelOptional = userRepository.findById(userId);

            if (userModelOptional.isPresent()) {
                UserResponseDto userResponseDto = getUserResponseDto(userModelOptional);
                log.info("User Found With User Id: {}", userId);
                return userResponseDto;
            } else {
                log.warn("User not found with User Id: {}", userId);
                return null;
            }
        } catch (Exception e) {
            log.error("Error while retrieving user. Exception: {}", e.getMessage());
            return null;
        }
    }

    private static UserResponseDto getUserResponseDto(Optional<UserModel> userModelOptional) {
        UserModel userModel = userModelOptional.get();
        return new UserResponseDto(
                userModel.getUserId(),
                userModel.getUserFirstName(),
                userModel.getUserLastName(),
                userModel.getUserEmail(),
                userModel.getUserPhoneNumber(),
                userModel.getUserGender(),
                userModel.getUserDateOfBirth(),
                userModel.getLegalDocumentFilePath(),
                userModel.getIsAgeVerified(),
                userModel.getUserStatus()
        );
    }

    @Override
    public boolean deleteUser(Long userId) {
        try {
            Optional<UserModel> userModelOptional = userRepository.findById(userId);

            if (userModelOptional.isPresent()) {
                UserModel userModel = userModelOptional.get();
                userModel.setUserStatus("inactive");
                userRepository.save(userModel);
                log.info("User Deleted Successfully With User Id: {}", userId);
                return true;
            } else {
                log.warn("User not found for deletion with User Id: {}", userId);
                return false;
            }
        } catch (Exception e) {
            log.error("Error while deleting user. Exception: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String updateUser(Long userId, UserRequestDto userRequestDto) {
        try {
            Optional<UserModel> userModelOptional = userRepository.findById(userId);

            if (userModelOptional.isPresent()) {
                UserModel userModel = getUserModel(userRequestDto, userModelOptional);
                userRepository.save(userModel);

                log.info("User Updated Successfully With User Id: {}", userId);
                return "User Updated Successfully With User Id: " + userId;
            } else {
                log.warn("User not found for update with User Id: {}", userId);
                return "User not found for update.";
            }
        } catch (Exception e) {
            log.error("Error while updating user. Exception: {}", e.getMessage());
            return "Error while updating user.";
        }
    }

    private static UserModel getUserModel(UserRequestDto userRequestDto, Optional<UserModel> userModelOptional) {
        UserModel userModel = userModelOptional.get();

        if (userRequestDto.userFirstName() != null) {
            userModel.setUserFirstName(userRequestDto.userFirstName());
        }
        if (userRequestDto.userLastName() != null) {
            userModel.setUserLastName(userRequestDto.userLastName());
        }
        if (userRequestDto.userEmail() != null) {
            userModel.setUserEmail(userRequestDto.userEmail());
        }
        if (userRequestDto.userPhoneNumber() != null) {
            userModel.setUserPhoneNumber(userRequestDto.userPhoneNumber());
        }
        if (userRequestDto.userGender() != null) {
            userModel.setUserGender(userRequestDto.userGender());
        }
        if (userRequestDto.userDateOfBirth() != null) {
            userModel.setUserDateOfBirth(userRequestDto.userDateOfBirth());
        }
        return userModel;
    }

}
