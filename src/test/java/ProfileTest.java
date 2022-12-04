import com.api.managers.ProfileManager;
import com.api.entities.Profile;
import com.api.security.SecurityContextMapper;
import com.api.utils.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Test class for testing ProfileManager class
 */
public class ProfileTest {
    static Profile profile;
    static ProfileManager profileDao;
    static String jwt;

    @BeforeAll
    public static void init() throws Exception {
        profileDao = new ProfileManager();

        profile = new Profile();
        profile.setUsername("username");
        profile.setPassword("password");

        jwt = profileDao.loginProfile(profile).getJWT();

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt).getBody());
    }

    /**
     * Test for getting profiles with similar skills.
     */
    @Test
    public void testGetProfilesWithSimilarSkills() {
        try {
            profileDao.getProfilesWithSimilarSkills(new SecurityContextMapper());
        } catch (Exception e) {
            Assertions.fail("TEST GET PROFILE WITH SIMILAR INTEREST FAILED", e);
        }

    }

    /**
     * Tests updating bio.
     */
    @Test
    public void testUpdateBio() {
        profile.setBio("THIS IS A NEW B");

        try {
            profileDao.updateBio(
                    new SecurityContextMapper(),
                    profile
            );
        } catch (Exception e) {
            Assertions.fail("UPDATE BIO FAILED");
        }
    }

    /**
     * Test get profile
     */
    @Test
    public void testGetProfile() {
        try {
            profileDao.getProfile("username");
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
            profileDao.createProfile(
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
            profileDao.createProfile(profile);
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
            profileDao.createProfile(profile);
        } catch (Exception e) {
            return;
        }
        Assertions.fail("CREATE PROFILE - DUPLICATE EMAIL TEST FAILED");
    }

    /**
     * Test for valid email update.
     */
    @Test
    public void testUpdateValidEmail() throws Exception {
        profile.setEmail("unique" + (int)(Math.random() * 100) + "email" + (int)(Math.random() * 10) + "@gmail.com");
        try {
            // updates unique email
            profileDao.updateEmail(
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
            profileDao.updateEmail(
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
    @Test
    public void testDeleteProfile() {
        try {
            profileDao.deleteProfile(new SecurityContextMapper());
        }
        catch (Exception e) {
            Assertions.fail("DELETE PROFILE FAILED");
        }
    }

    /**
     * Test for update password.
     */
    @Test
    public void testUpdatePassword() {
        profile.setPassword("password");
        try {
            profileDao.updatePassword(new SecurityContextMapper(), profile);
        } catch (Exception e) {
            Assertions.fail("UPDATE PASSWORD FAILED");
        }
    }
}
