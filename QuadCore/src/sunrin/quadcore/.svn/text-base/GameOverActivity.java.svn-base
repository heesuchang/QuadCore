package sunrin.quadcore;

import Helper.Global;
import Helper.Util;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOverActivity extends Activity
{

	TextView score;
	ImageView ok,highscore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.a_gameover);
	    
	    find();
	    
	    score.setText(Global.getScore()+"");

        SharedPreferences prefs = getSharedPreferences("score", MODE_PRIVATE);
        int first = prefs.getInt("first", 0);
        int second = prefs.getInt("second", 0);
        int third = prefs.getInt("third", 0);
        if(first < Global.getScore()){
        	new Util().setPref(prefs, "third", prefs.getInt("second", 0));
        	new Util().setPref(prefs, "second", prefs.getInt("first", 0));
        	new Util().setPref(prefs, "first", Global.getScore());
        }
        else if(second < Global.getScore()){
        	new Util().setPref(prefs, "third", prefs.getInt("second", 0));
        	new Util().setPref(prefs, "second", Global.getScore());
        }
        
        else if(third < Global.getScore())
        	new Util().setPref(prefs, "third", Global.getScore());
        
        Listener();
	}
	
	public void find(){
		score = (TextView)findViewById(R.id.tv_score);
		ok = (ImageView)findViewById(R.id.okd);
		highscore = (ImageView)findViewById(R.id.highscored);
	}
	
	public void Listener(){
		ok.setOnClickListener(new ImageView.OnClickListener(){

			public void onClick(View v)
            {
	            finish();
            }
			
		});
		
		highscore.setOnClickListener(new ImageView.OnClickListener(){

			public void onClick(View v)
            {
	            new Util().startActivity(GameOverActivity.this, BestScoreActivity.class);
            }
			
		});
	}
}