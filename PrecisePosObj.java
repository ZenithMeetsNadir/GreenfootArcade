import greenfoot.*;

public abstract class PrecisePosObj extends Actor implements IPrecisePosObj
{
    private double _dXPos = 0;
    private double _dYPos = 0;
    
    public PrecisePosObj() {
        super();
    }
    
    public PrecisePosObj(double xPos, double yPos) {
        setLeft(xPos);
        setTop(yPos);
    }
    
    public void activateDebugMode(Color color) {
        this.getImage().setColor(color);
        this.getImage().fillRect(0, 0, (int)this.getWidth(), (int)this.getHeight());
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
    
    @Override
    public String toString() {
        return this.getDXPos() + "; " + this.getDYPos();
    }
}
