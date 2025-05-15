// Piece.java


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class Piece extends GameThing
{
	protected String piece;
	protected int size;
	protected int row, column;

	public Piece(String let, int r, int c, Color color)
	{
		super(convert(c),convert(r),color);
		piece = let;
		row = r;
		column = c;
		size = 97;
	}

	public Piece(char let, int r, int c, Color color)
	{
		super(convert(c),convert(r),color);
		piece = String.valueOf(let);
		row = r;
		column = c;
		size = 97;
	}

	public boolean equals(Piece that)
	{
		return this.piece.equals(that.piece);
	}

	public boolean equals(String that)
	{
		return this.piece.equals(that);
	}

	public void display(Graphics g)
	{
		g.setColor(color);
		g.fillOval(x+5,y+5,size,size);
	}

	public boolean isBlank()
	{
		return piece.equals("");
	}

	public boolean isWhite()
	{
		return piece.equals("W");
	}

	public boolean isBlack()
	{
		return piece.equals("B");
	}

	public void assign(String let)
	{
		piece = let;
	}
}



