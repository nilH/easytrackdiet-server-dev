package nilH.easyTrackDiet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nilH.easyTrackDiet.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}
