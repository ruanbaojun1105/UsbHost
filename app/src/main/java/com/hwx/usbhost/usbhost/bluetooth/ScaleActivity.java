/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hwx.usbhost.usbhost.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hwx.usbhost.usbhost.AppConfig;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.activity.CocktailListActivity;
import com.hwx.usbhost.usbhost.activity.LoginActivity;
import com.hwx.usbhost.usbhost.activity.SettingMenuActivity;
import com.hwx.usbhost.usbhost.activity.broadcast.CommandReceiver;


public class ScaleActivity extends AppCompatActivity implements View.OnClickListener {
    // Debugging
    private static final String TAG = "OtherActivity";
    private static final boolean D = true;
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "bluetooth_device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    private String mConnectedDeviceName;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothService bluetoothService = null;

    private Object lock = new Object();
    private Button connect_device;
    private boolean isConnect = false;
    private Button opendevice;
    private Button setting;
    private TextView connect_state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D)
            Log.e(TAG, "+++ ON CREATE +++");
        setContentView(R.layout.bluetooth_other);
        initView();

        mConnectedDeviceName=AppConfig.getInstance().getString("bt_name", "");
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available",
                    Toast.LENGTH_LONG).show();
            //finish();
            return;
        }
        new CommandReceiver() {
            @Override
            public void onDataReceived(byte[] buffer, byte function, byte safeCod) {

            }

            @Override
            public void onFail() {
                connect_device.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLost() {
                connect_device.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDeviceInfo(String name, String address) {

            }

            @Override
            public void onStadeTag(int stade) {

            }
        }.regiest();
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            System.exit(0);
            return true;
        }
        return false;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (D)
            Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (bluetoothService == null)
                setupConnect();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if (D)
            Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity
        // returns.
        if (bluetoothService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't
            // started already
            if (bluetoothService.getState() == BluetoothService.STATE_NONE) {
                // Start the Bluetooth chat services
                bluetoothService.start();
            }
        }
    }

    private synchronized void setupConnect() {
        Log.d(TAG, "setupChat()");

        // if(!mConversationArrayAdapter.isEmpty())
        // Log.i(TAG1, mConversationArrayAdapter.getItem(0) + "hello");

        // Initialize the BluetoothChatService to perform bluetooth connections
        bluetoothService = BluetoothService.getInstance(mHandler);

        String addr = AppConfig.getInstance().getString("bt_address", "");
        if (!TextUtils.isEmpty(addr)) {
            connect_device.setVisibility(View.GONE);
            connDevice(addr);
        }
        // Initialize the buffer for outgoing messages
        // mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if (D)
            Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (D)
            Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (bluetoothService != null)
            bluetoothService.stop();
        if (D)
            Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if (D)
            Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(
                    BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (D)
                        Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            connect_state.setText(R.string.title_connected_to);
                            connect_state.append(mConnectedDeviceName);
                            connect_state.append("\t\tSTATE_CONNECTED");
                            connect_device.setVisibility(View.GONE);
                            isConnect = true;
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            connect_state.setText(R.string.title_connecting);
                            isConnect = false;
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            connect_state.setText(R.string.title_not_connected);
                            isConnect = false;
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    break;
                case MESSAGE_READ:
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    if (!TextUtils.isEmpty( msg.getData().getString(DEVICE_NAME)))
                        mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "Connected to " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    connect_state.setText(R.string.title_connected_to);
                    connect_state.append(mConnectedDeviceName);
                    connect_state.append("\t\tSTATE_CONNECTED");
                    connect_device.setVisibility(View.GONE);
                    isConnect = true;
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (D)
            Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(
                            DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    connDevice(address);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupConnect();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    //finish();
                }
        }
    }

    private void connDevice(String address) {
        // Get the BLuetoothDevice object
        if (mBluetoothAdapter == null)
            return;
        if (bluetoothService == null)
            return;
        BluetoothDevice device = mBluetoothAdapter
                .getRemoteDevice(address);
        // Attempt to connect to the device
        bluetoothService.connect(device);
    }
    private void initView() {
        connect_device = (Button) findViewById(R.id.connect_device);
        connect_state = (TextView) findViewById(R.id.connect_state);
        connect_device.setOnClickListener(this);
        opendevice = (Button) findViewById(R.id.opendevice);
        opendevice.setOnClickListener(this);
        setting = (Button) findViewById(R.id.setting);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect_device:
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                break;

            case R.id.opendevice://???????
                Intent intent = new Intent(this, CocktailListActivity.class);
                intent.putExtra(SettingMenuActivity.ISEDIT, false);//??????
                startActivity(intent);
                break;
            case R.id.setting:
                Intent intent1 = new Intent(this, LoginActivity.class);
                intent1.putExtra("pass_arg",SettingMenuActivity.PASSWORD);
                startActivity(intent1);
                break;
        }
    }
}