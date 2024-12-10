import greenfoot.*;

public class AnimateObj extends PrecisePosObj
{
    private double requestedDXPos = 0;
    private double requestedDYPos = 0;
    
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
    
    public void act()
    {
        
    }
}
