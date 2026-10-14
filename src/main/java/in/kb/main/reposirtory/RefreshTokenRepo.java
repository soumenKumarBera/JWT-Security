package in.kb.main.reposirtory;

import in.kb.main.entitys.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String> {

    RefreshToken findByRefreshToken(String refreshToken);
}
