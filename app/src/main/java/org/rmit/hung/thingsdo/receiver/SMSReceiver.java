/*
 * SMSReceiver.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       19/07/2014
 * Date last modified: 19/07/2014
 */

package org.rmit.hung.thingsdo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import org.rmit.hung.thingsdo.service.SMSService;
import org.rmit.hung.thingsdo.view.MainScreen;

/**
 * @author Ly Quoc Hung <s3426511@rmit.edu.vn>
 * @version %I%
 * Reference:
 * - http://androidexample.com/Incomming_SMS_Broadcast_Receiver_-_Android_Example/index.php?view=article_discription&aid=62&aaid=87
 */
public class SMSReceiver extends BroadcastReceiver {
	/**
	 * This method is called when the BroadcastReceiver is receiving an Intent
	 * broadcast.  During this time you can use the other methods on
	 * BroadcastReceiver to view/modify the current result values.  This method
	 * is always called within the main thread of its process, unless you
	 * explicitly asked for it to be scheduled on a different thread using
	 * {@link android.content.Context#registerReceiver(android.content.BroadcastReceiver,
	 * android.content.IntentFilter, String, android.os.Handler)}. When it runs on the main
	 * thread you should
	 * never perform long-running operations in it (there is a timeout of
	 * 10 seconds that the system allows before considering the receiver to
	 * be blocked and a candidate to be killed). You cannot launch a popup dialog
	 * in your implementation of onReceive().
	 * <p/>
	 * <p><b>If this BroadcastReceiver was launched through a &lt;receiver&gt; tag,
	 * then the object is no longer alive after returning from this
	 * function.</b>  This means you should not perform any operations that
	 * return a result to you asynchronously -- in particular, for interacting
	 * with services, you should use
	 * {@link android.content.Context#startService(android.content.Intent)} instead of
	 * {@link android.content.Context#bindService(android.content.Intent, android.content.ServiceConnection, int)}.  If
	 * you wish
	 * to interact with a service that is already running, you can use
	 * {@link #peekService}.
	 * <p/>
	 * <p>The Intent filters used in {@link android.content.Context#registerReceiver}
	 * and in application manifests are <em>not</em> guaranteed to be exclusive. They
	 * are hints to the operating system about how to find suitable recipients. It is
	 * possible for senders to force delivery to specific recipients, bypassing filter
	 * resolution.  For this reason, {@link #onReceive(android.content.Context, android.content.Intent) onReceive()}
	 * implementations should respond only to known actions, ignoring any unexpected
	 * Intents that they may receive.
	 *
	 * @param context
	 * 		The Context in which the receiver is running.
	 * @param intent
	 * 		The Intent being received.
	 * 		References:
	 * 		- http://blogmobile.itude.com/2012/12/03/sending-and-receiving-data-sms-messages-with-android/
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("SMS Read")) {
			String category = intent.getStringExtra("Category");

			((MainScreen) context).refreshList(category);
		} else {
			Bundle bundle = intent.getExtras();

			String receivedMessage = "";
			String sender = "";

			SmsMessage recMsg;

			if (bundle != null) {
				// retrieve the SMS message
				Object[] messageObjects = (Object[]) bundle.get("pdus");
				for (Object messageObject : messageObjects) {
					recMsg = SmsMessage.createFromPdu((byte[]) messageObject);

					try {
						receivedMessage += recMsg.getMessageBody();
					} catch (Exception ignored) {
					}

					sender = recMsg.getOriginatingAddress();
				}
			}

			// put SMS to the intent
			Intent smsService = new Intent(context.getApplicationContext(), SMSService.class);
			smsService.putExtra("Message", receivedMessage);
			smsService.putExtra("Sender", sender);

			context.getApplicationContext().startService(smsService);
		}
	}
}
