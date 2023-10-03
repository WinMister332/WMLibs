/*
 * ==========
 * WMLib.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:13:21 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs;

import net.winmister332.libs.commands.CommandForwarder;
import net.winmister332.libs.events.Event;
import net.winmister332.libs.events.EventArgs;
import net.winmister332.libs.logging.LogStatus;
import net.winmister332.libs.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * The primary WMLib class. It contains plugin info for WMLibs and callers.
 */
public final class WMLib extends JavaPlugin {
    private static WMLib instance = null;

    /**
     * Get the instance of the WMLib class.
     *
     * @return instance instance
     */
    public static WMLib getInstance() { return instance; }

    private Logger logger;

    /***
     * This event is run when the plugin is loaded.
     */
    public Event<EventArgs> loadEvent = new Event<>((s, e) ->
    {
        logger.logInfo("Reached load event!");
    });
    /**
     * The Enabled event.
     */
    public Event<EventArgs> enabledEvent = new Event<>();
    /**
     * The Disabled event.
     */
    public Event<EventArgs> disabledEvent = new Event<>();

    @Override
    public void onLoad() {
        if (instance == null)
            instance = this;

        try {
            logger = new Logger(this, true, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadEvent.invoke(this, EventArgs.getEmpty());
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        enabledEvent.invoke(this, EventArgs.getEmpty());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        disabledEvent.invoke(this, EventArgs.getEmpty());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        return CommandForwarder.getInstance().invokeCommand(command, sender, args, label);
    }

    /**
     * Gets wm logger.
     *
     * @return the wm logger
     */
    public Logger getWMLogger()
    {
        return logger;
    }
}
