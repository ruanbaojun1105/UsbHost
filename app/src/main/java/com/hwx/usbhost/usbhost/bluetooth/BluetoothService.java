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

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.hwx.usbhost.usbhost.AppConfig;
import com.hwx.usbhost.usbhost.Application;
import com.hwx.usbhost.usbhost.Constants;
import com.hwx.usbhost.usbhost.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with bluetooth_other devices. It has a thread that listens for incoming
 * connections, a thread for connecting with a device, and a thread for
 * performing data transmissions when connected.
 */
public class BluetoothService {
	// Debugging
	private static final String TAG = "BluetoothChatService";
	private static final boolean D = true;

	// Name for the SDP record when creating server socket
	private static final String NAME = "BluetoothChat";

	// Unique UUID for this application
	// private static final UUID MY_UUID =
	// UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
	private static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");//???????
	// private static final UUID MY_UUID =
	// UUID.fromString("896db752-44cc-4dda-954b-57dca98db571");
	// Member fields
	private final BluetoothAdapter mAdapter;
	private Handler mHandler;
	private AcceptThread mAcceptThread;
	private ConnectThread mConnectThread;
	private ConnectedThread mConnectedThread;
	private int mState;

	// Constants that indicate the current connection state
	public static final int STATE_NONE = 0; // we're doing nothing
	public static final int STATE_LISTEN = 1; // now listening for incoming
												// connections
	public static final int STATE_CONNECTING = 2; // now initiating an outgoing
													// connection
	public static final int STATE_CONNECTED = 3; // now connected to a remote
													// device

	private Object lock = new Object();

	private static BluetoothService instance = null;
	public static BluetoothService getInstance(Handler handler) {
		if (instance==null){
			synchronized (BluetoothService.class) {
				if (instance==null){
					instance=new BluetoothService(handler);
				}
			}
			}
		return instance;
	}
	public static BluetoothService getInstance() {
		return instance;
	}

		/**
         * Constructor. Prepares a new BluetoothChat session.
         *
         * @param handler
         *            A Handler to send messages back to the UI Activity
         */
	public BluetoothService(Handler handler) {
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mState = STATE_NONE;
		mHandler = handler;
	}

	/**
	 * Set the current state of the chat connection
	 * 
	 * @param state
	 *            An integer defining the current connection state
	 */
	private synchronized void setState(int state) {
		if (D)
			Log.d(TAG, "setState() " + mState + " -> " + state);
		mState = state;

		// Give the new state to the Handler so the UI Activity can update
		if (mHandler!=null)
			mHandler.obtainMessage(ScaleActivity.MESSAGE_STATE_CHANGE, state, -1)
				.sendToTarget();
		Bundle bundle = new Bundle();
		bundle.putInt("state", state);
		Application.sendLocalBroadCast(Constants.SERIAL_PORT_CONNECT_STATE,bundle);
	}

	/**
	 * Return the current connection state.
	 */
	public synchronized int getState() {
		return mState;
	}

	/**
	 * Start the chat service. Specifically start AcceptThread to begin a
	 * session in listening (server) mode. Called by the Activity onResume()
	 */
	public synchronized void start() {
		if (D)
			Log.d(TAG, "start");

		// Cancel any thread attempting to make a connection
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		// Start the thread to listen on a BluetoothServerSocket
		if (mAcceptThread == null) {
			mAcceptThread = new AcceptThread();
			mAcceptThread.start();
		}
		setState(STATE_LISTEN);
	}

