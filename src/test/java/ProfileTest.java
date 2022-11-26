import com.api.dao.DaoFactory;
import com.api.dao.interfaces.ProfileDao;
import com.api.entity.Profile;
import com.api.security.SecurityContextMapper;
import com.api.util.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProfileTest {
    static ProfileDao profileDao;

    static String jwt;

    @BeforeAll
    public static void init() throws Exception {
        profileDao = (ProfileDao) DaoFactory.getDao(DaoFactory.DaoType.PROFILE);

        System.out.println(System.getenv("MYSQL_PASS"));
        System.out.println(System.getenv("JWT_SECRET_KEY"));
        jwt = profileDao.loginProfile(
                new Profile.ProfileBuilder()
                        .setUsername("username2")
                        .setPassword("password")
                        .build()
        ).getJWT();

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt).getBody());
    }

    @Test
    public void testLogin() throws Exception {

    }

    @Test
    public void testGetProfile() throws Exception {

    }

    @Test
    public void testCreateProfile() throws Exception {
        // creates unique profile
        try {
            profileDao.createProfile(
                    new Profile.ProfileBuilder()
                            .setUsername("unique" + (int) (Math.random() * 100) + "username" + (int) (Math.random() * 10))
                            .setEmail("unique" + (int) (Math.random() * 100) + "email" + (int) (Math.random() * 10) + "@gmail.com")
                            .setPassword("password")
                            .build()
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
        try {
            // updates unique email
            profileDao.updateEmail(
                    new SecurityContextMapper(),
                    new Profile.ProfileBuilder()
                            .setEmail("unique" + (int)(Math.random() * 100) + "email" + (int)(Math.random() * 10) + "@gmail.com")
                            .build()
            );
        } catch (Exception e) {
            Assertions.fail("UNIQUE EMAIL UPDATE FAILED");
        }

        // updates email to one already existing in user database
        profileDao.updateEmail(
                new SecurityContextMapper(),
                new Profile.ProfileBuilder()
                        .setEmail("something@gmail.com")
                        .build()
        );

        // updates email with invalid email format
        profileDao.updateEmail(
                new SecurityContextMapper(),
                new Profile.ProfileBuilder()
                        .setEmail("@.com")
                        .build()
        );
    }

    @Test
    public void testDeleteProfile() throws Exception {

    }
}
