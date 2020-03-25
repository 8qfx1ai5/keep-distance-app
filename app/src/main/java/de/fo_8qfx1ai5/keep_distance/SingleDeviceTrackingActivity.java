package de.fo_8qfx1ai5.keep_distance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SingleDeviceTrackingActivity extends AppCompatActivity {

    TextView textViewDeviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_device_tracking);

        textViewDeviceInfo = (TextView)findViewById(R.id.textViewDeviceInfo);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("device")!= null)
        {
            textViewDeviceInfo.setText(bundle.getString("device"));
        }
    }
}
