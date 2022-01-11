package breakout.vr.BreakoutVR.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "breakout_player_scores", schema = "breakoutVR")
public class BreakoutPlayerScore implements Serializable, Comparable<BreakoutPlayerScore> {
    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "player_scores_sequence",
            sequenceName = "player_scores_sequence",
            allocationSize = 1,
            schema = "breakoutVR"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_scores_sequence"
    )
    private Long id;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "score")
    private Long score;

    public BreakoutPlayerScore(String username, Long score) {
        this.username = username;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BreakoutPlayerScore that = (BreakoutPlayerScore) o;

        if (!id.equals(that.id)) return false;
        if (!score.equals(that.score)) return false;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (int) (score ^ (score >>> 32));
        return result;
    }

    @Override
    public int compareTo(BreakoutPlayerScore o) {
        if(o == null){
            throw new NullPointerException("Cannot compare to null");
        }
        return this.score.compareTo(o.score) * (-1);
    }
}
