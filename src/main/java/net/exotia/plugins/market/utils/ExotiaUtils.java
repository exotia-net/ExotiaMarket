package net.exotia.plugins.market.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;
import java.util.function.Consumer;

public class ExotiaUtils {
    public static Component of(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }
    public static void sendList(List<Component> messages, Consumer<Component> send) {
        messages.forEach(send);
    }
}
