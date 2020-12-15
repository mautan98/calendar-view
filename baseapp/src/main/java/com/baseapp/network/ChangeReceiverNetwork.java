package com.baseapp.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.baseapp.utils.ConfigUltils;
import com.baseapp.utils.NetworkUtil;


/**
 * Created by vn on 12/20/2016.
 */

public class ChangeReceiverNetwork extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equalsIgnoreCase(intent.getAction())
                || WifiManager.WIFI_STATE_CHANGED_ACTION.equalsIgnoreCase(intent.getAction())) {

        }
        switch (intent.getAction()) {
            case ConnectivityManager.CONNECTIVITY_ACTION:
                boolean status = NetworkUtil.isNetworkAvailable(context);
                Log.e("network change", "Connect: " + status);
                if (status) {
                    Intent network = new Intent(ConfigUltils.Network.ACTION_CHANGE_NETWORK);
                    network.putExtra(ConfigUltils.Network.CHANGE_NETWORK, true);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(network);
                } else {
                    Intent disconnect = new Intent(ConfigUltils.Network.ACTION_NETWORK_DISCONNECT);
                    disconnect.putExtra(ConfigUltils.Network.CHANGE_NETWORK, false);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(disconnect);
                }
                break;
            case WifiManager.WIFI_STATE_CHANGED_ACTION:
                break;
        }
    }
}
