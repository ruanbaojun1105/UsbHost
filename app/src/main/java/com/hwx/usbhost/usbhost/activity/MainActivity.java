package com.hwx.usbhost.usbhost.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.util.LogUtils;

import java.util.HashMap;

@Deprecated
public class MainActivity extends Activity {
	private static final String TAG = "USB_HOST";

	private UsbManager myUsbManager;
	private UsbDevice myUsbDevice;
	private UsbInterface myInterface;
	private UsbDeviceConnection myDeviceConnection;

	private final int VendorID = 7758;
	private final int ProductID = 259;

	private TextView info;

	private UsbEndpoint epOut;
	private UsbEndpoint epIn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		registerReceiver(mUsbPermissionActionReceiver, filter);

		info = (TextView) findViewById(R.id.info);
		info.setOnClickListener((v)->{
			//do somethings
		});

		/*info.postDelayed(()->{
			info.setText("lambda is better!");
		},2000);*/
		// ???UsbManager
		myUsbManager = (UsbManager) getSystemService(USB_SERVICE);

		enumerateDevice();

		findInterface();

		openDevice();

		assignEndpoint();


		try {
			sendMessageToPoint("?????????".getBytes());
			receiveMessageFromPoint();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * ?????????????????????��?????????????????????????????????
		 * 05-27 14:54:24.140: D/USB_HOST(10870): DeviceInfo: 8457 , 30264
		 * 05-27 14:54:24.140: D/USB_HOST(10870): ????��???
		 * 05-27 14:54:24.140: D/USB_HOST(10870): interfaceCounts : 1
		 * 05-27 14:54:24.140: D/USB_HOST(10870): ???????��???
		 * 05-27 14:54:24.160: D/USB_HOST(10870): ???��???
		 * 05-27 14:54:24.170: D/USB_HOST(10870): ?????????????��->????��->????��????
		 * 										->?????��->??????????????????????????????????????????????
		 */
	}

	/**
	 * ??????IN | OUT???????????????????????1?OUT???0?IN????????????????��?
	 * #define USB_ENDPOINT_XFER_CONTROL 0 --???????
	 #define USB_ENDPOINT_XFER_ISOC 1 --???????
	 #define USB_ENDPOINT_XFER_BULK 2 --?��??
	 #define USB_ENDPOINT_XFER_INT 3 --?��????
	 */
	private void assignEndpoint() {
		if (myInterface == null)
			return;
		for (int i = 0; i < myInterface.getEndpointCount(); i++) {
			UsbEndpoint ep = myInterface.getEndpoint(i);
			LogUtils.e("type:"+ep.getType());
			epIn=ep;
			// look for bulk endpoint
			/*if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
				if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
					epOut = ep;
					System.out.println("Find the BulkEndpointOut," + "index:"
							+ i + "," + "???????"
							+ epOut.getEndpointNumber());
				} else {
					epIn = ep;
					System.out.println("Find the BulkEndpointIn:" + "index:" + i+ "," + "???????"+ epIn.getEndpointNumber());
				}
			}*/
			// look for contorl endpoint
			/*if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_CONTROL) {
				epControl = ep;
				System.out.println("find the ControlEndPoint:" + "index:" + i+ "," + epControl.getEndpointNumber());
			}
			// look for interrupte endpoint
			if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_INT) {
				if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
					epOut = ep;
					System.out.println("find the InterruptEndpointOut:"+ "index:" + i + ","+ epOut.getEndpointNumber());
				}
				if (ep.getDirection() == UsbConstants.USB_DIR_IN) {
					epIn = ep;
					System.out.println("find the InterruptEndpointIn:"+ "index:" + i + ","+ epIn.getEndpointNumber());
				}
			}*/
		}
		Log.d(TAG, getString(R.string.text));
	}

	// ????????
	private void sendMessageToPoint(byte[] buffer) throws Exception {
		if (epOut==null)
			return;
		// bulkOut????
		if (myDeviceConnection
				.bulkTransfer(epOut, buffer, buffer.length, 2000) < 0)
			Log.d(TAG, "bulkOut????????  ????");
		else {
			Log.d(TAG, "Send Message Succese??");
		}
	}

	// ???��????????bulkIn
	private byte[] receiveMessageFromPoint() throws Exception {
		if (epIn==null)
			return null;
		byte[] buffer = new byte[epIn.getMaxPacketSize()];
		if (myDeviceConnection.bulkTransfer(epIn, buffer, buffer.length,
				2000) < 0)
			System.out.println("bulkIn????????  ????");
		else {
			System.out.println("Receive Message Succese??"
			);
		}
		return buffer;
	}


	private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

	/**
	 * ???��
	 */
	private void openDevice() {
		if (myInterface != null) {
			UsbDeviceConnection conn = null;
			PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
			// ??open??��???????????????????????????????????????????????????????????????
			if (myUsbManager.hasPermission(myUsbDevice)) {
				conn = myUsbManager.openDevice(myUsbDevice);
			}else {
				myUsbManager.requestPermission(myUsbDevice, mPermissionIntent);
			}

			if (conn == null) {
				return;
			}

			if (conn.claimInterface(myInterface, true)) {
				myDeviceConnection = conn; // ???????android?��???????HID?��
				Log.d(TAG, "???��???");
			} else {
				conn.close();
			}
		}
	}

	/**
	 * ???��???
	 */
	private void findInterface() {
		if (myUsbDevice != null) {
			Log.d(TAG, "interfaceCounts : " + myUsbDevice.getInterfaceCount());
			for (int i = 0; i < myUsbDevice.getInterfaceCount(); i++) {
				UsbInterface intf = myUsbDevice.getInterface(i);
				// ??????????��???��?��???????��????????????????��????????
				if (intf.getInterfaceClass() == 14
						&& intf.getInterfaceSubclass() == 1
						&& intf.getInterfaceProtocol() == 0) {
					myInterface = intf;
					Log.d(TAG, "???????��???");
					break;
				}
			}
		}
	}

	/**
	 * ????��
	 */
	private void enumerateDevice() {
		if (myUsbManager == null)
			return;
		HashMap<String, UsbDevice> deviceList = myUsbManager.getDeviceList();
		if (!deviceList.isEmpty()) { // deviceList?????
			StringBuffer sb = new StringBuffer();
			for (UsbDevice device : deviceList.values()) {
				sb.append(device.toString());
				sb.append("\n");
				info.setText(sb);
				// ????��???
				Log.d(TAG, "DeviceInfo: " + device.getVendorId() + " , "
						+ device.getProductId());

				// ?????��
				if (device.getVendorId() == VendorID
						&& device.getProductId() == ProductID) {
					myUsbDevice = device;
					Log.d(TAG, "????��???");
				}
			}
		}
	}


	private final BroadcastReceiver mUsbPermissionActionReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_USB_PERMISSION.equals(action)) {
				synchronized (this) {
					UsbDevice usbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
					if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						//user choose YES for your previously popup window asking for grant perssion for this usb device
						if(null != usbDevice){
							openDevice();
						}
					}
					else {
						//user choose NO for your previously popup window asking for grant perssion for this usb device
						Toast.makeText(context, String.valueOf("Permission denied for device" + usbDevice), Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	};
}
