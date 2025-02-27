package user_management_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import user_management_service.Client.DmsFeign;
import user_management_service.dto.UserRequestDto;
import user_management_service.dto.UserResponseDto;
import user_management_service.model.UserModel;
import user_management_service.repository.UserRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final DmsFeign dmsFeign;

    @Override
    public String registerUser(UserRequestDto userRequestDto) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UserModel userModel = UserModel.builder()
                    .userFirstName(userRequestDto.userFirstName())
                    .userLastName(userRequestDto.userLastName())
                    .userEmail(userRequestDto.userEmail())
                    .userPhoneNumber(userRequestDto.userPhoneNumber())
                    .userGender(userRequestDto.userGender())
                    .userDateOfBirth(userRequestDto.userDateOfBirth())
                    .userStatus("active")
                    .build();

            String encryptedPassword = passwordEncoder.encode(userRequestDto.userPassword());
            userModel.setUserPassword(encryptedPassword);

            UserModel response = userRepository.save(userModel);
            log.info("User Registered Successfully With User Id: {}", response.getUserId());
            return "User Registered Successfully With User Id: {}" + response.getUserId();
        }catch (Exception e){
            log.error("User Not Registered Got Exception : {}",e.getMessage());
            return e.getMessage();
        }
    }
    
    @Override
    public UserResponseDto getUserById(Long userId) {
        try {
            Optional<UserModel> userModelOptional = userRepository.findByUserIdAndUserStatus(userId,"active");

            if (userModelOptional.isPresent()) {
                UserResponseDto userResponseDto = getUserResponseDto(userModelOptional.get());
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

    private static UserResponseDto getUserResponseDto(UserModel userModel) {
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
                userModel.getUserStatus(),
                userModel.getUserPassword()
        );
    }

    @Override
    public boolean deleteUser(Long userId) {
        try {
            Optional<UserModel> userModelOptional = userRepository.findByUserIdAndUserStatus(userId,"active");

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
            Optional<UserModel> userModelOptional = userRepository.findByUserIdAndUserStatus(userId,"active");

            if (userModelOptional.isPresent()) {
                UserModel userModel = getUserModel(userRequestDto, userModelOptional.get());
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

    @Override
    public List<UserResponseDto> getAllUsers() {
        try{
            List<UserModel> userList = userRepository.findByUserStatus("active");

            if(!userList.isEmpty()){
                log.info("All Users List :{}", userList);
                return userList.stream()
                        .map(userModel -> new UserResponseDto(
                                userModel.getUserId(),
                                userModel.getUserFirstName(),
                                userModel.getUserLastName(),
                                userModel.getUserEmail(),
                                userModel.getUserPhoneNumber(),
                                userModel.getUserGender(),
                                userModel.getUserDateOfBirth(),
                                userModel.getLegalDocumentFilePath(),
                                userModel.getIsAgeVerified(),
                                userModel.getUserStatus(),
                                userModel.getUserPassword()
                        ))
                        .toList();
            }else {
                log.warn("User List is Empty :{}",userList);
                return Collections.emptyList();
            }
        }catch(Exception e){
            log.error("Got Exception in getAllUsers :{}",e.getMessage());
            return null;
        }
    }

    @Override
    public String uploadLegalDoc(Long userId, MultipartFile file) {
        Optional<UserModel> user = userRepository.findByUserIdAndUserStatus(userId,"active");

        if (user.isPresent()) {
            log.info("User Found with User Id: {}", userId);

            LocalDate userDob = user.get().getUserDateOfBirth();
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(userDob, currentDate).getYears();
            if (age > 25) {
                log.info("User is older than 25 years, proceeding with document upload.");
                ResponseEntity<String> productImageDmsId = dmsFeign.uploadDocument(file, user.get().getUserId().toString(),
                        "Legal Age Verification Doc", "USER_MANAGEMENT_SERVICE");

                if (productImageDmsId != null) {
                    UserModel existingUser = user.get();
                    existingUser.setLegalDocumentFilePath(productImageDmsId.getBody());
                    existingUser.setIsAgeVerified(true);

                    userRepository.save(user.get());
                    log.info("Legal document uploaded and user is age verified.");
                    return "Success: Legal document uploaded and user is verified.";
                } else {
                    log.error("Document upload failed for userId: {}", userId);
                    return "Error: Document upload failed.";
                }
            } else {
                log.warn("User is not older than 25 years. Age: {}", age);
                return "Error: User is not older than 25 years.";
            }
        } else {
            log.warn("User Not Found with User Id: {}", userId);
            return "Error: User Not Found with User Id: " + userId;
        }
    }


    private static UserModel getUserModel(UserRequestDto userRequestDto, UserModel userModel) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        if (userRequestDto.userPassword() != null) {
            String encryptedPassword = passwordEncoder.encode(userRequestDto.userPassword());
            userModel.setUserPassword(encryptedPassword);
        }
        return userModel;
    }

    public boolean verifyPassword(String rawPassword, String storedPasswordHash) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, storedPasswordHash);
    }

}
