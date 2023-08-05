package net.exotia.plugins.market.inventories.abstractions;

import eu.okaeri.configs.OkaeriConfig;
import net.exotia.plugins.market.inventories.BaseItem;

import java.util.HashMap;

public abstract class AbstractInventoryConfiguration extends OkaeriConfig {
    public abstract String[] getPattern();
    public abstract String getTitle();
    public abstract HashMap<Character, BaseItem> getItems();
}
