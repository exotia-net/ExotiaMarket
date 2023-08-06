package net.exotia.plugins.market.configuration;

import eu.okaeri.configs.OkaeriConfig;
import net.exotia.developer.kit.core.configuration.sections.LiteCommandsSection;

import java.util.List;

public class MessagesConfiguration extends OkaeriConfig {
    public LiteCommandsSection liteCommands = new LiteCommandsSection();
    public List<String> playerAddedItem = List.of(
            "",
            "鸼 Gracz  <gradient:#ffe327:#efb029><b>{player}</b> <white>wystawil przedmiot na <gradient:#ffe327:#efb029><bold>/rynek",
            ""
    );
    public String itemIsNull = "鸼 <white>Musisz <gradient:#ffe327:#efb029><bold>trzymać</bold></gradient> jakis <gradient:#ffe327:#efb029><bold>przedmiot</bold></gradient> w rece!";
    public String servicesLimitReached = "鸼 <white>Osiagnieto <gradient:#ffe327:#efb029><bold>limit</bold></gradient> wystawionych <gradient:#ffe327:#efb029><bold>przedmiotów!</bold></gradient>";
    public String itemSuccessfullyAdded = "鸼 <white>Przedmiot został <gradient:#ffe327:#efb029><bold>pomyślnie</bold></gradient> dodany na <gradient:#ffe327:#efb029><bold>rynek!</bold></gradient>";
    public String itemBought = "鸼 <white>Pomyślnie zakupiono <gradient:#ffe327:#efb029><bold>przedmiot!";
    public String itemBoughtNotify = "鸼 <white>Gracz <gradient:#ffe327:#efb029><bold>{player}</bold></gradient> zakupił item za <gradient:#ffe327:#efb029><bold>{price}";
    public String collectedAllItems = "鸼 <white>Zebrałeś wszystkie itemy z magazynu!";
}
