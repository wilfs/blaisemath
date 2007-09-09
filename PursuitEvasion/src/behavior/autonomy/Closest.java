/*
 * Closest.java
 * Created on Sep 4, 2007, 2:17:30 PM
 */

package behavior.autonomy;

import agent.Agent;
import agent.Team;
import task.Goal;
import utility.DistanceTable;

/**
 * Player only considers the closest enemy.
 * <br><br>
 * @author Elisha Peterson
 */
public class Closest extends Autonomy {

    public Closest(){}
        
    /** Performs tasking based on a preset goal.
     * @param team the team to assign tasks to
     * @param goal the goal used for task assignment */
    public void assign(Team team,Goal goal){
        DistanceTable dist=new DistanceTable(team,goal.getTarget());
        for(Agent p:team){p.assignTask(dist.min(p,goal.getTarget()).getSecond(),goal.getType());}
    }
}
