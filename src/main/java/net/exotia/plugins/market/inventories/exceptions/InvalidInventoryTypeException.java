package net.exotia.plugins.market.inventories.exceptions;

public class InvalidInventoryTypeException extends RuntimeException {
    public InvalidInventoryTypeException() {
        super("GuiType is invalid!");
    }
}
