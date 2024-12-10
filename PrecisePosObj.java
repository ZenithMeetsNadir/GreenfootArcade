import greenfoot.*;

public abstract class PrecisePosObj extends Actor implements IPrecisePosObj
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

    public double getWidth() {
        return (double)this.getImage().getWidth();
    }

    public double getHeight() {
        return (double)this.getImage().getHeight();
    }
    
    public int getGround() {
        return this.getWorld().getHeight();
    }
}
