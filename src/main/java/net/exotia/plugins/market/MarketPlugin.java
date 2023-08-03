package net.exotia.plugins.market;

import net.exotia.developer.kit.core.ExotiaPlugin;
import net.exotia.developer.kit.core.PluginFactory;
import net.exotia.developer.kit.utils.items.ExotiaItemBuilder;
import net.exotia.plugins.market.commands.MarketCommand;
import net.exotia.plugins.market.configuration.MessagesConfiguration;
import net.exotia.plugins.market.configuration.PluginConfiguration;
import net.exotia.plugins.market.database.DatabaseService;
import net.exotia.plugins.market.models.MarketItem;
import net.exotia.plugins.market.models.enums.ItemState;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.UUID;

public class MarketPlugin extends JavaPlugin {
    private MessagesConfiguration messagesConfiguration;
    private PluginConfiguration pluginConfiguration;

    @Override
    public void onEnable() {
        var pluginFactory = PluginFactory.create(this)
                .configFile(MessagesConfiguration.class, "messages.yml", produce -> this.messagesConfiguration = produce)
                .configFile(PluginConfiguration.class, "configuration.yml", produce -> this.pluginConfiguration = produce);

        DatabaseService databaseService = new DatabaseService();

        pluginFactory.liteCommands(this.messagesConfiguration.liteCommands, builder -> { builder
                .commandInstance(new MarketCommand());
        });
        ExotiaPlugin exotiaPlugin = pluginFactory.produce();
        exotiaPlugin.bootstrap();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
