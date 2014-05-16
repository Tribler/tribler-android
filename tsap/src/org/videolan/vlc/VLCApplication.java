/*****************************************************************************
 * VLCApplication.java
 *****************************************************************************
 * Copyright © 2010-2013 VLC authors and VideoLAN
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/
package org.videolan.vlc;

//import org.videolan.vlc.gui.audio.AudioUtil;

import android.content.Context;
import android.content.res.Resources;

/**
 * 
 * @author dirk
 * stub class of VLCapplication. It returns the context of the real application. Make sure you set this in time with the setContext function.
 */
public class VLCApplication{
    public final static String TAG = "VLC/VLCApplication";
    private static Context context;

    public final static String SLEEP_INTENT = "org.videolan.vlc.SleepIntent";
    
    public static void setContext(Context c)
    {
    	context = c;
    }

    public static Context getAppContext()
    {
        return context;
    }

    /**
     * @return the main resources from the Application
     */
    public static Resources getAppResources()
    {
        if(context == null) return null;
        return context.getResources();
    }
}
