package user_management_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user_management_service.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUserIdAndUserStatus(Long userId, String userStatus);
    List<UserModel> findByUserStatus(String userStatus);
}
