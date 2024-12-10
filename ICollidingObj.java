import greenfoot.*;
import java.util.*;

public interface ICollidingObj extends IAnimateObj
{
    default double getVectorHorXIntersection(double horYPos, double frameX, double frameY) {
        return getXPosDiff() / getYPosDiff() 
        * (horYPos - frameY) + frameX;
    }
    
    default double getVectorVertYIntersection(double vertXPos, double frameX, double frameY) {
        return getYPosDiff() / getXPosDiff()
        * (vertXPos - frameX) + frameY;
    }
    
    /**
     * PROSIMTĚ UŽ NA TO NEŠAHEJ TY DEBILE!!!
     * @param colObj kolizní objekt
     * @return strana kolizního objektu, kde nastane kolize
     */
    default Optional<Direction> findDominantIntersection(CollisionObj colObj) {
        double xDir = getXPosDiff();      
        double yDir = getYPosDiff();
        
        if (yDir == 0) {
            if (xDir < 0)
                return Optional.of(Direction.right);
            else if (xDir > 0)
                return Optional.of(Direction.left);
        }
        else if (xDir == 0) {
            if (yDir < 0) 
                return Optional.of(Direction.down);       
            else if (yDir > 0) 
                return Optional.of(Direction.up);
        }
        
        double frameX = 0;
        double frameY = 0;
        
        if (xDir < 0)
            frameX = this.getLeftFrame();
        else if (xDir > 0) 
            frameX = this.getRightFrame();

            
        if (yDir < 0)
            frameY = this.getTopFrame();
        else if (yDir > 0)
            frameY = this.getBottomFrame();
        
        double topXIntersection = getVectorHorXIntersection(colObj.getTop(), frameX, frameY);
        double bottomXIntersection = getVectorHorXIntersection(colObj.getBottom(), frameX, frameY);
        
        double leftYIntersection = getVectorVertYIntersection(colObj.getLeft(), frameX, frameY);
        double rightYIntersection = getVectorVertYIntersection(colObj.getRight(), frameX, frameY);
        
        double xTolerance = xDir / Math.abs(xDir) * getWidth();
        double yTolerance = yDir / Math.abs(yDir) * getHeight();
        
        if (colObj.isIntersectingObjX(getLeftFrame(), getRightFrame())) {
            if (yDir > 0 
            && (colObj.isIntersectingX(topXIntersection)
            || colObj.isIntersectingX(topXIntersection - xTolerance))) {
                return Optional.of(Direction.up);
            }
            else if (yDir < 0 
            && (colObj.isIntersectingX(bottomXIntersection)
            || colObj.isIntersectingX(bottomXIntersection - xTolerance))) {
                return Optional.of(Direction.down);
            }
        }
        else {
            if (yDir > 0) {
                if (colObj.isIntersectingX(topXIntersection)
                || colObj.isIntersectingX(topXIntersection - xTolerance)) {
                    return Optional.of(Direction.up);
                }
                else if (xDir > 0
                && (colObj.isIntersectingY(leftYIntersection)
                || colObj.isIntersectingY(leftYIntersection - getHeight()))) {
                    return Optional.of(Direction.left);
                }
                else if (xDir < 0
                && (colObj.isIntersectingY(rightYIntersection)
                || colObj.isIntersectingY(rightYIntersection - getHeight()))) {
                    return Optional.of(Direction.right);    
                }
            }
            else if (yDir < 0) {
                if (colObj.isIntersectingX(bottomXIntersection)
                || colObj.isIntersectingX(topXIntersection - xTolerance)) {
                    return Optional.of(Direction.down);
                }
                else if (xDir > 0
                && (colObj.isIntersectingY(leftYIntersection)
                || colObj.isIntersectingY(leftYIntersection + getHeight()))) {
                    return Optional.of(Direction.left);
                }
                else if (xDir < 0 
                && (colObj.isIntersectingY(rightYIntersection)
                || colObj.isIntersectingY(rightYIntersection + getHeight()))) {
                    return Optional.of(Direction.right);
                }
            }
        }
        
        if (colObj.isIntersectingObjY(getTopFrame(), getBottomFrame())) {
            if (xDir > 0 
            && (colObj.isIntersectingY(leftYIntersection)
            || colObj.isIntersectingY(leftYIntersection - yTolerance))) {
                return Optional.of(Direction.left);
            }
            else if (xDir < 0 
            && (colObj.isIntersectingY(rightYIntersection)
            || colObj.isIntersectingY(rightYIntersection - yTolerance))) {
                return Optional.of(Direction.right);
            }
        }
        else {
            
        }
        
        return Optional.empty();
    }
    
    default void landOnTop(CollisionObj colObj) {
        this.setBottom(colObj.getTop() - 1);
    }
    
    default void landOnBottom(CollisionObj colObj) {
        this.setTop(colObj.getBottom() + 1);
    }
    
    default void landOnLeft(CollisionObj colObj) {
        this.setRight(colObj.getLeft() - 1);
    }
    
    default void landOnRight(CollisionObj colObj) {
        this.setLeft(colObj.getRight() + 1);
    }
    
    default void resolveLandingDirection(Optional<Direction> dir, CollisionObj colObj) {
        try {
            switch (dir.get()) {
                case up:
                    landOnTop(colObj);
                    break;
                case down:
                    landOnBottom(colObj);
                    break;
                case left:
                    landOnLeft(colObj);
                    break;
                case right:
                    landOnRight(colObj);
                    break;
            }
        } 
        catch (NoSuchElementException e) { }
    }
}
