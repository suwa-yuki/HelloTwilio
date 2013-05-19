package jp.classmethod.android.sample.hellotwilio;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.twilio.client.Device;
import com.twilio.client.Twilio;

public class DeviceLoader extends AsyncTaskLoader<Device> {

    private static final String TAG = DeviceLoader.class.getSimpleName();

    public DeviceLoader(Context context) {
        super(context);
    }

    @Override
    public Device loadInBackground() {
        try {
            // �P�C�p�r���e�B�g�[�N�����擾����
            String url = "http://hello-twilio2.herokuapp.com/auth";
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            HttpResponse res = httpClient.execute(get);
            HttpEntity entity = res.getEntity();
            String token = EntityUtils.toString(entity);
            Log.d(TAG, "�g�[�N��:" + token);
            // Twilio�Ƀf�o�C�X��o�^����
            return Twilio.createDevice(token, null);
        } catch (Exception e) {
            Log.e(TAG, "�G���[:" + e.getLocalizedMessage());
        }
        return null;
    }

}
