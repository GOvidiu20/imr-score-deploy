package breakout.vr.BreakoutVR.service;

import breakout.vr.BreakoutVR.models.BreakoutPlayerScore;
import breakout.vr.BreakoutVR.repository.BreakoutPlayerScoreRepository;
import breakout.vr.BreakoutVR.utils.AuthHelper;
import breakout.vr.BreakoutVR.utils.JWTUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BreakoutPlayerScoreService {

    private final BreakoutPlayerScoreRepository repository;
    @Getter
    private final JWTUtil jwtUtil;

    @Autowired
    public BreakoutPlayerScoreService(BreakoutPlayerScoreRepository repository, JWTUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<Object> addPlayerScore(BreakoutPlayerScore playerScore, Long limit, String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        }
        if (playerScore == null) {
            return new ResponseEntity<>("Cannot add an empty player score", HttpStatus.BAD_REQUEST);
        }
        List<BreakoutPlayerScore> breakoutPlayerScores = this.repository.findByUsername(playerScore.getUsername());

        if (breakoutPlayerScores == null || breakoutPlayerScores.isEmpty()) {
            List<BreakoutPlayerScore> playerScores = getAllDesc();

            if (playerScores != null && !playerScores.isEmpty()) {
                int length = playerScores.size();
                if (!(length < limit)) {
                    BreakoutPlayerScore breakoutPlayerScore = playerScores.get(length - 1);
                    this.repository.delete(breakoutPlayerScore);
                }
            }
            this.repository.save(playerScore);
        } else {
            BreakoutPlayerScore score = breakoutPlayerScores.get(0);
            score.setScore(playerScore.getScore());
            this.repository.save(score);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Player score added successfully");
    }

    public ResponseEntity<Object> getAllPlayerScoresDesc(String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(getAllDesc());
    }

    public ResponseEntity<Object> getFirstNPlayerScoresDesc(Long number, String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.repository.findAll().stream().sorted().limit(number).collect(Collectors.toList()));
    }

    public ResponseEntity<Object> couldBeInTopN(Long number, Long score, String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        }
        List<BreakoutPlayerScore> breakoutPlayerScores = this.repository.findAll().stream()
                .sorted(Comparator.comparing(BreakoutPlayerScore::getScore)).limit(number).collect(Collectors.toList());
        if (breakoutPlayerScores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Player is in top");
        }
        if (breakoutPlayerScores.get(0).getScore().compareTo(score) <= 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Player is in top");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Player is not in top");
    }

    private List<BreakoutPlayerScore> getAllDesc() {
        return this.repository.findAll().stream().sorted().collect(Collectors.toList());
    }
}
