public class Account {
    int userID;
    String username;
    String password;
    String email;
    List<Skill> skills;
    List<Skill> interests;
    ConvoMediator mediator;

    Account(String user, String pass, String em){
        this.username = user;
        this.password = pass;
        this.email = em;
    }

}
