import greenfoot.*;
import java.util.*;

public class Cloud extends CollisionObj implements IKeysMovement
{
    protected double speed = 2;
    
    protected String[] movKeys = new String[] {"8", "2", "4", "6"};
    
    public Cloud() {
        super();
    }
    
    public Cloud(double xPos, double yPos) {
        super(xPos, yPos);
    }
    
    public void act() {
        super.act();
        
        checkMovKeyDown();
    }
    
    public void setMovKeys(String[] upDownLeftRightKeys) {
        movKeys = upDownLeftRightKeys;
    }
    
    public String[] getMovKeys() {
        return movKeys;
    }
    
    public void move(ArrayList<Direction> dir) {
        if (dir.contains(Direction.up)) {
            this.setDYPos(this.getDYPos() - speed);
        }
        if (dir.contains(Direction.down)) {
            this.setDYPos(this.getDYPos() + speed);
        }
        if (dir.contains(Direction.left)) {
            this.setDXPos(this.getDXPos() - speed);
        }
        if (dir.contains(Direction.right)) {
            this.setDXPos(this.getDXPos() + speed);
        }
    }
}
