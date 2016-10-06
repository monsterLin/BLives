package com.monsterlin.blives.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * 网络监测
 * Created by monsterLin on 2016/4/24.
 */
public class CheckNetWork {

    private MaterialDialog mMaterialDialog;

    /**
     * 检测网络连接
     *
     * @param con
     * @return
     */
    public boolean isNetworkAvailable(Context con) {
        ConnectivityManager cm = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo == null) {
            return false;
        }
        if (netinfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 提示设置网络连接对话框
     *
     * @param context
     */
    public void showNetDialog(final Context context) {
        mMaterialDialog = new MaterialDialog(context)

                .setMessage("世界上最遥远的距离就是没网")
                .setPositiveButton("检查设置", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = null;
                        try {
                            @SuppressWarnings("deprecation")
                            String sdkVersion = android.os.Build.VERSION.SDK;
                            if (Integer.valueOf(sdkVersion) > 10) {
                                intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            } else {
                                intent = new Intent();
                                ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                                intent.setComponent(comp);
                                intent.setAction("android.intent.action.VIEW");
                            }
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mMaterialDialog.dismiss();

                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();

    }
}
