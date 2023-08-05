package net.exotia.plugins.market.inventories.main;

import net.exotia.plugins.market.inventories.abstractions.AbstractInventory;

public class MainInventory extends AbstractInventory<MainInventoryConfiguration> {

    public MainInventory() {
        super(MainInventoryConfiguration.class);
    }
    @Override
    protected String inventoryId() {
        return "main";
    }
}
