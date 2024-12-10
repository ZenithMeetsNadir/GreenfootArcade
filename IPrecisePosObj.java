import greenfoot.*;  

public interface IPrecisePosObj
{
    double getDXPos();
    void setDXPos(double value);
    
    double getDYPos();
    void setDYPos(double value);
    
    double getWidth();
    double getHeight();
    
    public int getGround();
    
    default void setPos(double xPos, double yPos) {
        setDXPos(xPos);
        setDYPos(yPos);
    }
    
    default double getTop() {
        return getDYPos() - getHeight() / 2;
    }
    default void setTop(double yPos) {
        setDYPos(yPos + getHeight() / 2);
    }
    
    default double getBottom() {
        return getDYPos() + getHeight() / 2 - 1;
    }
    default void setBottom(double yPos) {
        setDYPos(yPos - getHeight() / 2 + 1);
    }
    
    default double getLeft() {
        return getDXPos() - getWidth() / 2 + 1;
    }
    default void setLeft(double xPos) {
        setDXPos(xPos + getWidth() / 2 - 1);
    }
    
    default double getRight() {
        return getDXPos() + getWidth() / 2;
    }
    default void setRight(double xPos) {
        setDXPos(xPos - getWidth() / 2);
    }
    
    default boolean isTouchingGround() {
        return this.getBottom() == getGround();
    }
    
    default boolean isBelowGround() {
        return this.getBottom() > getGround();
    }
    
    default boolean isIntersectingX(double xPos) {
        return this.getLeft() <= xPos && this.getRight() >= xPos;
    }
    
    default boolean isIntersectingObjX(double left, double right) {
        return isIntersectingX(left) || isIntersectingX(right) 
        || (left < this.getLeft() && right > this.getRight());
    }
    
    default boolean isIntersectingY(double yPos) {
        return this.getTop() <= yPos && this.getBottom() >= yPos;
    }
    
    default boolean isIntersectingObjY(double top, double bottom) {
        return isIntersectingY(top) || isIntersectingY(bottom) 
        || (top < this.getTop() && bottom > this.getBottom());
    }
    
    default boolean isIntersectingObj(PrecisePosObj obj) {
        return isIntersectingObjX(obj.getLeft(), obj.getRight())
        && isIntersectingObjY(obj.getTop(), obj.getBottom());
    }
}
