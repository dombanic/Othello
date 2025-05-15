// GameThing.java

import java.awt.*;
import java.applet.*;

abstract public class GameThing
{
	protected int x, y;
	protected Color color;
	public static final int windowWidth  = 1550;
	public static final int windowHeight = 946;

	public GameThing(int x, int y, Color c)
	{
		this.x = x;
		this.y = y;
		color  = c;
	}

	public  int  getX()      {  return x;      }
	public  int  getY()      {  return y;      }
	public Color getColor()  {  return color;  }
   
	public static int convert(int c)
	{
		return c * 117 + 10;
	}

	abstract public void display(Graphics g);
}



