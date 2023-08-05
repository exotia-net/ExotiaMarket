package net.exotia.plugins.market.inventories;

import net.exotia.developer.kit.core.PluginFactory;
import net.exotia.plugins.market.inventories.abstractions.AbstractInventory;

import java.util.HashMap;

public class InventoryService {
    private final HashMap<Class<?>, AbstractInventory<?>> cachedInventories = new HashMap<>();
    private final PluginFactory pluginFactory;

    public InventoryService(PluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
    }

    public InventoryService withInventory(AbstractInventory<?> baseInventory) {
        this.pluginFactory.configFile(baseInventory.configurationClass(), this.fileName(baseInventory), baseInventory::setConfiguration);
        baseInventory.setInventoryService(this);
        this.cachedInventories.put(baseInventory.inventoryClass(), baseInventory);
        return this;
    }

    public AbstractInventory<?> inventory(Class<? extends AbstractInventory<?>> abstractInventory) {
        return this.cachedInventories.get(abstractInventory);
    }

    private String fileName(AbstractInventory<?> baseInventory) {
        return String.format("inventories/%s.yml", baseInventory.inventoryId());
    }
}
