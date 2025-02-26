package user_management_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_service")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_first_name", nullable = false)
    private String userFirstName;

    @Column(name = "user_last_name", nullable = false)
    private String userLastName;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_phone_number")
    private String userPhoneNumber;

    @Column(name = "user_gender")
    private String userGender;

    @Column(name = "user_dob")
    private LocalDate userDateOfBirth;

    @Column(name = "legal_document_dms")
    private String legalDocumentFilePath;

    @Column(name = "is_age_verified")
    private Boolean isAgeVerified = false;

    @Column(name = "user_status")
    private String userStatus;
}