	/**
	 * Start the ConnectThread to initiate a connection to a remote device.
	 * 
	 * @param device
	 *            The BluetoothDevice to connect
	 */
	public synchronized void connect(BluetoothDevice device) {
		if (D)
			Log.d(TAG, "connect to: " + device);

		// Cancel any thread attempting to make a connection
		if (mState == STATE_CONNECTING) {
			if (mConnectThread != null) {
				mConnectThread.cancel();
				mConnectThread = null;
			}
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		// Start the thread to connect with the given device
		mConnectThread = new ConnectThread(device);
		mConnectThread.start();
		setState(STATE_CONNECTING);
	}

	/**
	 * Start the ConnectedThread to begin managing a Bluetooth connection
	 * 
	 * @param socket
	 *            The BluetoothSocket on which the connection was made
	 * @param device
	 *            The BluetoothDevice that has been connected
	 */
	public synchronized void connected(BluetoothSocket socket,
			BluetoothDevice device) {
		if (D)
			Log.d(TAG, "connected");

		// Cancel the thread that completed the connection
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		// Cancel the accept thread because we only want to connect to one
		// device
		if (mAcceptThread != null) {
			mAcceptThread.cancel();
			mAcceptThread = null;
		}

		// Start the thread to manage the connection and perform transmissions
		mConnectedThread = new ConnectedThread(socket);


		AppConfig.getInstance().putString("bt_name", device.getName());//????????
		AppConfig.getInstance().putString("bt_address",device.getAddress());
		// Send the name of the connected device back to the UI Activity
		Bundle bundle = new Bundle();
		bundle.putString("name", device.getName());
		bundle.putString("address",device.getAddress());
		if (mHandler!=null) {
			Message msg = mHandler.obtainMessage(ScaleActivity.MESSAGE_DEVICE_NAME);
			msg.setData(bundle);
			mHandler.sendMessage(msg);
		}
		Application.sendLocalBroadCast(Constants.SERIAL_PORT_CONNECT_NAME,bundle);
		setState(STATE_CONNECTED);
	}

	/**
	 * Stop all threads
	 */
	public synchronized void stop() {
		if (D)
			Log.d(TAG, "stop");
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
		if (mAcceptThread != null) {
			mAcceptThread.cancel();
			mAcceptThread = null;
		}
		setState(STATE_NONE);
	}

	/**
	 * Write to the ConnectedThread in an unsynchronized manner
	 * 
	 * @param out
	 *            The bytes to write
	 * @see ConnectedThread#write(byte[])
	 */
	public boolean write(byte[] out) {
		// Create temporary object
		ConnectedThread r;
		// Synchronize a copy of the ConnectedThread
		synchronized (this) {
			if (mState != STATE_CONNECTED)
				return false;
			r = mConnectedThread;
		}
		// Perform the write unsynchronized
		r.write(out);
		return true;
	}
	public static final int safeCode = 0x10;//校验码
	final static byte[] endCode = new byte[]{0x0c, 0x0d};//结束符
	static final int starCode = 0x3a;//头
	static final int addrCode = 0x01;//地址
	public void sendData(byte[] data) {
		write(data);
	}
	/**
	 *发送数据
	 */
	public void sendData(/*SerialPort mSerialPort , OutputStream mOutputStream, */byte function, String data, boolean isAutoSafeCode) {
		// TODO Auto-generated method stub
		sendData(function,data.getBytes(),isAutoSafeCode);
	}
	/**
	 *发送数据
	 */
	public static void sendData(byte function,byte[] content,boolean isAutoSafeCode) {
		if (getInstance()==null)
			return;
		isAutoSafeCode=true;//所有都自动算出来
		byte[] head=new byte[]{starCode,addrCode,function,(byte) (content.length/256),(byte) (content.length%256)};
		byte safe=safeCode;
		if (isAutoSafeCode)
			safe=checkSafeCod(content);
		byte[] end=new byte[]{safe,endCode[0],endCode[1]};
		List<byte[]> list=new ArrayList<byte[]>();
		list.add(head);
		list.add(content);
		list.add(end);

		byte[] a=sysCopy(list);
		getInstance().write(a);
		Log.e("dd:","number code :"+function+" 's data send ok！");
	}
	public static byte checkSafeCod(byte[] data){
		byte safeCode=0;
		for(byte at:data){
			safeCode^=at;
		}
		return safeCode;
	}
	/**
	 * Indicate that the connection attempt failed and notify the UI Activity.
	 */
	private void connectionFailed() {
		setState(STATE_LISTEN);
		Bundle bundle = new Bundle();
		bundle.putString(ScaleActivity.TOAST, "Unable to connect device");//toast
		// Send a failure message back to the Activity
		if (mHandler!=null) {
			Message msg = mHandler.obtainMessage(ScaleActivity.MESSAGE_TOAST);
			msg.setData(bundle);
			mHandler.sendMessage(msg);
		}
		Application.sendLocalBroadCast(Constants.SERIAL_PORT_CONNECT_FAIL);
	}

	/**
	 * Indicate that the connection was lost and notify the UI Activity.
	 */
	private void connectionLost() {
		setState(STATE_LISTEN);
		if (mHandler!=null) {
			Message msg = mHandler.obtainMessage(ScaleActivity.MESSAGE_TOAST);
			Bundle bundle = new Bundle();
			bundle.putString(ScaleActivity.TOAST, "Device connection was lost");
			msg.setData(bundle);
			mHandler.sendMessage(msg);
		}
		Application.sendLocalBroadCast(Constants.SERIAL_PORT_CONNECT_FAIL);
	}

	/**
	 * This thread runs while listening for incoming connections. It behaves
	 * like a server-side client. It runs until a connection is accepted (or
	 * until cancelled).
	 */
	private class AcceptThread extends Thread {
		// The local server socket
		private final BluetoothServerSocket mmServerSocket;

		public AcceptThread() {
			BluetoothServerSocket tmp = null;
			// Create a new listening server socket
			try {
				tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
			} catch (IOException e) {
				Log.e(TAG, "listen() failed", e);
			}
			mmServerSocket = tmp;
		}

		public void run() {
			if (D)
				Log.d(TAG, "BEGIN mAcceptThread" + this);
			setName("AcceptThread");
			BluetoothSocket socket = null;
			if (null == mmServerSocket) {
				return;
			}
			// Listen to the server socket if we're not connected
			while (mState != STATE_CONNECTED) {
				try {
					// This is a blocking call and will only return on a
					// successful connection or an exception
					socket = mmServerSocket.accept();
				} catch (IOException e) {
					Log.e(TAG, "accept() failed", e);
					break;
				}

				// If a connection was accepted
				if (socket != null) {
					synchronized (BluetoothService.this) {
						switch (mState) {
						case STATE_LISTEN:
						case STATE_CONNECTING:
							// Situation normal. Start the connected thread.
							connected(socket, socket.getRemoteDevice());
							break;
						case STATE_NONE:
						case STATE_CONNECTED:
							// Either not ready or already connected. Terminate
							// new socket.
							try {
								socket.close();
							} catch (IOException e) {
								Log.e(TAG, "Could not close unwanted socket", e);
							}
							break;
						}
					}
				}
			}
			if (D)
				Log.i(TAG, "END mAcceptThread");
		}

		public void cancel() {
			if (D)
				Log.d(TAG, "cancel " + this);
			try {
				if (null != mmServerSocket) {
					mmServerSocket.close();
				}
			} catch (IOException e) {
				Log.e(TAG, "close() of server failed", e);
			}
		}
	}

	/**
	 * This thread runs while attempting to make an outgoing connection with a
	 * device. It runs straight through; the connection either succeeds or
	 * fails.
	 */
	private class ConnectThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		public ConnectThread(BluetoothDevice device) {
			mmDevice = device;
			BluetoothSocket tmp = null;
			// Get a BluetoothSocket for a connection with the
			// given BluetoothDevice
			try {
				tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
			} catch (IOException e) {
				Log.e(TAG, "create() failed", e);
			}
			mmSocket = tmp;
		}

		public void run() {
			Log.i(TAG, "BEGIN mConnectThread");
			setName("ConnectThread");

			// Always cancel discovery because it will slow down a connection
			mAdapter.cancelDiscovery();
			if (mmSocket == null) {
				return;
			}
			// Make a connection to the BluetoothSocket
			try {
				// This is a blocking call and will only return on a
				// successful connection or an exception
				mmSocket.connect();
			} catch (IOException e) {
				connectionFailed();
				// Close the socket
				try {
					mmSocket.close();
				} catch (IOException e2) {
					Log.e(TAG,
							"unable to close() socket during connection failure",
							e2);
				}
				// Start the service over to restart listening mode
				BluetoothService.this.start();
				return;
			}

			// Reset the ConnectThread because we're done
			synchronized (BluetoothService.this) {
				mConnectThread = null;
			}

			// Start the connected thread
			connected(mmSocket, mmDevice);
		}

		public void cancel() {
			try {
				if (null != mmSocket) {
					mmSocket.close();
				}
			} catch (IOException e) {
				Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}

	/**
	 * This thread runs during a connection with a remote device. It handles all
	 * incoming and outgoing transmissions.
	 */
	private class ConnectedThread {
		private final BluetoothSocket mmSocket;
		private final InputStream mmInStream;
		private final OutputStream mmOutStream;
		private ReadThread mReadThread;

		int fact_size=0;
		byte[] data_buffer=new byte[0];
		byte[] buffer = new byte[64];

		public ConnectedThread(BluetoothSocket socket) {
			Log.d(TAG, "create ConnectedThread");
			mmSocket = socket;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;

			// Get the BluetoothSocket input and output streams
			try {
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();

				/* Create a receiving thread */
				mReadThread = new ReadThread();
				mReadThread.start();
			} catch (IOException e) {
				Log.e(TAG, "temp sockets not created", e);
			}

			mmInStream = tmpIn;
			mmOutStream = tmpOut;
		}
		private class ReadThread extends Thread {

			@Override
			public void run() {
				super.run();

				//Arrays.fill(buffer, 0, 4096, (byte) 0);
				while (!isInterrupted()) {
					int size = 0;
					if (mmInStream == null)
						return;
					try {
						size = mmInStream.read(buffer);
					} catch (IOException e) {
						e.printStackTrace();
					}
					fact_size+=size;
					if (size==0&&fact_size>0){//clear
						initNumber();
					}
					try {
						if (size>0) {
							byte[] b=Arrays.copyOfRange(buffer, 0, size);
							data_buffer= byteMerger(data_buffer,b);
							//sendData(b);
							if (checkDataHead(data_buffer)){
								initNumber();
							}else if (getNumberData(fact_size,data_buffer)){//clear
								initNumber();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		void initNumber(){
			data_buffer=new byte[0];
			fact_size=0;
			System.gc();
		}
		/**
		 * 十六进制接收解析
		 * @param size
		 * @param buffer
		 * 3a 01 05 00 03 01 02 03 10 0c 0d
		 */
		public static final int safeCode = 0x10;//校验码
		final byte[] endCode = new byte[]{0x0c, 0x0d};//结束符
		static final int starCode = 0x3a;//头
		static final int addrCode = 0x01;//地址
		private boolean getNumberData(int size,byte[] buffer) {
			byte numberNo = 0;//功能编码
			int count_data = 0;//数据长度
			if (size > 8 ) {//一段数据至少9位
				if (addrCode != buffer[1])
					return true;//抛弃这段
				count_data = (int) buffer[3] * 256 + buffer[4];
				int a = 5 + count_data;
				if (a+2>=size)
					return false;
				numberNo = buffer[2];

//				byte finalNumberNo = numberNo;
//				new Handler(Application.getContext().getMainLooper()).post(() ->
//						Toast.makeText(Application.getContext(),"resolve :"+ finalNumberNo,Toast.LENGTH_SHORT).show());
				LogUtils.e("resolve :"+numberNo);

				if (buffer[a + 1] == endCode[0] && buffer[a + 2] == endCode[1]) {
					byte[] content= Arrays.copyOfRange(buffer, 5, a);
					byte sa=checkSafeCod(content);
					if (numberNo==0x05){
						sa=0x01;
					}
					sendData((byte) 0xff,new byte[]{0x00,0x00},true);//收到消息返回一条

					Bundle bundle1=new Bundle();
					bundle1.putByteArray("data",content);
					bundle1.putByte("numberNo",numberNo);
					bundle1.putByte("safeCode",buffer[a]);
					Application.sendLocalBroadCast(Constants.SERIAL_PORT_COMMAND,bundle1);

					if (mHandler!=null)
						mHandler.obtainMessage(ScaleActivity.MESSAGE_READ,
								buffer[a], numberNo, content).sendToTarget();
                /*if (sa==buffer[a]) {//测试暂时不开验证
                    onDataReceived(content, numberNo, buffer[a]);
                    return true;
                }*/
				}
				return true;
			}
			return  false;
		}
		boolean checkDataHead(byte[] buffer){
			if (buffer[0] == starCode)
				return false;
        /*else {
            //此处可以继续判断，但一般不会有这样情况
            for (int i = 0; i <buffer.length ; i++) {
                if (buffer[i] == starCode){
                    data_buffer=Arrays.copyOfRange(buffer, i, buffer.length);
                    return false;
                }
            }
        }*/
			return true;
		}
		public byte checkSafeCod(byte[] data){
			byte safeCode=0;
			for(byte at:data){
				safeCode^=at;
			}
			return safeCode;
		}
		/**
		 * Write to the connected OutStream.
		 *
		 * @param buffer
		 *            The bytes to write
		 */
		public void write(byte[] buffer) {
			try {
				if (mmOutStream!=null) {
					mmOutStream.write(buffer);
					Log.i("bt", new String(buffer));
					// Share the sent message back to the UI Activity
					if (mHandler != null) {
						mHandler.obtainMessage(ScaleActivity.MESSAGE_WRITE, -1, -1,
								buffer).sendToTarget();
					}
					Bundle bundle = new Bundle();
					bundle.putByteArray("data", buffer);
					Application.sendLocalBroadCast(Constants.SERIAL_PORT_SEND, bundle);
				}
			} catch (IOException e) {
				Log.e(TAG, "Exception during write", e);
			}
		}

		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}
	//java 合并两个byte数组
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){
		byte[] byte_3 = new byte[byte_1.length+byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}
	public static byte[] sysCopy(List<byte[]> srcArrays) {
		int len = 0;
		for (byte[] srcArray:srcArrays) {
			len+= srcArray.length;
		}
		byte[] destArray = new byte[len];
		int destLen = 0;
		for (byte[] srcArray:srcArrays) {
			System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
			destLen += srcArray.length;
		}
		return destArray;
	}
}
