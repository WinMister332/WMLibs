/*
 * ==========
 * WMPlugin.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/10/01 @ 14:15:05 PM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs;

import net.winmister332.libs.commands.CommandForwarder;
import net.winmister332.libs.commands.CommandInvoker;
import net.winmister332.libs.commands.CommandProperties;
import net.winmister332.libs.events.Event;
import net.winmister332.libs.events.EventArgs;
import net.winmister332.libs.events.XEventArgs;
import net.winmister332.libs.integration.papi.PAPIIntegrator;
import net.winmister332.libs.logging.Logger;
import net.winmister332.libs.logging.VerbosityLevel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/***
 * This is the base class for all WinMister plugins.
 */
public abstract class WMPlugin extends JavaPlugin
{
    private Logger wmLogger;
    private CommandInvoker invoker;

    //TODO
    /**
     * This event is run when a command is executed.
     */
    //public Event<CommandForwarder.CommandEventArgs> commandExecutedEvent = new Event<>();

    /**
     * This event is triggered when the plugin is enabled.
     */
    public Event<XEventArgs<Boolean>> enabledEvent = new Event<>();

    /**
     * This event is triggered when the plugin is disabled.
     */
    public Event<XEventArgs<Boolean>> disabledEvent = new Event<>();

    /**
     * This event is triggered when the plugin is loaded.
     */
    public Event<EventArgs> loadEvent = new Event<>();

    /**
     * This event is triggered on every first initial load of the plugin. This event is ran before the load event, and this event does NOT run during reload.
     */
    public Event<EventArgs> firstLoad = new Event<>();

    //public Event<EventArgs> reloadEvent = new Event<>();

    /**
     * The main CTOR for the WMPlugin class.
     *
     * @param debug   If true, it'll allow the debug state in the WMLogger.
     * @param logFile if not null, a log file at the specified location will be created and written to.
     * @param level   Denotes the verbosity level of the WMLogger.
     */
    protected WMPlugin(boolean debug, File logFile, VerbosityLevel level)
    {
        try
        {
            wmLogger = new Logger(this, getPluginMeta().getDisplayName(), debug, false, level, logFile);
        }
        catch (Exception ex)
        {

        }

        invoker = CommandInvoker.getInstance();
    }

    /**
     * The main CTOR for the WMPlugin class.
     */
    public WMPlugin()
    {
        this(false, null, VerbosityLevel.ALL);
    }

    /**
     * Gets the WMLogger instance associated with this plugin/class.
     *
     * @return The @see Logger instance associated with this plugin.
     */
    public Logger getWmLogger() { return wmLogger; }

    /**
     * Gets the CommandInvoker of the WM Commands.
     *
     * @return The @see CommandInvoker class.
     */
    public CommandInvoker getInvoker() { return invoker; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return CommandForwarder.getInstance().invokeCommand(command, sender, args, label);
    }

    private XEventArgs<Boolean> getEnabledEventArgs() { return new XEventArgs<>(isEnabled()); }

    @Override
    public void onDisable() {
        disabledEvent.invoke(this, getEnabledEventArgs());
    }

    private boolean doOnce = true;
    @Override
    public void onLoad() {
        try
        {
            if (doOnce)
            {
                firstLoad.invoke(this, EventArgs.getEmpty());
                doOnce = false;
            }
            loadEvent.invoke(this, EventArgs.getEmpty());
        }
        catch (Exception ex)
        {
            getWmLogger().logError("Could not load plugin: \"" + getPluginMeta().getDisplayName() + "\"...", ex);
            setEnabled(false);
        }
    }

    @Override
    public void onEnable() {
        enabledEvent.invoke(this, getEnabledEventArgs());
    }
}
