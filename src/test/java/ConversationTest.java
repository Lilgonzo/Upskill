import com.api.entities.Conversation;
import com.api.entities.Message;
import com.api.entities.Profile;
import com.api.managers.ConversationManager;
import com.api.managers.ProfileManager;
import com.api.security.SecurityContextMapper;
import com.api.utils.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConversationTest {
    static Profile fromProfile;
    static Conversation conversation;
    Profile toProfile;
    static ConversationManager conversationManager;
    static String jwt;

    @BeforeAll
    public static void init() throws Exception {
        conversation = new Conversation();
        conversationManager = new ConversationManager();
        ProfileManager profileManager = new ProfileManager();

        fromProfile = new Profile();
        fromProfile.setUsername("username");
        fromProfile.setPassword("password");

        jwt = profileManager.loginProfile(fromProfile).getJWT();

        SecurityContextMapper.setClaims(JWTUtil.verifyJwts(jwt).getBody());
    }

    /**
     * tests find all messages in conversation
     */
    @Test
    public void testFindConversationMessages() {
        try {
            conversationManager.findConversationMessages(4);
        } catch (Exception e) {
            Assertions.fail("TEST FIND CONVERSATION MESSAGES FAILED", e);
        }
    }

    /**
     * tests delete convo
     */
    @Test
    public void testDeleteConversation() {
        try {
            //conversationManager.deleteConversation(new SecurityContextMapper(), 4);
        } catch (Exception e) {
            Assertions.fail("TEST DELETE CONVERSATION FAILED", e);
        }
    }

    /**
     * tests send message
     */
    @Test
    public void testSendMessage() {
        toProfile = new Profile();
        Message message = new Message();
        toProfile.setUserID(Integer.parseInt(new SecurityContextMapper().getUserPrincipal().getName()));
        conversation.setConversationID(4);
        message.setMessage("yoooooo!!!");
        message.setFromUser(toProfile);
        message.setConversation(conversation);
        try {
            conversationManager.sendMessage(new SecurityContextMapper(), message);
        } catch (Exception e) {
            Assertions.fail("TEST SEND MESSAGE FAILED", e);
        }
    }
}
