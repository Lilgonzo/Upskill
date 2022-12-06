import com.api.managers.ProfileManager;
import com.api.entities.Profile;
import com.api.security.SecurityContextMapper;
import com.api.utils.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class for testing ProfileManager class
 */
public class ProfileTest {
    static Profile profile;
    static ProfileManager profileManager;
    static String jwt;

    @BeforeAll
    public static void init() throws Exception {
        profileManager = new ProfileManager();

        profile = new Profile();
        profile.setUsername("username");
        profile.setPassword("password");

        jwt = profileManager.loginProfile(profile).getJWT();

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt).getBody());
    }

    /**
     * Test for getting profiles with similar skills.
     */
    @Test
    public void testGetProfilesWithSimilarSkills() {
        try {
            profileManager.getProfilesWithSimilarSkills(new SecurityContextMapper());
        } catch (Exception e) {
            Assertions.fail("TEST GET PROFILE WITH SIMILAR SKILLS FAILED", e);
        }

    }

    /**
     * Test for getting profiles with similar interests.
     */
    //todo
    //double check
    @Test
    public void testGetProfilesWithSimilarInterests() {
        try {
            profileManager.getProfilesWithSimilarInterests(new SecurityContextMapper());
        } catch (Exception e) {
            Assertions.fail("TEST GET PROFILE WITH SIMILAR INTERESTS FAILED", e);
        }

    }

    /**
     * Tests updating bio.
     */
    @Test
    public void testUpdateBio() {
        profile.setBio("THIS IS A NEW B");

        try {
            profileManager.updateBio(
                    new SecurityContextMapper(),
                    profile
            );
        } catch (Exception e) {
            Assertions.fail("UPDATE BIO FAILED");
        }
    }

    /**
     * Tests updating SETTINGS.
     */
    //todo
    //unsure if right since idk how they are associated other than database
    @Test
    public void testUpdateSettings() {
        Settings settings = new Settings();
        settings.setAge(20);
        try {
            profileManager.updateSettings(
                    new SecurityContextMapper(),
                    profile
            );
        } catch (Exception e) {
            Assertions.fail("UPDATE BIO FAILED");
        }
    }

    /**
     * Tests like user.
     */
    //todo
    //unsure if right
    @Test
    public void testLikeUser() {
        Interaction interaction = new Interaction();
        profile2 = new Profile();
        profile2.setUsername("username");
        profile2.setPassword("password");

        jwt = profileManager.loginProfile(profile2).getJWT();

        interaction.setLike(1);
        interaction.setToUser(profile2);
        interaction.setFromUser(profile);

        try {
            interactionManager.likeUser(
                    new SecurityContextMapper(),
                    interaction
            );
        } catch (Exception e) {
            Assertions.fail("LIKE USER FAILED");
        }
    }

    /**
     * Tests updating rating.
     */
    //todo
    //unsure if right since idk how they are associated other than database
    @Test
    public void testUpdateRating() {
        Rating rate = new Rating();
        profile2 = new Profile();
        profile2.setUsername("username");
        profile2.setPassword("password");

        jwt = profileManager.loginProfile(profile2).getJWT();

        rating.setToUser(profile2);
        rating.setFromUser(profile);
        rating.setRating(2);

        try {
            profileManager.updateRating(
                    new SecurityContextMapper(),
                    profile
            );
        } catch (Exception e) {
            Assertions.fail("UPDATE RATING FAILED");
        }
    }

    /**
     * Test get profile
     */
    @Test
    public void testGetProfile() {
        try {
            profileManager.getProfile("username");
        } catch (Exception e) {
            Assertions.fail("FAILED TO GET PROFILE", e);
        }
    }

    /**
     * Test create unique profile.
     */
    @Test
    public void testCreateUniqueProfile() {
        // creates unique profile
        profile.setUsername("unique" + (int) (Math.random() * 100) + "username" + (int) (Math.random() * 10));
        profile.setEmail("unique" + (int) (Math.random() * 100) + "email" + (int) (Math.random() * 10) + "@gmail.com");
        profile.setPassword("password");

        try {
            profileManager.createProfile(
                    profile
            );
        } catch (Exception e) {
            Assertions.fail("CREATE UNIQUE PROFILE FAILED");
        }
    }

    /**
     * Tests duplicate username.
     */
    @Test
    public void testCreateDuplicateUsernameProfile() {
        profile.setUsername("username1");
        profile.setEmail("unique" + (int) (Math.random() * 100) + "email" + (int) (Math.random() * 10) + "@gmail.com");
        profile.setPassword("password");

        try {
            profileManager.createProfile(profile);
        } catch (Exception e) {
            return;
        }
        Assertions.fail("CREATE PROFILE - DUPLICATE USERNAME TEST FAILED");
    }

    /**
     * Tests duplicate email.
     */
    @Test
    public void testCreateDuplicateEmailProfile() {
        profile.setUsername("unique" + (int) (Math.random() * 100) + "username" + (int) (Math.random() * 10));
        profile.setEmail("something@gmail.com");
        profile.setPassword("password");

        try {
            profileManager.createProfile(profile);
        } catch (Exception e) {
            return;
        }
        Assertions.fail("CREATE PROFILE - DUPLICATE EMAIL TEST FAILED");
    }

    /**
     * Test for valid email update.
     */
    @Test
    public void testUpdateValidEmail() {
        profile.setEmail("unique" + (int)(Math.random() * 100) + "email" + (int)(Math.random() * 10) + "@gmail.com");
        try {
            // updates unique email
            profileManager.updateEmail(
                    new SecurityContextMapper(),
                    profile
            );
        } catch (Exception e) {
            Assertions.fail("UPDATE PROFILE - UNIQUE EMAIL UPDATE FAILED", e);
        }
    }

    /**
     * Test for duplicate email update.
     */
    @Test
    public void testUpdateDuplicateEmail() {
        profile.setEmail("something@gmail.com");
        try {
            // updates unique email
            profileManager.updateEmail(
                    new SecurityContextMapper(),
                    profile
            );
        } catch (Exception e) {
            return;
        }
        Assertions.fail("UPDATE PROFILE - DUPLICATE EMAIL UPDATE FAILED");
    }

    /**
     * Test for deleting profile. Only fails if connection to db fails otherwise always returns void even
     * if profile is non-existent.
     */
    //todo
    @Test
    public void testDeleteProfile() {
        try {
            //profileManager.deleteProfile(new SecurityContextMapper());
        }
        catch (Exception e) {
            Assertions.fail("DELETE PROFILE FAILED", e);
        }
    }

    /**
     * Test for update password.
     */
    @Test
    public void testUpdatePassword() {
        profile.setPassword("password");
        try {
            profileManager.updatePassword(new SecurityContextMapper(), profile);
        } catch (Exception e) {
            Assertions.fail("UPDATE PASSWORD FAILED");
        }
    }

    /**
     * Test for logout
     */
    @Test
    public void testLogout() {
        profileManager.logout();
    }
}
