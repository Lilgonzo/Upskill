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
        profile.setUsername("username2");
        profile.setPassword("password");

        jwt = profileDao.loginProfile(profile).getJWT();

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt).getBody());
    }

    @Test
    public void testGetProfilesWithSimilarSkills() throws Exception {
        profileDao = new ProfileManager();

        var profiles = profileDao.getProfilesWithSimilarSkills(new SecurityContextMapper());
        for (var profile: profiles) {
            System.out.println("Username: " + profile.getUsername());
            System.out.println("Email: " + profile.getEmail());
        }
    }

    /**
     * Tests Updating bio.
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

    @Test
    public void testLogin() {
        // todo - already used in init beforeall not sure if this is needed??
    }

    @Test
    public void testGetProfile() {

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

        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,
                () -> profileDao.createProfile(profile),
                "CREATE PROFILE - DUPLICATE USERNAME TEST FAILED"
        );
    }

    /**
     * Tests duplicate email.
     */
    @Test
    public void testCreateDuplicateEmailProfile() {
        profile.setUsername("unique" + (int) (Math.random() * 100) + "username" + (int) (Math.random() * 10));
        profile.setEmail("something@gmail.com");
        profile.setPassword("password");

        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,
                () -> profileDao.createProfile(profile),
                "CREATE PROFILE - DUPLICATE EMAIL TEST FAILED"
        );
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
            //Assertions.fail("UPDATE PROFILE - UNIQUE EMAIL UPDATE FAILED");
            throw new Exception(e);
        }
    }

    /**
     * Test for duplicate email update.
     */
    @Test
    public void testUpdateDuplicateEmail() {
        profile.setEmail("something2@gmail.com");

        Assertions.assertThrows(
            SQLIntegrityConstraintViolationException.class,
                () ->
                    profileDao.updateEmail(
                        new SecurityContextMapper(),
                        profile
                    ),
                "UPDATE PROFILE - DUPLICATE EMAIL UPDATE FAILED"
        );
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
        try {
            //profileDao.deleteProfile(new SecurityContextMapper());
        } catch (Exception e) {
            Assertions.fail("UPDATE PASSWORD FAILED");
        }
    }
}
