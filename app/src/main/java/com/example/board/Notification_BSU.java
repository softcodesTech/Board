package com.example.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
@SuppressWarnings("ALL")
public class Notification_BSU extends AppCompatActivity implements View.OnClickListener{

    String KEY_REPLY = "key_reply";
    String KEY_REPLY_HISTORY = "key_reply_history";
    public static final int NOTIFICATION_ID = 1;

    Button btnBasicInlineReply, btnInlineReplyHistory;
    TextView txtReplied;

    private static List<CharSequence> responseHistory = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification__b_s_u);
        btnBasicInlineReply = findViewById(R.id.btn_basic_inline_reply);
        btnInlineReplyHistory = findViewById(R.id.btn_inline_replies_with_history);
        txtReplied = findViewById(R.id.txt_inline_reply);
        btnBasicInlineReply.setOnClickListener(this);
        btnInlineReplyHistory.setOnClickListener(this);

        clearExistingNotifications();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processInlineReply(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_basic_inline_reply:
                createInlineNotification();
                break;

            case R.id.btn_inline_replies_with_history:

                if (!responseHistory.isEmpty()) {
                    CharSequence[] history = new CharSequence[responseHistory.size()];
                    createInlineNotificationWithHistory(responseHistory.toArray(history));
                } else {
                    createInlineNotificationWithHistory(null);
                }
                break;


        }
    }

    private void clearExistingNotifications() {
        int notificationId = getIntent().getIntExtra("notificationId", 0);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.cancel(notificationId);
    }

    @SuppressLint("SetTextI18n")
    private void processInlineReply(Intent intent) {

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        if (remoteInput != null) {
            CharSequence charSequence = remoteInput.getCharSequence(
                    KEY_REPLY);

            if (charSequence != null) {
                //Set the inline reply text in the TextView

                String reply = charSequence.toString();

                txtReplied.setText("Reply is " + reply);


                //Update the notification to show that the reply was received.
                NotificationCompat.Builder repliedNotification =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(
                                        android.R.drawable.stat_notify_chat)
                                .setContentText("Your comment is captured...");

                NotificationManager notificationManager =
                        (NotificationManager)
                                getSystemService(Context.NOTIFICATION_SERVICE);
                assert notificationManager != null;
                notificationManager.notify(NOTIFICATION_ID,
                        repliedNotification.build());


                /**Uncomment the below code to cancel the notification.
                 * Comment the above code too.
                 * **/
            /*NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(NOTIFICATION_ID);*/

            } else {

                String reply = Objects.requireNonNull(remoteInput.getCharSequence(KEY_REPLY_HISTORY)).toString();
                responseHistory.add(0, reply);
                if (!responseHistory.isEmpty()) {
                    CharSequence[] history = new CharSequence[responseHistory.size()];
                    createInlineNotificationWithHistory(responseHistory.toArray(history));
                } else {
                    createInlineNotificationWithHistory(null);
                }

            }

        }
    }

    private void createInlineNotification() {

        //Create notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Chaplaincy");

        String replyLabel = "Enter your comment here";

        //Initialise RemoteInput
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY)
                .setLabel(replyLabel)
                .build();


        int randomRequestCode = new Random().nextInt(54325);

        //PendingIntent that restarts the current activity instance.
        Intent resultIntent = new Intent(this, Home.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Set a unique request code for this pending intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, randomRequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        //Notification Action with RemoteInput instance added.
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                android.R.drawable.sym_action_chat, "REPLY", resultPendingIntent)
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build();

        //Notification.Action instance added to Notification Builder.
        builder.addAction(replyAction);

        Intent intent = new Intent(this, Home.class);
        intent.putExtra("notificationId", NOTIFICATION_ID);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent dismissIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "CLOSE", dismissIntent);

        //Create Notification.
        NotificationManager notificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.notify(NOTIFICATION_ID,
                builder.build());

    }

    private void createInlineNotificationWithHistory(CharSequence[] history) {

        //Create notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("All Comments");

        String replyLabel = "Comment on this Post";

        //Initialise RemoteInput
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY_HISTORY)
                .setLabel(replyLabel)
                .build();


        int randomRequestCode = new Random().nextInt(54325);

        //PendingIntent that restarts the current activity instance.
        Intent resultIntent = new Intent(this, Home.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Set a unique request code for this pending intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, randomRequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        //Notification Action with RemoteInput instance added.
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                android.R.drawable.sym_action_chat, "REPLY", resultPendingIntent)
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build();

        //Notification.Action instance added to Notification Builder.
        builder.addAction(replyAction);

        Intent intent = new Intent(this, Home.class);
        intent.putExtra("notificationId", NOTIFICATION_ID);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent dismissIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "CLOSE", dismissIntent);

        if (history != null) {
            builder.setRemoteInputHistory(history);
        }

        //Create Notification.
        NotificationManager notificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.notify(NOTIFICATION_ID,
                builder.build());

    }
}
