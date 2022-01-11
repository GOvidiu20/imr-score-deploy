package breakout.vr.BreakoutVR.repository;

import breakout.vr.BreakoutVR.models.BreakoutPlayerScore;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreakoutPlayerScoreRepository extends JpaRepository<BreakoutPlayerScore, Long> {
    List<BreakoutPlayerScore> findByUsername(String username);
}
