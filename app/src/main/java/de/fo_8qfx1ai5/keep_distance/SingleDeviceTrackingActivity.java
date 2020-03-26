package de.fo_8qfx1ai5.keep_distance;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SingleDeviceTrackingActivity extends AppCompatActivity {

    ListView listViewHistory;
    TextView textViewStatusText;
    Button buttonStartSingleDeviceSearch;
    ArrayList<String> bluetoothDevices = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    BluetoothAdapter bluetoothAdapter;
    String deviceAddress = "";

    long startTime;
    long endTime;
    long deltaTime;

    /**
     * handles the bluetooth events for our device searches
     */
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

                // unblock the user interface after search
                textViewStatusText.setText(R.string.text_view_final_search_message);
                buttonStartSingleDeviceSearch.setEnabled(true);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                // add device to the list, if it is the selected one
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String address = device.getAddress();
                if (address.equals(deviceAddress)) {
                    endTime = System.currentTimeMillis();
                    deltaTime = endTime - startTime;
                    String name = device.getName();
                    String rssi_min = Integer.toString(intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));

                    if (name == null || name.equals("")) {
                        bluetoothDevices.add(0, rssi_min + " dBm   " + deltaTime + "ms\n");
                    } else {
                        bluetoothDevices.add(0, rssi_min + " dBm   " + deltaTime + "ms\n" + name);
                    }
                    arrayAdapter.notifyDataSetChanged();
                    bluetoothAdapter.cancelDiscovery();
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_device_tracking);

        // initialize global variables
        buttonStartSingleDeviceSearch = findViewById(R.id.buttonSingleDeviceSearchStart);
        textViewStatusText = findViewById(R.id.textViewSingleDeviceSearchStatus);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bluetoothDevices);
        listViewHistory = findViewById(R.id.listViewSingleDeviceHistory);
        listViewHistory.setAdapter(arrayAdapter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // add start info about the device to the start button
        Bundle bundle = getIntent().getExtras();
        String infoFromList = null;
        if (bundle != null) {
            infoFromList = bundle.getString("device");
        }
        if (infoFromList != null) {
            deviceAddress = infoFromList.substring(infoFromList.indexOf("\n") + 1);
            buttonStartSingleDeviceSearch.setText(String.format(getResources().getString(R.string.button_label_single_device_tracking_with_address), deviceAddress));
        }
        if (deviceAddress.equals("")) {
            finish();
        }

        // set filter for the bluetooth search events
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * function is triggered by the search button
     *
     * @param view the clicked button as view
     */
    public void searchedClicked(View view) {
        // bock the user interface during the search
        textViewStatusText.setText(R.string.text_view_running_search_message);
        buttonStartSingleDeviceSearch.setEnabled(false);

        // start the next search
        startTime = System.currentTimeMillis();
        bluetoothAdapter.startDiscovery();
    }
}
