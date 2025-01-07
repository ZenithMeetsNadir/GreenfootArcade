import greenfoot.*;

public interface IAnimateColBlock extends ICollisionBlock, IAnimateObj 
{
    default boolean hasMoved() {
        return getXPosDiff() == 0 && getYPosDiff() == 0;
    }
    
    void setUpCollision();
}
