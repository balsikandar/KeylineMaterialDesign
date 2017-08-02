package com.balsikandar.android.keylinematerialdesign;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by bal sikandar on 30/07/17.
 */

public class KeylineUtil {

    private KeylineUtil() {
        // This utility class is not publicly instantiable.
    }

    //100 full transparent zero full opaque
    public enum TRANSPARENCY {
        HUNDRED, NINETY, EIGHTY, SEVENTY , SIXTY, FIFTY, FOURTY, THIRTY,
        TWENTY, TEN, ZERO
    }

    public enum GRIDSIZE {
        FOURDP, EIGHTDP, TWELVEDP, SIXTEENDP, TWENTYDP, TWENTYFOURDP
    }

    public enum LINE {
        HORIZONTAL, VERTICAL, BOTH
    }

    public static int dpToPx(float dp, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static boolean isServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
