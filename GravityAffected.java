import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class GravityAffected extends PrecisePosObj
{
    private boolean _isStanding = false;
    private double _gravity = 0;
    
    protected int gravityCap = 20;
    protected double g = 10;
    
    public boolean isGravityAffected = true;
    
    public static final int avgFps = 60;
    
    public GravityAffected() {
        super();
    }
    
    public GravityAffected(double xPos, double yPos) {
        super(xPos, yPos);
    }
    
    public boolean getIsStanding() {
        return _isStanding;
    }
    public void setIsStanding(boolean value) {
        _isStanding = value;
        
        if (value == true)
            setGravity(0);
    }
    
    public double getGravity() {
        return _gravity;
    }
    public void setGravity(double value) {
        _gravity = value;
        
        if (value != 0)
            setIsStanding(false);
    }
    
    public void startFalling() {
        this.setIsStanding(false);
        this.setGravity(0);
    }
    
    protected void computeGravity() {
        setGravity(getGravity() + g / avgFps);
    
        if (getGravity() > gravityCap)
            setGravity(gravityCap); 
    }
    
    public void act() {  
        if (isGravityAffected) {
                if (!getIsStanding()) {
                computeGravity();
                this.setDYPos(this.getDYPos() + getGravity());
            }
        }
    }
}
