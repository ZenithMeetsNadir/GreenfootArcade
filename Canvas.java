import greenfoot.*; 
import java.util.*;

public class Canvas extends World
{
    protected ArrayList<ICollisionBlock> registeredColBlocks = new ArrayList<ICollisionBlock>();
    protected ArrayList<ICollidingObj> registeredColObjs = new ArrayList<ICollidingObj>();
    
    public Canvas()
    {    
        super(600, 400, 1); 
        
        Player player = new Player(50, 100);
        Cloud cloud = new Cloud(50, 200);
        Cloud cloud2 = new Cloud(50, 200);
        Brick brick = new Brick(200, 200);
        Brick brick2 = new Brick(200, 0);
        brick2.setBottom(brick.getTop());
        
        cloud2.setMovKeys(new String[] {"up", "down", "left", "right"});
        
        //player.isGravityAffected = false;
        //player.activateDebugMode(Color.CYAN);
        //cloud.activateDebugMode(Color.MAGENTA);
        
        place(player);
        place(cloud);
        place(cloud2);
        /*place(brick);
        place(brick2);*/
    }

    protected void place(PrecisePosObj object) {
        this.addObject(object, (int)object.getDXPos(), (int)object.getDYPos());
        
        if (object instanceof ICollisionBlock && !this.getRegisteredColBlocks().contains(object))
            registerColBlock((ICollisionBlock)object);
            
        if (object instanceof ICollidingObj && !this.getRegisteredColObjs().contains(object))
            registerColObj((ICollidingObj)object);
    }
    
    public ArrayList<ICollisionBlock> getRegisteredColBlocks() {
        return registeredColBlocks;
    }
    
    public void registerColBlock(ICollisionBlock block) {
        registeredColBlocks.add(block);
    }
    
    public void removeColBlock(ICollisionBlock block) {
        registeredColBlocks.remove(block);
    }
    
    public ArrayList<ICollidingObj> getRegisteredColObjs() {
        return registeredColObjs;
    }
    
    public void registerColObj(ICollidingObj obj) {
        registeredColObjs.add(obj);
    }
    
    public void removeColObj(ICollidingObj obj) {
        registeredColObjs.remove(obj);
    }
}
