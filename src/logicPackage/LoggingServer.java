package logicPackage;

public class LoggingServer {
    private String username;
    private String password;
    private String nickname;
    private String tag;
    private boolean status;
    
    //sets the team
    private final Team team;

    public LoggingServer(String username, String password, String nickname, String tag) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.tag = tag;
        team =  new Team(this, tag);
    } 
    
    //set status to leader
    public void setStatus(boolean status){
        this.status = status;
    }
    
}
