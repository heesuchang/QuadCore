package Helper;

import sunrin.quadcore.*;
import android.util.Log;

public class Global
{
	private static int Life;
	private static int Score;
	private static int mOrientation;
	
	public static void init(){
		setLife(1);
		setScore(0);
		setmOrientation(-1);
	}
	
	public static void TheoremThread(){
		Log.e("Destroy", "jumping");
		boolean retry = true;
		while (retry) {
			try {
				JumpingObjectView.u_thread.join();
				DropObjectView.m_thread.join();
				DragObjectView.m_thread.join();
				BalanceObjectView.balance.join();
				BalanceObjectView.score.join();
				retry = false;
				Global.setScore(Global.getScore()-1);
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public static int isLife()
    {
	    return Life;
    }

	public static void setLife(int life)
    {
	    Life = life;
    }

	public static int getScore()
    {
	    return Score;
    }

	public static void setScore(int score)
    {
	    Score = score;
    }

	public static int getmOrientation()
    {
	    return mOrientation;
    }

	public static void setmOrientation(int mOrientation)
    {
	    Global.mOrientation = mOrientation;
    }
}
