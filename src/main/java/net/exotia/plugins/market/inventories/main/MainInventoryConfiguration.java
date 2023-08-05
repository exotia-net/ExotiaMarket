package net.exotia.plugins.market.inventories.main;

import net.exotia.plugins.market.inventories.BaseItem;
import net.exotia.plugins.market.inventories.abstractions.AbstractInventoryConfiguration;

import java.util.HashMap;

public class MainInventoryConfiguration extends AbstractInventoryConfiguration {
    @Override
    public String[] getPattern() {
        return new String[0];
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public HashMap<Character, BaseItem> getItems() {
        return null;
    }
}
