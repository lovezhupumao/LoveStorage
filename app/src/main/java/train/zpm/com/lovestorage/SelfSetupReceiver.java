package train.zpm.com.lovestorage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/*
开机自启动程序，是一个广播，当开机时便会运行该程序
 */
public class SelfSetupReceiver extends BroadcastReceiver {

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals(ACTION)) {
            Intent mainActivityIntent = new Intent(context, TimerWidgetService.class);  // 要启动的Activity
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(mainActivityIntent);
        }

    }
}
