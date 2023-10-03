/*
 * ==========
 * EventAction.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332, Faua
 * TimeCreated: 2023/09/27 @ 24:14:28 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.events;

/***
 * Handles the invokation of an event. An EventAction is just an C#-like action which takes an EventArgs as a value.
 * @param <T>
 */
@FunctionalInterface
public interface EventAction<T extends EventArgs>{
    void invoke(Object sender, T e);
}
