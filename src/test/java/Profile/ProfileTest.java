package Profile;

import com.api.dao.DaoFactory;
import com.api.dao.interfaces.ProfileDao;
import com.api.entity.JWT;
import com.api.entity.Profile;
import com.api.security.SecurityContextMapper;
import com.api.util.JWTUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProfileTest {
    static ProfileDao profileDao;

    JWT jwt;

    static {
        try {
            profileDao = (ProfileDao) DaoFactory.getDao(DaoFactory.DaoType.PROFILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void init() throws Exception {
        jwt = profileDao.loginProfile(
                new Profile.ProfileBuilder()
                        .setUsername("username")
                        .setPassword("password")
                        .build()
        );

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt.getJWT()).getBody());
    }

    @Test
    public void testLogin() throws Exception {
    }

    @Test
    public void testGetProfile() throws Exception {

    }

    @Test
    public void testCreateProfile() throws Exception {

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
    public void testUpdateProfile() throws Exception {
        try {
            // updates unique email
            profileDao.updateProfile(
                    new SecurityContextMapper(),
                    new Profile.ProfileBuilder()
                            .setEmail("updated@email.com")
                            .build()
            );
        } catch (Exception e) {
            Assert.fail("UNIQUE EMAIL UPDATE FAILED");
        }


        // updates email to one already existing in user database
        profileDao.updateProfile(
                new SecurityContextMapper(),
                new Profile.ProfileBuilder()
                        .setEmail("something@gmail.com")
                        .build()
        );

        // updates email with invalid email format
        profileDao.updateProfile(
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
