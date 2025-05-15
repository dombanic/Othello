// Util.java

public class Util
{
	public static int random(int min, int max)
	{
		int range = max - min + 1;
		return (int) (Math.random() * range + min);
	}


   public static void delay(int n)
	{
		long startDelay = System.currentTimeMillis();
		long endDelay = 0;
		while (endDelay - startDelay < n)
			endDelay = System.currentTimeMillis();
	}  
}



