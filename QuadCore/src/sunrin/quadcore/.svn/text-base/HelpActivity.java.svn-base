package sunrin.quadcore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class HelpActivity extends Activity
{
	ImageView next,info,img;
	int cnt = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.a_information);
	    
	    find();
	    Listener();
	}
	
	public void find(){
		next = (ImageView)findViewById(R.id.btn_play);
		info = (ImageView)findViewById(R.id.info1);
		}
	
	public void Listener(){
		next.setOnClickListener(new OnClickListener(){

			public void onClick(View v)
            {
	            // TODO Auto-generated method stub
	            switch(cnt){
	            case 0:
	            	//img.setBackgroundResource(R.drawable.info_drop);
	            	info.setBackgroundResource(R.drawable.info2);
	            	cnt++;
	            	break;
	            case 1:
	            	//img.setBackgroundResource(R.drawable.info_packman);
	            	info.setBackgroundResource(R.drawable.info3);
	            	cnt++;
	            	break;
	            case 2:
	            	//img.setBackgroundResource(R.drawable.info_drag);
	            	info.setBackgroundResource(R.drawable.info4);
	            	cnt++;
	            	break;
	            case 3:
	            	//img.setBackgroundResource(R.drawable.info_jumping);
	            	info.setBackgroundResource(R.drawable.info5);
	            	cnt++;
	            	break;
	            case 4:
	            	Intent i = new Intent(HelpActivity.this, MainActivity.class);
	            	startActivity(i);
	            	break;
	            }
            }
			
		});
	}
}
