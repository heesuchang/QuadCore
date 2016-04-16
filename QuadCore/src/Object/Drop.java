package Object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Drop extends Ball
{

	public Drop(int radius, int x, int y)
	{
		setX(x);
		setY(y);
		setRadius(radius);
	}
}