import greenfoot.*; 
import java.util.*;

public class Canvas extends World
{
    protected ArrayList<CollisionObj> registeredColObjs = new ArrayList<CollisionObj>();
        
    public Canvas()
    {    
        super(600, 400, 1); 
        
        Player player = new Player(100, 100);
        Cloud cloud = new Cloud(200, 200);
        
        //player.isGravityAffected = false;
        //player.activateDebugMode(Color.CYAN);
        //cloud.activateDebugMode(Color.MAGENTA);
        
        place(player);
        place(cloud);
    }
    
    protected void place(PrecisePosObj object) {
        this.addObject(object, (int)object.getDXPos(), (int)object.getDYPos());
        
        if (object instanceof CollisionObj)
            registerColObj((CollisionObj)object);
    }
    
    public ArrayList<CollisionObj> getRegisteredColObjs() {
        return registeredColObjs;
    }
    
    public void registerColObj(CollisionObj obj) {
        registeredColObjs.add(obj);
    }
    
    public void removeColObj(CollisionObj obj) {
        registeredColObjs.remove(obj);
    }
}
