import greenfoot.*;
import java.util.*;

public class Player extends GravityAffected implements IKeysMovement, ICollidingObj
{
    private double requestedDXPos = 0;
    private double requestedDYPos = 0;
    
    protected int jumpForce = 7;
    protected boolean jumping = false;
    protected double speed = 2;
    
    protected String[] movKeys = new String[] { "w", "s", "a", "d" };
    
    public Player() {
        super();
    }
    
    public Player(double xPos, double yPos) {
        super(xPos, yPos);
        setPosOmitCheck(xPos, yPos);
    }
    
    protected boolean standingOnTop(CollisionObj colObj) {
        return colObj.isIntersectingObjX(getLeft(), getRight()) && colObj.getTop() == this.getBottom() + 1;
    }
    
    @Override
    public void landOnTop(CollisionObj colObj) {
        this.setBottom(colObj.getTop() - 1);
        this.setIsStanding(true);
    }
    
    @Override
    public void landOnBottom(CollisionObj colObj) {
        this.setTop(colObj.getBottom() + 1);
        this.setGravity(0);
    }
    
    String debugLanding = "";
    
    @Override
    public void processRPos() {
        Canvas canvas = this.getWorldOfType(Canvas.class);
        if (canvas == null) {
            setPosOmitCheck(this.getDXPos(), this.getDYPos());
            return;
        }

        ArrayList<CollisionObj> colObjs = canvas.getRegisteredColObjs();
        
        boolean isStanding = false;
        String str = "";
        
        boolean stanceFound = this.isTouchingGround();
        for (CollisionObj colObj : colObjs) {
            if (this.standingOnTop(colObj))
                stanceFound = true;
        }
        
        //debug
        str += "DEBUG:\n";
        str += "stance found: " + stanceFound + "\n";
        //enddebug
        
        Optional<Direction> landingDirection = Optional.empty();
        
        for (CollisionObj colObj : colObjs) {       
            double xDir = getXPosDiff(); 
            double yDir = getYPosDiff(); 
            
            if (xDir != 0 && this.getIsStanding() && !stanceFound)
                this.startFalling();
            
            if (colObj.isIntersectingObj(this))
                landingDirection = findDominantIntersection(colObj);
            
            str += "Land: ";
            
            try {
                if (landingDirection.get() == Direction.up)
                    stanceFound = true;
                    
                this.debugLanding = landingDirection.get().toString();
            } catch (NoSuchElementException e) { }
            
            str += debugLanding + "\n";
            
            resolveLandingDirection(landingDirection, colObj);
        }
        
        if (this.isBelowGround()) {
            this.setBottom(this.getGround());
            setIsStanding(true);
        }
        
        setPosOmitCheck(this.getDXPos(), this.getDYPos());
        
        canvas.showText(str, 100, 50);
    }
    
    @Override
    public void act() {
        super.act();
        
        if (this.getIsStanding())
            jumping = false;
        
        checkMovKeyDown();
        processRPos();
    }
    
    @Override
    public String[] getMovKeys() {
        return movKeys;
    }
    
    @Override
    public void move(ArrayList<Direction> dir) {
        if (dir.contains(Direction.up)) {
            if (isGravityAffected) {
                if (!jumping) {
                    jumping = true;
                    this.setGravity(this.getGravity() - jumpForce);
                }  
            }
            else {
                this.setDYPos(this.getDYPos() - speed);
            }
        }
        if (dir.contains(Direction.down)) {
            if (!isGravityAffected) {
                this.setDYPos(this.getDYPos() + speed);
            }
        }
        if (dir.contains(Direction.left)) {
            this.setDXPos(this.getDXPos() - speed);
        }
        if (dir.contains(Direction.right)) {
            this.setDXPos(this.getDXPos() + speed);
        }
    }
}
