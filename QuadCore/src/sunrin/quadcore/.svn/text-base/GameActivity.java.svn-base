package sunrin.quadcore;

import java.util.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.hardware.SensorManager;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import Helper.*;

public class GameActivity extends Activity{
	int width,height;
	ProgressDialog dialog;
	OrientationEventListener mOrientationListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		width = new Util().getWidth(getApplicationContext());
		height= new Util().getHeight(getApplicationContext());
		
		Global.init();
		
		setContentView(LayoutInflater.from(this).inflate(R.layout.cv_drop, null), new LayoutParams(width, height));
		
		final BalanceObjectView balanceView = (BalanceObjectView)findViewById(R.id.cv_pacman);
		
		mOrientationListener = new OrientationEventListener(this,
				SensorManager.SENSOR_DELAY_GAME) {
			public void onOrientationChanged(int orientation) {
				Global.setmOrientation(orientation);
				balanceView.invalidate();
			}
		};
		mOrientationListener.enable();
		
		class LifeThread extends Thread{

			public void run(){
				while(Global.isLife() == 1){}
				if(Global.isLife() == 0){
					progress.sendEmptyMessage(0);
					try
                    {
	                    Thread.sleep(2000);
	                    Global.TheoremThread();
                    }
                    catch(InterruptedException e)
                    {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
                    }
					progress.sendEmptyMessage(1);
					new Util().startActivity(GameActivity.this, GameOverActivity.class);
				}
				else if(Global.isLife() == -1){
					progress.sendEmptyMessage(0);
					try
                    {
	                    Thread.sleep(2000);
	                    Global.TheoremThread();
                    }
                    catch(InterruptedException e)
                    {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
                    }
					progress.sendEmptyMessage(1);
					finish();
				}
			}
		}

		LifeThread l_thread = new LifeThread();
		l_thread.start();
	}

	public Handler progress = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what == 0)
				dialog =  ProgressDialog.show(GameActivity.this, null,"게임을 종료중입니다.",true);
				
			else if(msg.what == 1)
				dialog.dismiss();
		}
	};
	
	@Override
	public void onBackPressed(){
		Global.setLife(-1);
	}

}