import greenfoot.*;  

public interface IAnimateObject extends IPrecisePosObj
{
    default void setDXPosOmitCheck(double xPos) {
        this.setDXPos(xPos);
        super.setDXPos(xPos);
    }
    default double getDXPosFrame() {
        return super.getDXPos();
    }
    
    default void setDYPosOmitCheck(double yPos) {
        this.setDYPos(yPos);
        super.setDYPos(yPos);
    }
    default double getDYPosFrame() {
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
}
