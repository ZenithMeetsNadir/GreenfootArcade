import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class PrecisePosObj extends Actor
{
    private double _dXPos = 0;
    private double _dYPos = 0;
    
    public PrecisePosObj() {
        super();
    }
    
    public PrecisePosObj(double xPos, double yPos) {
        setDXPos(xPos);
        setDYPos(yPos);
    }
    
    public double getDXPos() {
        return _dXPos;
    }
    public void setDXPos(double value) {
        _dXPos = value;
        this.setLocation((int)value, (int)getDYPos());
    }
    
    public double getDYPos() {
        return _dYPos;
    }
    public void setDYPos(double value) {
        _dYPos = value;
        this.setLocation((int)Math.round(getDXPos()), (int)Math.round(value));
    }
    
    public void setPos(double xPos, double yPos) {
        setDXPos(xPos);
        setDYPos(yPos);
    }
    
    public double getTop() {
        return getDYPos() - (double)this.getImage().getHeight() / 2;
    }
    public void setTop(double yPos) {
        setDYPos(yPos + (double)this.getImage().getHeight() / 2);
    }
    
    public double getBottom() {
        return getDYPos() + this.getImage().getHeight() / 2 - 1;
    }
    public void setBottom(double yPos) {
        setDYPos(yPos - this.getImage().getHeight() / 2 + 1);
    }
    
    public double getLeft() {
        return getDXPos() - (double)this.getImage().getWidth() / 2 + 1;
    }
    public void setLeft(double xPos) {
        setDXPos(xPos + (double)this.getImage().getWidth() / 2 - 1);
    }
    
    public double getRight() {
        return getDXPos() + (double)this.getImage().getWidth() / 2;
    }
    public void setRight(double xPos) {
        setDXPos(xPos - (double)this.getImage().getWidth() / 2);
    }
    
    public void activateDebugMode(Color color) {
        this.getImage().setColor(color);
        this.getImage().fillRect(-10, -10, this.getImage().getWidth() +10 , this.getImage().getHeight() + 10);
    }
    
    public int getGround() {
        return this.getWorld().getHeight();
    }
    
    public boolean isTouchingGround() {
        return this.getBottom() == getGround();
    }
    
    public boolean isBelowGround() {
        return this.getBottom() > getGround();
    }
    
    public boolean isIntersectingX(double xPos) {
        return this.getLeft() <= xPos && this.getRight() >= xPos;
    }
    
    public boolean isIntersectingObjX(double left, double right) {
        return isIntersectingX(left) || isIntersectingX(right) 
        || (left < this.getLeft() && right > this.getRight());
    }
    
    public boolean isIntersectingY(double yPos) {
        return this.getTop() <= yPos && this.getBottom() >= yPos;
    }
    
    public boolean isIntersectingObjY(double top, double bottom) {
        return isIntersectingY(top) || isIntersectingY(bottom) 
        || (top < this.getTop() && bottom > this.getBottom());
    }
    
    public boolean isIntersectingObj(PrecisePosObj obj) {
        return isIntersectingObjX(obj.getLeft(), obj.getRight())
        && isIntersectingObjY(obj.getTop(), obj.getBottom());
    }
}
