package com.rivendell.pushtestandroid

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import com.baidu.android.pushservice.PushMessageReceiver
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/*
 * Push message receiver
 * onBind is the callback of startWork,
 * onMessage callback will receive tags: onSetTags, onDelTags, onListTags,
 * onNotificationClicked callback will be invoked when user click notification,
 * onUnbind is the callback of stopWork
 * errorCode：
 * 0 - Success
 * 10001 - Network Problem
 * 10101 - Integrate Check Error
 * 30600 - Internal Server Error
 * 30601 - Method Not Allowed
 * 30602 - Request Params Not Valid
 * 30603 - Authentication Failed
 * 30604 - Quota Use Up Payment Required
 * 30605 - Data Required Not Found
 * 30606 - Request Time Expires Timeout
 * 30607 - Channel Token Timeout
 * 30608 - Bind Relation Not Found
 * 30609 - Bind Number Too Many
 *
 */
class MyPushMessageReceiver : PushMessageReceiver() {

    val TAG = "MyPushMessageReceiver"

    override fun onBind(
        context: Context?, errorCode: Int, appid: String?,
        userId: String?, channelId: String?, requestId: String?
    ) {
        val responseString = ("onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId)
        Log.d(TAG, responseString)

        if (errorCode == 0) {
            Log.d(TAG, "bind successfully")
        }
        updateContent(context, responseString)
    }

    override fun onMessage(
        context: Context?, message: String?,
        customContentString: String?
    ) {
        val messageString = ("onMessage=$message customContentString=$customContentString")
        Log.d(TAG, messageString)
        if (!TextUtils.isEmpty(customContentString)) {
            var customJson: JSONObject? = null
            try {
                customJson = JSONObject(customContentString)
                var myvalue: String? = null
                if (!customJson!!.isNull("mykey")) {
                    myvalue = customJson!!.getString("mykey")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        updateContent(context, messageString)
    }

    override fun onNotificationArrived(
        context: Context?, title: String?,
        description: String?, customContentString: String?
    ) {
        val notifyString = ("onNotificationArrived  title=$title} " +
                "description=$description} customContent=$customContentString")
        Log.d(TAG, notifyString)

        if (!TextUtils.isEmpty(customContentString)) {
            var customJson: JSONObject? = null
            try {
                customJson = JSONObject(customContentString)
                var myvalue: String? = null
                if (!customJson.isNull("mykey")) {
                    myvalue = customJson.getString("mykey")
                }
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
        updateContent(context, notifyString)
    }

    override fun onNotificationClicked(
        context: Context?, title: String?,
        description: String?, customContentString: String?
    ) {
        val notifyString = ("onNotificationClicked title=$title " +
                "description=$description} customContent=$customContentString")
        Log.d(TAG, notifyString)
        if (!TextUtils.isEmpty(customContentString)) {
            var customJson: JSONObject? = null
            try {
                customJson = JSONObject(customContentString)
                var myvalue: String? = null
                if (!customJson!!.isNull("mykey")) {
                    myvalue = customJson.getString("mykey")
                }
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
        updateContent(context, notifyString)
    }

    override fun onSetTags(
        context: Context?, errorCode: Int,
        successTags: List<String?>?, failTags: List<String?>?, requestId: String?
    ) {
        val responseString = ("onSetTags errorCode=$errorCode} " +
                "successTags=$successTags failTags=$failTags requestId=$requestId")
        Log.d(TAG, responseString)
        updateContent(context, responseString)
    }

    override fun onDelTags(
        context: Context?, errorCode: Int,
        successTags: List<String?>, failTags: List<String?>, requestId: String
    ) {
        val responseString = ("onDelTags errorCode=$errorCode " +
                "successTags=$successTags failTags=$failTags requestId=$requestId")
        Log.d(TAG, responseString)

        updateContent(context, responseString)
    }

    override fun onListTags(
        context: Context?, errorCode: Int, tags: List<String?>,
        requestId: String?
    ) {
        val responseString = ("onListTags errorCode=$errorCode tags=$tags")
        Log.d(TAG, responseString)
        updateContent(context, responseString)
    }

    override fun onUnbind(context: Context?, errorCode: Int, requestId: String?) {
        val responseString = ("onUnbind errorCode=$errorCode requestId = $requestId")
        Log.d(TAG, responseString)

        if (errorCode == 0) {
            Log.d(TAG, "unbind successfully")
        }
        updateContent(context, responseString)
    }

    private fun updateContent(context: Context?, content: String) {
        Log.d(TAG, "updateContent")
        var logText: String = ""
        val sDateFormat = SimpleDateFormat("HH-mm-ss")
        logText += sDateFormat.format(Date()) + ": "
        logText += content
        Log.i(TAG, "logText:$logText")
    }
}