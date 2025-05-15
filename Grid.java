// Grid.java

import java.awt.*;
import java.util.*;

public class Grid extends GameThing
{
	private Cell cells[][];
	private int size;
	private int numBlack, numWhite, numMoves;
	private boolean blackTurn;
	private boolean anyMovesLeft;

	public Grid()
	{
		super(0,0,Color.black);
		size = 8;
		cells = new Cell[size][size];
		numBlack = numWhite = 2;
		numMoves = numBlack + numWhite;

		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				cells[r][c] = new Cell(r, c, new Color (0,128,0));  // darkGreen
	}
   
   public Grid(Grid that)
   {
      super(0,0,Color.black);
      size = that.size;
      numBlack = that.numBlack;
      numWhite = that.numWhite;
      numMoves = that.numMoves;
      blackTurn = that.blackTurn;
      anyMovesLeft = that.anyMovesLeft;

      cells = new Cell[size][size];
      for (int r = 0; r < size; r++)
      {
         for (int c = 0; c < size; c++)
         {
            Cell oldCell = that.cells[r][c];
            cells[r][c] = new Cell(oldCell.getX(), oldCell.getY(), oldCell.getColor());
            
            if (!oldCell.isBlank())
            {
                if (oldCell.isBlack())
                {
                    this.cells[r][c].placeBlack();
                }
                else if (oldCell.isWhite())
                {
                    this.cells[r][c].placeWhite();
                }
            }

            if (oldCell.isValidMove())
            {
                this.cells[r][c].makeMoveValid();
            }
            else
            {
                this.cells[r][c].makeMoveInvalid();
            }
         }
      }
   }


	public void updateValidMoves(boolean bt)
	{
		blackTurn = bt;
		makeAllMovesInvalid();
		setValidMoves(blackTurn);
	}

	public void display(Graphics g)
	{
		countBlackAndWhite();
		displayBoard(g);
		displayStats(g);
	}

	public void displayBoard(Graphics g)
	{
		g.setColor(color);
		g.fillRect(0,0,windowWidth,windowHeight);
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				cells[r][c].display(g);
	}

	public void countBlackAndWhite()
	{
		numBlack = numWhite = 0;
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				if (cells[r][c].isBlack())
					numBlack++;
				else if (cells[r][c].isWhite())
					numWhite++;
		numMoves = numBlack + numWhite;
	}

	public int getNumBlack()
	{
		return numBlack;
	}

	public int getNumWhite()
	{
		return numWhite;
	}

	public int getNumMoves()
	{
		return numMoves;
	}

	private void displayStats(Graphics g)
	{
		g.setColor(Color.blue);  
		g.fillRect(947,10,592,926);
		g.setColor(Color.red);
		g.setFont(new Font("Algerian",Font.PLAIN,120));
		g.drawString("OTHELLO",995,150);
		g.setFont(new Font("Arial",Font.BOLD,72));

      g.setColor(new Color(0,128,128));  // darkCyan
   	if (blackTurn)
   		g.fillRect(1003,225,481,100);
   	else
   		g.fillRect(1003,375,481,100);

		g.setColor(Color.black);
		g.drawString("# BLACK:",1020,300);
		g.drawString(""+numBlack,1390,300);
		g.setColor(Color.white);
		g.drawString("# WHITE:",1021,450);
		g.drawString(""+numWhite,1390,450);
	}

	public int getSize()
	{
		return size;
	}

	public Cell getCell(int r, int c)
	{
		return cells[r][c];
	}

	public boolean isBlank(int r, int c)
	{
		return cells[r][c].isBlank();
	}

	public boolean isValidMove(int r, int c)
	{
		return cells[r][c].isValidMove();
	}

	public boolean canMove()
	{
		return anyMovesLeft;
	}

	public void makeAllMovesInvalid()
	{
		anyMovesLeft = false;
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				cells[r][c].makeMoveInvalid();
	}
   
	public void setValidMoves(boolean blackTurn)
	{
		String player, opponent;

		if (blackTurn)
		{
			player = "B";
			opponent = "W";
		}
		else
		{
			player = "W";
			opponent = "B";
		}

		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
			{
				if (checkN(r,c,player,opponent))
				{
					cells[r][c].makeMoveValid();
					anyMovesLeft = true;
				}
				else if (checkNE(r,c,player,opponent))
				{
					cells[r][c].makeMoveValid();
					anyMovesLeft = true;
				}
				else if (checkE(r,c,player,opponent))
				{
					cells[r][c].makeMoveValid();
					anyMovesLeft = true;
				}
				else if (checkSE(r,c,player,opponent))
				{
					cells[r][c].makeMoveValid();
					anyMovesLeft = true;
				}
				else if (checkS(r,c,player,opponent))
				{
					cells[r][c].makeMoveValid();
					anyMovesLeft = true;
				}
				else if (checkSW(r,c,player,opponent))
				{
					cells[r][c].makeMoveValid();
					anyMovesLeft = true;
				}
				else if (checkW(r,c,player,opponent))
				{
					cells[r][c].makeMoveValid();
					anyMovesLeft = true;
				}
				else if (checkNW(r,c,player,opponent))
				{
					cells[r][c].makeMoveValid();
					anyMovesLeft = true;
				}
			}
	}

	private boolean checkN(int r, int c, String player, String opponent)
	{
		if (!cells[r][c].isBlank())
			return false;
		int originalR = r;
		do
		{
			r--;
		}
		while (r >= 0 && cells[r][c].equals(opponent));

		if (r >= 0 && originalR-r >= 2 && cells[r][c].equals(player))
			return true;
		return false;
	}

