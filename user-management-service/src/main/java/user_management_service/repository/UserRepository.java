package user_management_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user_management_service.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
