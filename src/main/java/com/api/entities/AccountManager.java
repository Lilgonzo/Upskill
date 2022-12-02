public class AccountManager{
    
    public static void retrieveActSimilarInterest(){

    }

    public static void retrieveActSimilarSkill(){
        
    }

    public static void retrieveMatches(){
        
    }

    public static void searchAccount(){
        
    }

    public static void filterResults(){
        
    }

    public static void addSkill(Skill skill){
        
    }

    public static void addInterest(Skill interest){
        
    }

    public static void likeAccount(Account account){
        AccountDB.updateInteraction(true, account);
    }

    public static void dislikeAccount(Account account){
        AccountDB.updateInteraction(false, account);
    }

    public static void deleteAccount(){
        
    }
}
