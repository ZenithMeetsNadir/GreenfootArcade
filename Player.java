import greenfoot.*;
import java.util.*;

public class Player extends GravityAffected implements IKeysMovement, ICollidingObj
{
    private double requestedDXPos = 0;
    private double requestedDYPos = 0;
    
    protected int jumpForce = 8;
    protected boolean jumping = false;
    protected double speed = 2;
    
    protected String[] movKeys = new String[] { "w", "s", "a", "d" };
    
    public Player() {
        super();
    }
    
    public Player(double xPos, double yPos) {
        super(xPos, yPos);
    }
    
    @Override
    public void landOnTop(ICollisionBlock colObj) {
        this.setBottom(colObj.getTop() - 1);
        this.setIsStanding(true);
    }
    
    @Override
    public void landOnBottom(ICollisionBlock colObj) {
        this.setTop(colObj.getBottom() + 1);
        this.setGravity(0);
    }
    
    String debugLanding = "";
    String debugCollision = "";
    
    @Override    
    public void processRPos() {
        Canvas canvas = this.getWorldOfType(Canvas.class);
        if (canvas == null) {
            setPosOmitCheck(this.getDXPos(), this.getDYPos());
            return;
        }

        ArrayList<ICollisionBlock> colBlocks = canvas.getRegisteredColBlocks();
        
        boolean isStanding = false;
        String str = "";
        
        boolean stanceFound = this.isTouchingGround();
        for (ICollisionBlock colBlock : colBlocks) {
            if (this.standingOnTop(colBlock))
                stanceFound = true;
        }
        
        //debug
        str += "DEBUG:\n";
        str += "stance found: " + stanceFound + "\n";
        //enddebug
        
        Optional<Direction> landingDirection;
        
        for (ICollisionBlock colBlock : colBlocks) { 
            landingDirection = Optional.empty();
            
            double xDir = getXPosDiff();
            double yDir = getYPosDiff();
            
            if (xDir != 0 && this.getIsStanding() && !stanceFound)
                this.startFalling();
            
            if (colBlock.isIntersectingObj(this))
                landingDirection = findDominantIntersection(colBlock);    
                
            try {
                if (landingDirection.get() == Direction.up)
                    stanceFound = true;
                    
                this.debugLanding = landingDirection.get().toString();
                this.debugCollision = "" + colBlock.toString();
            } catch (NoSuchElementException e) { }
            
            resolveLandingDirection(landingDirection, colBlock);
        }
        
        str += "land: " + debugLanding + "\n";
        
        if (this.isBelowGround()) {
            this.setBottom(this.getGround());
            setIsStanding(true);
        }
        
        canvas.showText(str, 100, 50);
        
        super.processRPos();
    }
    
    @Override
    public void act() {
        if (this.getIsStanding())
            jumping = false;
        
        checkMovKeyDown();
        
        super.act();
    }
    
    @Override
    public String[] getMovKeys() {
        return movKeys;
    }
    
    @Override
    public void move(ArrayList<Direction> dir) {
        if (dir.contains(Direction.up)) {
            if (isGravityAffected) {
                if (!jumping && this.getGravity() == 0) {
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
