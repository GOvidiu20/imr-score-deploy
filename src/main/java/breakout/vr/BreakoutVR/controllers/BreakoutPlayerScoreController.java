package breakout.vr.BreakoutVR.controllers;

import breakout.vr.BreakoutVR.models.BreakoutPlayerScore;
import breakout.vr.BreakoutVR.utils.RequestPlayerScore;
import breakout.vr.BreakoutVR.service.BreakoutPlayerScoreService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/scores")
public class BreakoutPlayerScoreController {

    private final BreakoutPlayerScoreService service;

    @Autowired
    public BreakoutPlayerScoreController(BreakoutPlayerScoreService service) {
        this.service = service;
    }

    @GetMapping
    public @ResponseBody
    ResponseEntity<Object> getAllDesc(@RequestParam String token) {
        return this.service.getAllPlayerScoresDesc(token);
    }

    @GetMapping(path = "/firstN")
    public @ResponseBody
    ResponseEntity<Object> getFirstNDesc(@RequestParam Long number, @RequestParam String token) {
        return this.service.getFirstNPlayerScoresDesc(number, token);
    }

    @GetMapping(path = "/inTop")
    public ResponseEntity<Object> couldBeInTopN(@RequestParam Long number, @RequestParam Long score, @RequestParam String token) {
        return this.service.couldBeInTopN(number, score, token);
    }

    @PostMapping()
    public ResponseEntity<Object> addPlayerScore(@RequestBody String playerScore, @RequestParam Long limit, @RequestParam String token) {
        Gson g = new Gson();
        RequestPlayerScore requestPlayerScore = g.fromJson(playerScore, RequestPlayerScore.class);
        BreakoutPlayerScore breakoutPlayerScore = BreakoutPlayerScore.builder().username(requestPlayerScore.getUsername())
                .score(requestPlayerScore.getScore()).build();
        return this.service.addPlayerScore(breakoutPlayerScore, limit, token);
    }
}
