package net.exotia.plugins.market.inventories;

import eu.okaeri.configs.OkaeriConfig;
import net.exotia.developer.kit.utils.items.ExotiaItem;

import java.util.List;

public class BaseItem extends OkaeriConfig {
    public ExotiaItem item;
    public List<String> actions;

    public BaseItem(ExotiaItem item, List<String> actions) {
        this.item = item;
        this.actions = actions;
    }
}
