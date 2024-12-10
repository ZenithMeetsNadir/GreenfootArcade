import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class Player extends GravityAffected implements IKeysMovement
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
    
    public void setDXPosOmitCheck(double xPos) {
        this.setDXPos(xPos);
        super.setDXPos(xPos);
    }
    public double getDXPosFrame() {
        return super.getDXPos();
    }
    
    public void setDYPosOmitCheck(double yPos) {
        this.setDYPos(yPos);
        super.setDYPos(yPos);
    }
    public double getDYPosFrame() {
        return super.getDYPos();
    }
    
    public void refreshReqPos() {
        this.setDXPos(this.getDXPosFrame());
        this.setDYPos(this.getDYPosFrame());
    }
    
    public void setPosOmitCheck(double xPos, double yPos) {
        setDXPosOmitCheck(xPos);
        setDYPosOmitCheck(yPos);
    }
    
    public double getTopFrame() {
        return super.getTop();
    }
    public void setTopOmitCheck(double yPos) {
        super.setTop(yPos);
        refreshReqPos();
    }
    
    public double getBottomFrame() {
        return super.getBottom();
    }
    public void setBottomOmitCheck(double yPos) {
        super.setBottom(yPos);
        refreshReqPos();
    }
    
    public double getLeftFrame() {
        return super.getLeft();
    }
    public void setLeftOmitCheck(double xPos) {
        super.setLeft(xPos);
        refreshReqPos();
    }
    
    public double getRightFrame() {
        return super.getRight();
    }
    public void setRightOmitCheck(double xPos) {
        super.setRight(xPos);
        refreshReqPos();
    }
    
    /**
     * @return positive number for right
     */
    protected double getXPosDiff() {
        return this.getDXPos() - getDXPosFrame();
    }
    
    /**
     * @return positive number for down
     */
    protected double getYPosDiff() {
        return this.getDYPos() - getDYPosFrame();
    }
    
    protected boolean standingOnTop(CollisionObj colObj) {
        return colObj.isIntersectingObjX(getLeft(), getRight()) && colObj.getTop() == this.getBottom() + 1;
    }
    
    protected double getVectorHorXIntersection(double horYPos, double frameX, double frameY) {
        return getXPosDiff() / getYPosDiff() 
        * (horYPos - frameY) + frameX;
    }
    
    protected double getVectorVertYIntersection(double vertXPos, double frameX, double frameY) {
        return getYPosDiff() / getXPosDiff()
        * (vertXPos - frameX) + frameY;
    }
       
    /**
     * PROSIMTĚ UŽ NA TO NEŠAHEJ TY DEBILE!!!
     * @param colObj kolizní objekt
     * @return strana kolizního objektu, kde nastane kolize
     */
    protected Optional<Direction> findDominantIntersection(CollisionObj colObj) {
        double xDir = getXPosDiff();      
        double yDir = getYPosDiff();
        
        if (yDir == 0) {
            if (xDir < 0)
                return Optional.of(Direction.right);
            else if (xDir > 0)
                return Optional.of(Direction.left);
        }
        else if (xDir == 0) {
            if (yDir < 0) 
                return Optional.of(Direction.down);       
            else if (yDir > 0) 
                return Optional.of(Direction.up);
        }
        
        double frameX = 0;
        double frameY = 0;
        
        if (xDir < 0)
            frameX = this.getLeftFrame();
        else if (xDir > 0) 
            frameX = this.getRightFrame();

            
        if (yDir < 0)
            frameY = this.getTopFrame();
        else if (yDir > 0)
            frameY = this.getBottomFrame();
        
        double topXIntersection = getVectorHorXIntersection(colObj.getTop(), frameX, frameY);
        double bottomXIntersection = getVectorHorXIntersection(colObj.getBottom(), frameX, frameY);
        
        double leftYIntersection = getVectorVertYIntersection(colObj.getLeft(), frameX, frameY);
        double rightYIntersection = getVectorVertYIntersection(colObj.getRight(), frameX, frameY);
        
        double xTolerance = xDir / Math.abs(xDir) * getWidth();
        double yTolerance = yDir / Math.abs(yDir) * getHeight();
        
        if (colObj.isIntersectingObjX(getLeftFrame(), getRightFrame())) {
            if (yDir > 0 
            && (colObj.isIntersectingX(topXIntersection)
            || colObj.isIntersectingX(topXIntersection - xTolerance))) {
                return Optional.of(Direction.up);
            }
            else if (yDir < 0 
            && (colObj.isIntersectingX(bottomXIntersection)
            || colObj.isIntersectingX(bottomXIntersection - xTolerance))) {
                return Optional.of(Direction.down);
            }
        }
        else {
            if (yDir > 0) {
                if (colObj.isIntersectingX(topXIntersection)
                || colObj.isIntersectingX(topXIntersection - xTolerance)) {
                    return Optional.of(Direction.up);
                }
                else if (xDir > 0
                && (colObj.isIntersectingY(leftYIntersection)
                || colObj.isIntersectingY(leftYIntersection - getHeight()))) {
                    return Optional.of(Direction.left);
                }
                else if (xDir < 0
                && (colObj.isIntersectingY(rightYIntersection)
                || colObj.isIntersectingY(rightYIntersection - getHeight()))) {
                    return Optional.of(Direction.right);    
                }
            }
            else if (yDir < 0) {
                if (colObj.isIntersectingX(bottomXIntersection)
                || colObj.isIntersectingX(topXIntersection - xTolerance)) {
                    return Optional.of(Direction.down);
                }
                else if (xDir > 0
                && (colObj.isIntersectingY(leftYIntersection)
                || colObj.isIntersectingY(leftYIntersection + getHeight()))) {
                    return Optional.of(Direction.left);
                }
                else if (xDir < 0 
                && (colObj.isIntersectingY(rightYIntersection)
                || colObj.isIntersectingY(rightYIntersection + getHeight()))) {
                    return Optional.of(Direction.right);
                }
            }
        }
        
        if (colObj.isIntersectingObjY(getTopFrame(), getBottomFrame())) {
            if (xDir > 0 
            && (colObj.isIntersectingY(leftYIntersection)
            || colObj.isIntersectingY(leftYIntersection - yTolerance))) {
                return Optional.of(Direction.left);
            }
            else if (xDir < 0 
            && (colObj.isIntersectingY(rightYIntersection)
            || colObj.isIntersectingY(rightYIntersection - yTolerance))) {
                return Optional.of(Direction.right);
            }
        }
        else {
            
        }
        
        return Optional.empty();
    }
    
    protected boolean landOnTop(CollisionObj colObj) {
        this.setBottom(colObj.getTop() - 1);
        this.setIsStanding(true);
        return true;
    }
    
    protected void landOnBottom(CollisionObj colObj) {
        this.setTop(colObj.getBottom() + 1);
        this.setGravity(0);
    }
    
    protected void landOnLeft(CollisionObj colObj) {
        this.setRight(colObj.getLeft() - 1);
    }
    
    protected void landOnRight(CollisionObj colObj) {
        this.setLeft(colObj.getRight() + 1);
    }
    
    protected void resolveLandingDirection(Optional<Direction> dir, CollisionObj colObj) {
        try {
            switch (dir.get()) {
                case up:
                    landOnTop(colObj);
                    break;
                case down:
                    landOnBottom(colObj);
                    break;
                case left:
                    landOnLeft(colObj);
                    break;
                case right:
                    landOnRight(colObj);
                    break;
            }
        } 
        catch (NoSuchElementException e) { }
    }
    
    String debugLanding = "";
    
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
    
    public void act() {
        super.act();
        
        if (this.getIsStanding())
            jumping = false;
        
        checkMovKeyDown();
        processRPos();
    }
    
    public void setDXPos(double xPos) {
        requestedDXPos = xPos;
    }
    public double getDXPos() {
        return requestedDXPos;
    }
    
    public void setDYPos(double yPos) {
        requestedDYPos = yPos;
    }
    public double getDYPos() {
        return requestedDYPos;
    }
    
    public String[] getMovKeys() {
        return movKeys;
    }
    
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
