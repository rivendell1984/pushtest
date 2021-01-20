package com.rivendell.pushtestandroid

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.baidu.android.pushservice.BasicPushNotificationBuilder
import com.baidu.android.pushservice.PushConstants
import com.baidu.android.pushservice.PushManager


class MainActivity : AppCompatActivity() {

    private val REQ_CODE_INIT_APIKEY = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkStoragePerms(REQ_CODE_INIT_APIKEY)


        // 1.Default notification
        // To support Android O（8.x）with customized channelId/channelName
        val bBuilder = BasicPushNotificationBuilder()
        bBuilder.setChannelId("testDefaultChannelId")
        bBuilder.setChannelName("testDefaultChannelName")
        PushManager.setDefaultNotificationBuilder(this, bBuilder);

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQ_CODE_INIT_APIKEY) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initWithApiKey()
                } else {
                    Toast.makeText(
                        this,
                        "please grant the permission", Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                initWithApiKey()
            }
        }
    }

    private fun checkStoragePerms(requestCode: Int) {
        val writePermission: Int = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                requestCode
            )
        } else {
            initWithApiKey()
        }
    }

    private fun initWithApiKey() {
        PushManager.enableHuaweiProxy(this, true)

        PushManager.startWork(
            applicationContext,
            PushConstants.LOGIN_TYPE_API_KEY,
            Utils.getMetaValue(this, "api_key")
        )
    }
}