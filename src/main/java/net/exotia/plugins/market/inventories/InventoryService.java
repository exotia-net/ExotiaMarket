package net.exotia.plugins.market.inventories;

import net.exotia.developer.kit.core.PluginFactory;
import net.exotia.plugins.market.inventories.abstractions.AbstractInventory;
import net.exotia.plugins.market.inventories.abstractions.AbstractInventoryConfiguration;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private final List<AbstractInventory<?>> cachedInventories = new ArrayList<>();
    private final List<AbstractInventoryConfiguration> configurations = new ArrayList<>();
    private final PluginFactory pluginFactory;

    public InventoryService(PluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
    }

    public InventoryService withInventory(AbstractInventory<?> baseInventory) {
        this.pluginFactory.configFile(baseInventory.configurationClass(), this.fileName(baseInventory), config -> {
            this.configurations.add(config);
            baseInventory.setConfiguration(config);
        });
        baseInventory.setInventoryService(this);
        this.cachedInventories.add(baseInventory);
        return this;
    }

    private String fileName(AbstractInventory<?> baseInventory) {
        return String.format("inventories/%s.yml", baseInventory.inventoryId());
    }
}
