package sunrin.quadcore;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class BestScoreActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_bestscore);
		
        SharedPreferences prefs = getSharedPreferences("score", MODE_PRIVATE);
        TextView first = (TextView)findViewById(R.id.best_1);
        TextView second = (TextView)findViewById(R.id.best_2);
        TextView third = (TextView)findViewById(R.id.best_3);

        first.setText("1등  : "+prefs.getInt("first", 0)+"점");
        second.setText("2등  : "+prefs.getInt("second", 0)+"점");
        third.setText("3등  : "+prefs.getInt("third", 0)+"점");
	}
}
