/*
 * ==========
 * PAPIIntegrator.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/29 @ 17:18:21 PM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.integration.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.winmister332.libs.events.Event;
import net.winmister332.libs.events.EventAction;
import net.winmister332.libs.events.EventArgs;
import net.winmister332.libs.events.XEventArgs;
import net.winmister332.libs.utils.Version;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public final class PAPIIntegrator
{
    public final class WMPAPIPlugin extends PlaceholderExpansion
    {
        public final class PapiPlaceholderRequestedEventArgs extends EventArgs
        {
            private String requestedPlaceholderName;
            private OfflinePlayer player;
            private String outputValue;

            public PapiPlaceholderRequestedEventArgs(String requestedPlaceholderName, OfflinePlayer player)
            {
                this.requestedPlaceholderName = requestedPlaceholderName;
            }

            public String getRequestedPlaceholder() { return requestedPlaceholderName; }
            public OfflinePlayer getPlayer() { return player; }

            public String getOutputValue() { return outputValue; }
            public void setOutputValue(String outputValue) { this.outputValue = outputValue; }
        }

        private JavaPlugin plugin;

        public Event<PapiPlaceholderRequestedEventArgs> placeholderRequested = new Event<>();

        public WMPAPIPlugin(JavaPlugin plugin)
        {
            this.plugin = plugin;
        }

        @Override
        public @NotNull String getIdentifier() {
            return plugin.getPluginMeta().getName().toLowerCase();
        }

        @Override
        public @NotNull String getAuthor() {
            return plugin.getPluginMeta().getAuthors().get(0);
        }

        @Override
        public @NotNull String getVersion() {
            return plugin.getPluginMeta().getVersion();
        }

        @Override
        public @Nullable String onRequest(OfflinePlayer player, @NotNull String params)
        {
            PapiPlaceholderRequestedEventArgs e = new PapiPlaceholderRequestedEventArgs(params, player);
            placeholderRequested.invoke(this, e);
            while (e.getOutputValue() == null)
            {
            }

            return e.getOutputValue() != null ? e.getOutputValue() : null;
        }
    }
}
