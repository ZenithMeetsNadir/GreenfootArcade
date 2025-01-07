import greenfoot.*;
import java.util.*;

public class Cloud extends AnimateObj implements IKeysMovement, IAnimateColBlock
{
    protected double speed = 2;
    
    protected String[] movKeys = new String[] {"8", "2", "4", "6"};
    
    public Cloud() {
        super();
    }
    
    public Cloud(double xPos, double yPos) {
        super(xPos, yPos);
    }
    
    @Override
    public void processRPos() {
        this.setPosOmitCheck(this.getDXPos(), this.getDYPos());
    }
    
    public void setUpCollision() {
        if (!this.hasMoved()) {
            Canvas canvas = this.getWorldOfType(Canvas.class);
            if (canvas == null) {
                setPosOmitCheck(this.getDXPos(), this.getDYPos());
                return;
            }
            
            ArrayList<ICollidingObj> colObjs = canvas.getRegisteredColObjs();
            
            double xDir = this.getXPosDiff();
            double yDir = this.getYPosDiff();
            
            for (ICollidingObj colObj : colObjs) {
                colObj.refreshReqPos();
                
                double fallBackXPos = colObj.getDXPosFrame();
                double fallBackYPos = colObj.getDYPosFrame();
                
                double pushXPos = fallBackXPos + xDir;
                double pushYPos = fallBackYPos + yDir;
                
                boolean isPushing = colObj.isIntersectingObj(this);
                
                colObj.setPos(pushXPos, pushYPos);
                colObj.processRPos();
                
                if (isPushing) {
                    double pushResultXDiff = colObj.getDXPos() - fallBackXPos;
                    double pushResultYDiff = colObj.getDYPos() - fallBackYPos;
                    
                    this.setPos(this.getDXPosFrame() + pushResultXDiff, this.getDYPosFrame() + pushResultYDiff);
                }
                    
                boolean previouslyStandingOnTop = colObj.standingOnTop(this);
                
                colObj.setPos(fallBackXPos, fallBackYPos);
                colObj.processRPos();
                
                this.processRPos();
                
                if (previouslyStandingOnTop && !colObj.standingOnTop(this)) {
                    if (colObj instanceof GravityAffected) {
                        GravityAffected gColObj = (GravityAffected)colObj;
                        if (gColObj.getIsStanding())
                            gColObj.startFalling();
                    }
                }
            }
        } else {
            this.processRPos();
        }
    }
    
    @Override
    public void act() {
        checkMovKeyDown();
        
        setUpCollision();
    }
    
    public void setMovKeys(String[] upDownLeftRightKeys) {
        movKeys = upDownLeftRightKeys;
    }
    
    @Override
    public String[] getMovKeys() {
        return movKeys;
    }
    
    @Override
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
