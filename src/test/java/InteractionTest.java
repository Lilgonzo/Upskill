import com.api.entities.Interaction;
import com.api.entities.Profile;
import com.api.managers.InteractionManager;
import com.api.managers.ProfileManager;
import com.api.security.SecurityContextMapper;
import com.api.utils.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InteractionTest {

    static Interaction interaction;
    static Profile profile;
    static ProfileManager profileManager;
    static InteractionManager interactionManager;
    static String jwt;

    @BeforeAll
    public static void init() throws Exception {
        profileManager = new ProfileManager();
        interactionManager = new InteractionManager();

        profile = new Profile();
        profile.setUsername("username");
        profile.setPassword("password");

        jwt = profileManager.loginProfile(profile).getJWT();

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt).getBody());
    }

    @Test
    public void testGetMatches() {
        try {
            interactionManager.getMatches(new SecurityContextMapper());
        } catch (Exception e) {
            Assertions.fail("FAILED GET MATCHES", e);
        }
    }
}
