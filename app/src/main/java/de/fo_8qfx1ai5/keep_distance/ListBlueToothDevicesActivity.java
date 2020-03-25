package de.fo_8qfx1ai5.keep_distance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ListBlueToothDevicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_devices);

        final Button button = (Button) findViewById(R.id.ListDevicesButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}
