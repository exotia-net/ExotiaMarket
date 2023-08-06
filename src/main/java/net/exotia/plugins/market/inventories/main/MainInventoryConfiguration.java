package net.exotia.plugins.market.inventories.main;

import net.exotia.developer.kit.utils.items.ExotiaItem;
import net.exotia.developer.kit.utils.items.ExotiaItemBuilder;
import net.exotia.plugins.market.inventories.BaseItem;
import net.exotia.plugins.market.inventories.abstractions.AbstractInventoryConfiguration;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainInventoryConfiguration extends AbstractInventoryConfiguration {
    private String title = "Rynek";
    private List<String> pattern = Arrays.asList(
            "# # # # # # # # #",
            "# x x x x x x x #",
            "# x x x x x x x #",
            "# x x x x x x x #",
            "# # < # s # > # #"
    );
    private HashMap<Character, BaseItem> items = new HashMap<>(){{
        put('#', new BaseItem(new ExotiaItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("&7").build(), List.of("")));
    }};

    public List<String> viewItemLore = List.of(
            "",
            "&fWłasciciel: <gradient:#4fa943:#9ec52f>{owner}",
            "&fCena: <gradient:#4fa943:#9ec52f>{price}",
            "",
            "&fData dodania: <gradient:#4fa943:#9ec52f>{created}",
            "&fData wygaśniecia: <gradient:#4fa943:#9ec52f>{expireIn}",
            "",
            "&f&oKliknij aby <gradient:#4fa943:#9ec52f><b>kupić</b></gradient> &ften przedmiot",
            ""
    );

    @Override
    public String[] getPattern() {
        return this.pattern.toArray(String[]::new);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public HashMap<Character, BaseItem> getItems() {
        return this.items;
    }
}
