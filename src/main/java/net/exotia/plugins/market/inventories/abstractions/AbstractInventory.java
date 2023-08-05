package net.exotia.plugins.market.inventories.abstractions;

import net.exotia.plugins.market.inventories.InventoryService;

public abstract class AbstractInventory<CONFIGURATION extends AbstractInventoryConfiguration> {
    protected CONFIGURATION configuration;
    private final Class<CONFIGURATION> configurationClass;
    protected InventoryService inventoryService;

    public AbstractInventory(Class<CONFIGURATION> configurationClass) {
        this.configurationClass = configurationClass;
    }

    public abstract String inventoryId();

    public void setConfiguration(AbstractInventoryConfiguration configuration) {
        this.configuration = (CONFIGURATION) configuration;
    }
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public Class<CONFIGURATION> configurationClass() {
        return this.configurationClass;
    }
}
