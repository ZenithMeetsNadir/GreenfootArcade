import greenfoot.*;

public abstract class AnimateObj extends PrecisePosObj implements IAnimateObj
{
    private double requestedDXPos = 0;
    private double requestedDYPos = 0;
    
    public AnimateObj() {
        super();
    }
    
    public AnimateObj(double xPos, double yPos) {
        super(xPos, yPos);
    }
    
    @Override
    public void setDXPos(double xPos) {
        requestedDXPos = xPos;
    }
    @Override
    public double getDXPos() {
        return requestedDXPos;
    }
    
    @Override
    public void setDYPos(double yPos) {
        requestedDYPos = yPos;
    }
    @Override
    public double getDYPos() {
        return requestedDYPos;
    }
    
    @Override
    public void setDXPosOmitCheck(double xPos) {
        this.setDXPos(xPos);
        super.setDXPos(xPos);
    }
    @Override
    public double getDXPosFrame() {
        return super.getDXPos();
    }
    
    @Override
    public void setDYPosOmitCheck(double yPos) {
        this.setDYPos(yPos);
        super.setDYPos(yPos);
    }
    @Override
    public double getDYPosFrame() {
        return super.getDYPos();
    }
    
    @Override
    public double getTopFrame() {
        return super.getTop();
    }
    @Override
    public void setTopOmitCheck(double yPos) {
        super.setTop(yPos);
        refreshReqPos();
    }
    
    @Override
    public double getBottomFrame() {
        return super.getBottom();
    }
    @Override
    public void setBottomOmitCheck(double yPos) {
        super.setBottom(yPos);
        refreshReqPos();
    }
    
    @Override
    public double getLeftFrame() {
        return super.getLeft();
    }
    @Override
    public void setLeftOmitCheck(double xPos) {
        super.setLeft(xPos);
        refreshReqPos();
    }
    
    @Override
    public double getRightFrame() {
        return super.getRight();
    }
    @Override
    public void setRightOmitCheck(double xPos) {
        super.setRight(xPos);
        refreshReqPos();
    }
}
