import greenfoot.*;  

public interface IAnimateObj extends IPrecisePosObj
{      
    void setDXPosOmitCheck(double xPos);
    double getDXPosFrame();
    
    void setDYPosOmitCheck(double yPos);
    double getDYPosFrame();
    
    default void refreshReqPos() {
        setDXPos(getDXPosFrame());
        setDYPos(getDYPosFrame());
    }
    
    default void setPosOmitCheck(double xPos, double yPos) {
        setDXPosOmitCheck(xPos);
        setDYPosOmitCheck(yPos);
    }
    
    double getTopFrame();
    void setTopOmitCheck(double yPos);
    
    double getBottomFrame();
    void setBottomOmitCheck(double yPos);
    
    double getLeftFrame();
    void setLeftOmitCheck(double xPos);
    
    double getRightFrame();
    void setRightOmitCheck(double xPos);
    
    /**
     * @return positive number for right
     */
    default double getXPosDiff() {
        return getDXPos() - getDXPosFrame();
    }
    
    /**
     * @return positive number for down
     */
    default double getYPosDiff() {
        return getDYPos() - getDYPosFrame();
    }
    
    void processRPos();
}
