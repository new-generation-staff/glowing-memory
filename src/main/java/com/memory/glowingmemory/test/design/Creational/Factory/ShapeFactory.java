package com.memory.glowingmemory.test.design.Creational.Factory;

/**
 * @author zc
 */
public class ShapeFactory {
    /**
     * Ignore Case
     *
     * @param shapeType
     * @return
     */
    private static final String RECTANGLE = "RECTANGLE";
    private static final String SQUARE = "SQUARE";

    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        Shape shape = null;
        switch (shapeType.toUpperCase()) {
            case RECTANGLE:
                shape = new Rectangle();
                break;
            case SQUARE:
                shape = new Square();
                break;
            default:
                break;
        }
        return shape;
    }
}

class FactoryPatternDemo {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape rectangle = shapeFactory.getShape("square");
        rectangle.draw();
    }
}