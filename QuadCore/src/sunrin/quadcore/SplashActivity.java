package sunrin.quadcore;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import Helper.Util;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_spalsh);
        mAferSplash.sendEmptyMessageDelayed(0, 2000);
    }
    
    Handler mAferSplash = new Handler(){
    	public void handleMessage(Message msg){
    		new Util().startActivity(SplashActivity.this, MainActivity.class);
    	}
    };
}
