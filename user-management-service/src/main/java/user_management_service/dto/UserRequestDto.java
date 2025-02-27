package user_management_service.dto;

import java.time.LocalDate;

public record UserRequestDto(Long userId,
                             String userFirstName,
                             String userLastName,
                             String userEmail,
                             String userPhoneNumber,
                             String userGender,
                             LocalDate userDateOfBirth,
                             String userPassword) {
}
