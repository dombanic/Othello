// Cell.java

import java.awt.*;

public class Cell extends GameThing
{
	private Piece piece;
	private int width, height;
	private Rectangle area;
	private int row, column;
	private boolean validMove;
	private boolean justPlaced;

	public Cell(int r, int c, Color color)
	{
		super(convert(c),convert(r),color);
		piece = new Piece("",r,c,color);
		row = r;
		column = c;
		width = height = 107;
		area = new Rectangle(x+11,y+49,width,height);
		validMove = false;
		justPlaced = false;
	}

	public void display(Graphics g)
	{
		if (justPlaced)
		{
			color = new Color(255,128,128);  // lightRed
			justPlaced = false;
		}
		g.setColor(color);
		g.fillRect(x,y,width,height);
		if (!piece.isBlank())
			piece.display(g);
	}
  
	public Piece getPiece()
	{
		return piece;
	}

	public boolean isBlank()
	{
		return piece.isBlank();
	}

	public boolean isWhite()
	{
		return piece.isWhite();
	}

	public boolean isBlack()
	{
		return piece.isBlack();
	}

	public void flip(int r, int c)
	{
		if (piece.isBlack())
			piece = new White(r, c);
		else if (piece.isWhite())
			piece = new Black(r, c);
	}

	public boolean isValidMove()
	{
		return validMove;
	}

	public void makeMoveValid()
	{
		validMove = true;
		color = Color.green;
	}

	public void makeMoveInvalid()
	{
		validMove = false;
		color = new Color (0,128,0);  // darkGreen
	}

	public boolean equals(Cell that)
	{
		return !isBlank() && this.piece.equals(that.piece);
	}

	public boolean equals(String that)
	{
		return this.piece.equals(that);
	}

	public boolean equals(String piece, Cell that)
	{
		return this.piece.equals(piece) && that.piece.equals(piece);
	}

	public boolean equals(String piece, Cell that, Cell theOther)
	{
		return this.piece.equals(piece) && that.piece.equals(piece)
			&& theOther.piece.equals(piece);
	}

	public boolean clicked(int x, int y)
	{
		return piece.isBlank() && area.inside(x,y);
	}

	private boolean first4Pieces(int r, int c)
	{
		return  (r == 3 && c == 3) || (r == 3 && c == 4) || (r == 4 && c == 3) || (r == 4 && c == 4);
	}

	public void placeBlack()
	{
		piece = new Black(row, column);
		if (!first4Pieces(row,column))
			justPlaced = true;
	}

	public void placeWhite()
	{
		piece = new White(row, column);
		if (!first4Pieces(row,column))
			justPlaced = true;
	}

	public int getRow()
	{
		return row;
	}

	public int getColumn()
	{
		return column;
	}   

   public String toString()
   {
      return "Row: "+row+"   Column: "+column;
   }   
}



