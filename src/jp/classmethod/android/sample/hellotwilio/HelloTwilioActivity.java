package jp.classmethod.android.sample.hellotwilio;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.twilio.client.Connection;
import com.twilio.client.ConnectionListener;
import com.twilio.client.Device;
import com.twilio.client.Twilio;

public class HelloTwilioActivity extends FragmentActivity implements OnClickListener, Twilio.InitListener, LoaderCallbacks<Device>, ConnectionListener {

    private static final String TAG = HelloTwilioActivity.class.getSimpleName();
    private static final int TWILIO_PHONE_LOADER = 0;
    private Device mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_twilio);
        // Connect�{�^��
        findViewById(R.id.button).setOnClickListener(this);
        // Twilio�N���C�A���g������������
        Twilio.initialize(getApplicationContext(), this);
    }

    @Override
    public void onInitialized() {
        Log.d(TAG, "Twilio�N���C�A���g�̏��������������܂���");
        // TwilioPhoneLoader���Ăяo��
        getSupportLoaderManager().initLoader(TWILIO_PHONE_LOADER, null, this);
    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, "�G���[���������܂���:" + e.getLocalizedMessage());
    }
    
    @Override
    protected void finalize() {
        if (mDevice != null) {
            mDevice.release();
        }
    }

    @Override
    public void onClick(View v) {
        if (mDevice != null) {
            // ���͂��ꂽ���b�Z�[�W���p�����[�^�ɓn���A�ڑ�����
            EditText editText = (EditText) findViewById(R.id.edit_text);
            String message = editText.getText().toString();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("message", message);
            mDevice.connect(params, this);
        } else {
            Log.e(TAG, "�f�o�C�X������܂���");
        }
    }

    @Override
    public Loader<Device> onCreateLoader(int id, Bundle bundle) {
        DeviceLoader loader = new DeviceLoader(getApplicationContext());
        loader.forceLoad();
        return loader;
    }
    
    @Override
    public void onLoadFinished(Loader<Device> loader, Device device) {
        Log.d(TAG, "�f�o�C�X�̏������������܂���");
        mDevice = device;
        getSupportLoaderManager().destroyLoader(TWILIO_PHONE_LOADER);
    }
    
    @Override
    public void onLoaderReset(Loader<Device> loader) {
    }

    @Override
    public void onConnected(Connection connection) {
        Log.d(TAG, "onConnected");
    }

    @Override
    public void onConnecting(Connection connection) {
        Log.d(TAG, "onConnecting");
    }

    @Override
    public void onDisconnected(Connection connection) {
        Log.d(TAG, "onDisconnected");
    }

    @Override
    public void onDisconnected(Connection connection, int arg1, String arg2) {
        Log.d(TAG, "onDisconnected");
    }

}
