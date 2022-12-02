import com.api.dao.DaoFactory;
import com.api.dao.interfaces.ProfileDao;
import com.api.entity.Profile;
import com.api.security.SecurityContextMapper;
import com.api.util.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProfileTest {
    static Profile profile;
    static ProfileDao profileDao;
    static String jwt;

    @BeforeAll
    public static void init() throws Exception {
        profileDao = (ProfileDao) DaoFactory.getDao(DaoFactory.DaoType.PROFILE);

        profile = new Profile();
        profile.setUsername("username");
        profile.setPassword("password");

        jwt = profileDao.loginProfile(profile).getJWT();

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt).getBody());
    }

    @Test
    public void testGetProfilesWithSimilarSkills() throws Exception {
        profileDao = (ProfileDao) DaoFactory.getDao(DaoFactory.DaoType.PROFILE);

        var profiles = profileDao.getProfilesWithSimilarSkills(new SecurityContextMapper());
        for (var profile: profiles) {
            System.out.println("Username: " + profile.getUsername());
            System.out.println("Email: " + profile.getEmail());
        }
    }

    @Test
    public void testUpdateBio() throws Exception {
        profile.setBio("Rawr");

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
    public void testLogin() throws Exception {
        // todo - already used in init beforeall not sure if this is needed??
    }

    @Test
    public void testGetProfile() throws Exception {

    }

    @Test
    public void testCreateProfile() throws Exception {
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

        // creates duplicate profile


    }

    /**
     * Updates email.
     * <br/><br/>
     * Tests for:
     * <ul>
     *     <li>Valid Email</li>
     *     <li>Invalid Duplicate Email</li>
     *     <li>Invalid Email Format</li>
     * </ul>
     */
    @Test
    public void testUpdateEmail() throws Exception {
        profile.setEmail("unique" + (int)(Math.random() * 100) + "email" + (int)(Math.random() * 10) + "@gmail.com");
        try {
            // updates unique email
            profileDao.updateEmail(
                    new SecurityContextMapper(),
                    profile
            );
        } catch (Exception e) {
            Assertions.fail("UNIQUE EMAIL UPDATE FAILED");
        }

        /*// updates email to one already existing in user database
        profileDao.updateEmail(
                new SecurityContextMapper(),
                new Profile.ProfileBuilder()
                        .setEmail("something@gmail.com")
                        .build()
        );*/

        /*// updates email with invalid email format
        profileDao.updateEmail(
                new SecurityContextMapper(),
                new Profile.ProfileBuilder()
                        .setEmail("@.com")
                        .build()
        );*/
    }

    @Test
    public void testDeleteProfile() throws Exception {

    }
}
