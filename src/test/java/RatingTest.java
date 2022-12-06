import com.api.entities.Profile;
import com.api.entities.Rating;
import com.api.managers.ProfileManager;
import com.api.managers.RatingManager;
import com.api.security.SecurityContextMapper;
import com.api.utils.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RatingTest {

    static Profile fromProfile;
    Profile toProfile;
    Rating rating;
    static RatingManager ratingManager;
    static String jwt;

    @BeforeAll
    public static void init() throws Exception {
        ratingManager = new RatingManager();
        ProfileManager profileManager = new ProfileManager();

        fromProfile = new Profile();
        fromProfile.setUsername("username");
        fromProfile.setPassword("password");

        jwt = profileManager.loginProfile(fromProfile).getJWT();

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt).getBody());
    }

    /**
     * Tests updating rating.
     */
    @Test
    public void testUpdateRating() {
        rating = new Rating();
        toProfile = new Profile();
        toProfile.setUserID(7);

        rating.setRating(2);
        rating.setToUser(toProfile);
        rating.setFromUser(fromProfile);

        try {
            ratingManager.updateRating(
                    new SecurityContextMapper(),
                    rating
            );
        } catch (Exception e) {
            Assertions.fail("UPDATE RATING FAILED", e);
        }
    }
}
