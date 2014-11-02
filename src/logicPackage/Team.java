package logicPackage;

import java.util.ArrayList;

public class Team {
    private String tag;
    private int places = 0;
    private ArrayList<LoggingServer> team = new ArrayList();
   
    public Team(LoggingServer log, String tag){
        this.tag = tag;
        team.add(log);
        log.setStatus(true);
    }
    
    public int addTeammate(LoggingServer log){
        if (!teamFull()){
            team.add(log);
            places++;
            return 1;
        }
        else
            return 0;
    }

    public String getTag() {
        return tag;
    }
    
    public boolean teamFull(){
        return places == 10;
    }
    
    
    
}