	private boolean checkNE(int r, int c, String player, String opponent)
	{
		if (!cells[r][c].isBlank())
			return false;
		int originalR = r;
		do
		{
			r--;
			c++;
		}
		while (r >= 0 && c < size && cells[r][c].equals(opponent));

		if (r >= 0 && c < size && originalR-r >= 2 && cells[r][c].equals(player))
			return true;
		return false;
	}

	private boolean checkE(int r, int c, String player, String opponent)
	{
		if (!cells[r][c].isBlank())
			return false;
		int originalC = c;
		do
		{
			c++;
		}
		while (c < size && cells[r][c].equals(opponent));

		if (c < size && c-originalC >= 2 && cells[r][c].equals(player))
			return true;
		return false;
	}

	private boolean checkSE(int r, int c, String player, String opponent)
	{
		if (!cells[r][c].isBlank())
			return false;
		int originalR = r;
		do
		{
			r++;
			c++;
		}
		while (r < size && c < size && cells[r][c].equals(opponent));

		if (r < size && c < size && r-originalR >= 2 && cells[r][c].equals(player))
			return true;
		return false;
	}

	private boolean checkS(int r, int c, String player, String opponent)
	{
		if (!cells[r][c].isBlank())
			return false;
		int originalR = r;
		do
		{
			r++;
		}
		while (r < size && cells[r][c].equals(opponent));

		if (r < size && r-originalR >= 2 && cells[r][c].equals(player))
			return true;
		return false;
	}

	private boolean checkSW(int r, int c, String player, String opponent)
	{
		if (!cells[r][c].isBlank())
			return false;
		int originalR = r;
		do
		{
			r++;
			c--;
		}
		while (r < size && c >= 0 && cells[r][c].equals(opponent));

		if (r < size && c >= 0 && r-originalR >= 2 && cells[r][c].equals(player))
			return true;
		return false;
	}

	private boolean checkW(int r, int c, String player, String opponent)
	{
		if (!cells[r][c].isBlank())
			return false;
		int originalC = c;
		do
		{
			c--;
		}
		while (c >= 0 && cells[r][c].equals(opponent));

		if (c >= 0 && originalC-c >= 2 && cells[r][c].equals(player))
			return true;
		return false;
	}

	private boolean checkNW(int r, int c, String player, String opponent)
	{
		if (!cells[r][c].isBlank())
			return false;
		int originalR = r;
		do
		{
			r--;
			c--;
		}
		while (r >= 0 && c >= 0 && cells[r][c].equals(opponent));

		if (r >= 0 && c >= 0 && originalR-r >= 2 && cells[r][c].equals(player))
			return true;
		return false;
	}

	public void flipPieces(boolean blackTurn, int r, int c)
	{
		String player, opponent;

		if (blackTurn)
		{
			player = "B";
			opponent = "W";
		}
		else
		{
			player = "W";
			opponent = "B";
		}

		flipN(r,c,player,opponent);
		flipNE(r,c,player,opponent);
		flipE(r,c,player,opponent);
		flipSE(r,c,player,opponent);
		flipS(r,c,player,opponent);
		flipSW(r,c,player,opponent);
		flipW(r,c,player,opponent);
		flipNW(r,c,player,opponent);
	}

	private void flipN(int r, int c, String player, String opponent)
	{
		if (checkN(r,c,player,opponent))
		{
			r--;
			while (r >= 0 && cells[r][c].equals(opponent))
			{
				cells[r][c].flip(r,c);
				r--;
			}
		}
	}

	private void flipNE(int r, int c, String player, String opponent)
	{
		if (checkNE(r,c,player,opponent))
		{
			r--;
			c++;
			while (r >= 0 && c < size && cells[r][c].equals(opponent))
			{
				cells[r][c].flip(r,c);
				r--;
				c++;
			}
		}
	}

	private void flipE(int r, int c, String player, String opponent)
	{
		if (checkE(r,c,player,opponent))
		{
			c++;
			while (c < size && cells[r][c].equals(opponent))
			{
				cells[r][c].flip(r,c);
				c++;
			}
		}
	}

	private void flipSE(int r, int c, String player, String opponent)
	{
		if (checkSE(r,c,player,opponent))
		{
			r++;
			c++;
			while (r < size && c < size && cells[r][c].equals(opponent))
			{
				cells[r][c].flip(r,c);
				r++;
				c++;
			}
		}
	}

	private void flipS(int r, int c, String player, String opponent)
	{
		if (checkS(r,c,player,opponent))
		{
			r++;
			while (r < size && cells[r][c].equals(opponent))
			{
				cells[r][c].flip(r,c);
				r++;
			}
		}
	}

	private void flipSW(int r, int c, String player, String opponent)
	{
		if (checkSW(r,c,player,opponent))
		{
			r++;
			c--;
			while (r < size && c >= 0 && cells[r][c].equals(opponent))
			{
				cells[r][c].flip(r,c);
				r++;
				c--;
			}
		}
	}

	private void flipW(int r, int c, String player, String opponent)
	{
		if (checkW(r,c,player,opponent))
		{
			c--;
			while (c >= 0 && cells[r][c].equals(opponent))
			{
				cells[r][c].flip(r,c);
				c--;
			}
		}
	}

	private void flipNW(int r, int c, String player, String opponent)
	{
		if (checkNW(r,c,player,opponent))
		{
			r--;
			c--;
			while (r >= 0 && c >= 0 && cells[r][c].equals(opponent))
			{
				cells[r][c].flip(r,c);
				r--;
				c--;
			}
		}
	}
}



