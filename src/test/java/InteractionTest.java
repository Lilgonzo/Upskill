import com.api.entities.Interaction;
import com.api.entities.Profile;
import com.api.entities.Rating;
import com.api.managers.InteractionManager;
import com.api.managers.ProfileManager;
import com.api.security.SecurityContextMapper;
import com.api.utils.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InteractionTest {

    static Interaction interaction;
    static Profile fromProfile;
    Profile toProfile;
    static ProfileManager profileManager;
    static InteractionManager interactionManager;
    static String jwt;

    @BeforeAll
    public static void init() throws Exception {
        interaction = new Interaction();
        profileManager = new ProfileManager();
        interactionManager = new InteractionManager();

        fromProfile = new Profile();
        fromProfile.setUsername("username");
        fromProfile.setPassword("password");

        jwt = profileManager.loginProfile(fromProfile).getJWT();

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt).getBody());
    }

    /**
     * tests getting profiles by rating
     */
    @Test
    public void testGetProfilesByRating() {
        Rating rating = new Rating();
        rating.setRating(5);

        try {
            profileManager.getProfilesByRating(rating);
        } catch (Exception e) {
            Assertions.fail("FAILED TO GET PROFILES BY RATING", e);
        }
    }

    /**
     * Tests getting matches.
     */
    @Test
    public void testGetMatches() {
        try {
            interactionManager.getMatches(new SecurityContextMapper());
        } catch (Exception e) {
            Assertions.fail("FAILED GET MATCHES", e);
        }
    }

    /**
     * Tests like user.
     */
    @Test
    public void testLikeUser() {
        toProfile = new Profile();
        toProfile.setUserID(7);

        interaction.setLike(1);
        interaction.setToUser(toProfile);
        interaction.setFromUser(fromProfile);

        try {
            interactionManager.likeUser(
                    new SecurityContextMapper(),
                    interaction
            );
        } catch (Exception e) {
            Assertions.fail("LIKE USER FAILED", e);
        }
    }
}
