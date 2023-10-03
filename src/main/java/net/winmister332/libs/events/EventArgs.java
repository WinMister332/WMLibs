/*
 * ==========
 * EventArgs.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332, Faua
 * TimeCreated: 2023/09/27 @ 24:14:45 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.events;

public class EventArgs
{
    /***
     * Get an empty instance of EventArgs.
     * @return An empty instance.
     * @param <T>
     */
    public static <T extends EventArgs> T getEmpty()
    {
        return (T)new EventArgs();
    }

    /***
     * Creates a new instance of the XEventArgs class which takes a default value.
     * @param x The value.
     * @return XEventArgs
     * @param <T> The XEventArgs type.
     * @param <X> The type of the object 'x'.
     */
    public static  <T extends EventArgs, X> T create(X x)
    {
        EventArgs ea = (EventArgs) new XEventArgs<X>(x);
        return (T)ea;
    }
}
