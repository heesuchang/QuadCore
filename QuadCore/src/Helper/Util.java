package Helper;
import android.os.*;
import android.app.*;
import android.content.*;
import android.view.*;

public class Util{
	public void startActivity(Activity activity ,Class<?> nextactivity){
		Intent intent = new Intent(activity,nextactivity);
		activity.startActivity(intent);
		activity.finish();
	}
	
	public void e_startActivity(Activity activity ,Class<?> nextactivity){
		Intent intent = new Intent(activity,nextactivity);
		activity.startActivity(intent);
	}
	
	public int getWidth(Context activity){
		Display display = ((WindowManager)activity.getSystemService(Service.WINDOW_SERVICE)).getDefaultDisplay();
		
		return display.getWidth();
	}
	
	public int getHeight(Context activity){
		Display display = ((WindowManager)activity.getSystemService(Service.WINDOW_SERVICE)).getDefaultDisplay();
		
		return display.getHeight();
	}
	
	public void setPref(SharedPreferences pref , String key , String value){
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public void setPref(SharedPreferences pref , String key , int value){
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void setPref(SharedPreferences pref , String key , boolean value){
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
}
