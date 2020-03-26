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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListBlueToothDevicesActivity extends AppCompatActivity {

    ListView deviceList;
    TextView searchStatusText;
    Button listDevicesButton;

    ArrayList<String> bluetoothDevices = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    int counterDevicesWithName = 0;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("Action", action);

            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                searchStatusText.setText("Finished.");
                listDevicesButton.setEnabled(true);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                String address = device.getAddress();
                String rssi_min = Integer.toString(intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));

                if (name == null || name.equals("")) {
                    bluetoothDevices.add(counterDevicesWithName, rssi_min + "dBm\n" + address);
                } else {
                    bluetoothDevices.add(0, rssi_min + "dBm   (" + name + ")" + "\n" + address);
                    counterDevicesWithName++;
                }
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_devices);

        deviceList = (ListView) findViewById(R.id.listViewBlueToothDevices);
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                bluetoothAdapter.cancelDiscovery();
                Intent intent = new Intent(ListBlueToothDevicesActivity.this, SingleDeviceTrackingActivity.class);
                intent.putExtra("device", parent.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });

        searchStatusText = findViewById(R.id.textViewSearchStatus);
        listDevicesButton = findViewById(R.id.listDevicesButton);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bluetoothDevices);

        deviceList.setAdapter(arrayAdapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void searchedClicked(View view) {
        searchStatusText.setText("Searching ...");
        listDevicesButton.setEnabled(false);
        bluetoothDevices.clear();
        bluetoothAdapter.startDiscovery();
    }
}
