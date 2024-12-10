import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public interface IKeysMovement
{  
    default void setMovKeys(String[] upDownLeftRightKeys) { }
    String[] getMovKeys();
    
    default void checkMovKeyDown() {
        String[] movKeys = getMovKeys();
        ArrayList<Direction> directions = new ArrayList();
        
        if (Greenfoot.isKeyDown(movKeys[0]))
            directions.add(Direction.up);
        if (Greenfoot.isKeyDown(movKeys[1]))
            directions.add(Direction.down);
        if (Greenfoot.isKeyDown(movKeys[2]))
            directions.add(Direction.left);
        if (Greenfoot.isKeyDown(movKeys[3]))
            directions.add(Direction.right);
        
        move(directions);
    }
    
    void move(ArrayList<Direction> dir);
}