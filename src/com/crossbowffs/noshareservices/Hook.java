package com.crossbowffs.noshareservices;

import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {
    public static final String TAG = "NoShareServices";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        try {
            XposedHelpers.findClass("com.android.internal.app.ChooserActivity", loadPackageParam.classLoader);
        } catch (XposedHelpers.ClassNotFoundError e) {
            return;
        }

        XposedHelpers.findAndHookMethod(
            "com.android.internal.app.ChooserActivity", loadPackageParam.classLoader,
            "queryTargetServices", "com.android.internal.app.ChooserActivity$ChooserListAdapter",
            new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.i(TAG, "Blocked ChooserActivity#queryTargetServices");
                    param.setResult(null);
                }
            });
    }
}
