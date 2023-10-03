/*
 * ==========
 * XEventArgs.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332, Faua
 * TimeCreated: 2023/09/27 @ 24:15:02 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.events;

public class XEventArgs<X> extends EventArgs
{
    private X value;

    public XEventArgs(X x)
    {
        value = x;
    }

    public X getValue() { return value; }
}
