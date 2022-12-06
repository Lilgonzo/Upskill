import com.api.entities.Profile;
import com.api.entities.Settings;
import com.api.managers.ProfileManager;
import com.api.managers.SettingsManager;
import com.api.security.SecurityContextMapper;
import com.api.utils.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SettingsTest {

    static Profile fromProfile;
    Settings settings;
    static SettingsManager settingsManager;
    static String jwt;

    @BeforeAll
    public static void init() throws Exception {
        settingsManager = new SettingsManager();

        ProfileManager profileManager = new ProfileManager();

        fromProfile = new Profile();
        fromProfile.setUsername("username");
        fromProfile.setPassword("password");

        jwt = profileManager.loginProfile(fromProfile).getJWT();

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt).getBody());
    }

    /**
     * Tests updating SETTINGS.
     */
    @Test
    public void testUpdateSettings() {
        settings = new Settings();
        settings.setAge(20);

        try {
            settingsManager.updateSettings(
                    new SecurityContextMapper(),
                    settings
            );
        } catch (Exception e) {
            Assertions.fail("UPDATE SETTINGS FAILED");
        }
    }
}
