// Coordinate.java

import java.awt.*;
import java.util.*;

public class Coordinate
{
	private int row, column;
	private boolean available;

	public Coordinate()
	{
		row = -1;
		column = -1;
		available = false;
	}

	public Coordinate(int r, int c)
	{
		row = r;
		column = c;
		available = true;
	}

	public Coordinate(Cell cell)
	{
		row = cell.getRow();
		column = cell.getColumn();
		available = true;
	}

	public Coordinate(int number)
	{
		number--;
		row = number / 3;
		column = number % 3;
		available = true;
	}

	public int getRow()
	{
		return row;
	}

	public int getColumn()
	{
		return column;
	}

	public boolean isAvailable()
	{
		return available;
	}

	public boolean equals(Coordinate that)
	{
		return this.row == that.row && this.column == that.column;
	}
}



