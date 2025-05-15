// Othello.java
// The provide files comprise a functional 2-Player Othello game.
// It is based on the same template used for the TicTacToe examples.
// Students need to convert this to a 1-Player game where the user
// plays against the computer's A.I.


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class Othello
{
	public static void main(String args[])
	{
		OthelloGame game = new OthelloGame();
		game.addWindowListener(new WindowAdapter()
		{public void windowClosing(WindowEvent e)
		{System.exit(0);}});
	}
}


class OthelloGame extends Frame implements MouseListener
{
	private Graphics gBuffer;
   private Image virtualMem;
   private Grid grid = new Grid();
	private boolean blackTurn;
   private boolean userIsBlack;
	private boolean blackCanMove, whiteCanMove;
	private boolean gameOver;
	private boolean gameConstructed = false;
   private int level;
   
   private double blackwin = 0;
   private double whitewin = 0;

	public OthelloGame()
	{
		super(" OTHELLO  (A minute to learn...  A lifetime to master!)   By: Mr. John L. M. Schram");
		setSize(GameThing.windowWidth+22,GameThing.windowHeight+60);
		setVisible(true);
		virtualMem = createImage(GameThing.windowWidth,GameThing.windowHeight);
		gBuffer = virtualMem.getGraphics();
		addMouseListener(this);
		blackTurn = true;
      userIsBlack = Util.random(1,2) == 1;
		gameOver = false;
		blackCanMove = whiteCanMove = true;
      
		grid.getCell(3,3).placeWhite();
		grid.getCell(4,4).placeWhite();
		grid.getCell(3,4).placeBlack();
		grid.getCell(4,3).placeBlack();
      enterGameData();
      
		gameConstructed = true;
		repaint();
	}

	public void paint(Graphics g)
	{
		if (gameConstructed)
		{
			Graphics2D g2D = (Graphics2D) g;
			g2D.translate(11,49);

			grid.updateValidMoves(blackTurn);
			grid.display(gBuffer);
			checkIfAnyMovesLeft(gBuffer);

			g.drawImage (virtualMem,0,0,this);
         
         if (!gameOver && !blackTurn && userIsBlack)
				getMoveWhite(level);
			else if (!gameOver && blackTurn && !userIsBlack)
				getMoveBlack();
         /*
         if (gameOver)
         {
            Util.delay(100);
            if (grid.getNumBlack() > grid.getNumWhite())
               blackwin++;
            else if (grid.getNumWhite() > grid.getNumBlack())
               whitewin++;
            blackTurn = true;
            grid = new Grid();
            grid.getCell(3,3).placeWhite();
		      grid.getCell(4,4).placeWhite();
		      grid.getCell(3,4).placeBlack();
		      grid.getCell(4,3).placeBlack();
            gameOver = false;
            repaint();
            System.out.println(blackwin/whitewin);
         }
         */
		}
	}
   
   public void enterGameData()
	{
		level = 4;  // default set at the highest level

		do
			{
				level = Integer.parseInt(JOptionPane.showInputDialog("Select difficulty level: \n 1 = Beginner \n 2 = Intermediate \n 3 = Advanced \n 4 = Expert"));
			}
			while (level < 1 || level > 4);
	}

   public void mouseEntered(MouseEvent e)   { }
   public void mouseExited(MouseEvent e)    { }
   public void mouseClicked(MouseEvent e)   { }
   public void mouseReleased(MouseEvent e)  { }

	public void checkIfAnyMovesLeft(Graphics g)
	{
		if (blackTurn)
		{
			blackCanMove = grid.canMove();
			if (!blackCanMove)
			{
				grid.updateValidMoves(false);
				whiteCanMove = grid.canMove();
			}
		}
		else
		{
			whiteCanMove = grid.canMove();
			if (!whiteCanMove)
			{
				grid.updateValidMoves(true);
				blackCanMove = grid.canMove();
			}
		}

		gameOver = grid.getNumMoves() >= 64 ||
			        grid.getNumBlack() ==  0 ||
			        grid.getNumWhite() ==  0 ||
			        (!blackCanMove && !whiteCanMove);

		if (gameOver)
			endGame(g);
		else
		{
			if (blackTurn && !blackCanMove)
				blackPass(g);
			else if (!blackTurn && !whiteCanMove)
				whitePass(g);
		}
	}

	public void blackPass(Graphics g)
	{
		blackTurn = false;
		grid.updateValidMoves(blackTurn);
		grid.display(g);
		g.setColor(Color.magenta);
		g.fillRect(993,625,505,250);
		g.setFont(new Font("Arial Narrow",Font.ITALIC,64));
		g.setColor(Color.black);
		g.drawString("Black cannot move.",1015,720);
		g.setColor(Color.white);
		g.drawString("White goes again!",1015,830);
	}

	public void whitePass(Graphics g)
	{
		blackTurn = true;
		grid.updateValidMoves(blackTurn);
		grid.display(g);
		g.setColor(Color.magenta);
		g.fillRect(993,625,505,250);
		g.setFont(new Font("Arial Narrow",Font.ITALIC,64));
		g.setColor(Color.white);
		g.drawString("White cannot move.",1015,720);
		g.setColor(Color.black);
		g.drawString("Black goes again!",1015,830);
	}
   
	public void endGame(Graphics g)
	{
		g.setColor(Color.magenta);
		g.fillRect(993,625,505,250);
		g.setFont(new Font("Impact",Font.ITALIC,96));

		if (grid.getNumBlack() > grid.getNumWhite())
		{
			g.setColor(Color.black);
			g.drawString("Game Over",1028,730);
			g.drawString("Black Wins!",1008,840);  
		}
		else if (grid.getNumWhite() > grid.getNumBlack())
		{
			g.setColor(Color.white);
			g.drawString("Game Over",1028,730);
			g.drawString("White Wins!",999,840);  
		}
		else
		{
			g.setColor(Color.yellow);
			g.drawString("Game Over",1028,730);
			g.drawString("TIE!",1175,838);    
		}
	}

   public void mousePressed(MouseEvent e)
   {
    	if (gameOver)
    		return;
         
		for (int r = 0; r < grid.getSize(); r++)
			for (int c = 0; c < grid.getSize(); c++)
				if (grid.getCell(r,c).clicked(e.getX(),e.getY()) && grid.getCell(r,c).isValidMove())
				{
					if (blackTurn)
					{
						grid.flipPieces(blackTurn,r,c);
						grid.getCell(r,c).placeBlack();
                  grid.makeAllMovesInvalid();
						blackTurn = false;
					}
					else
					{
						grid.flipPieces(blackTurn,r,c);
						grid.getCell(r,c).placeWhite();
                  grid.makeAllMovesInvalid();
						blackTurn = true;
					}
				}
		repaint();
   }

	public void update(Graphics g)
	{
		paint(g);
	}
   
   public void getMoveWhite(int level)
	{
		int row = 0, column = 0;
      Util.delay(1500);
      boolean corner = false;
      
      if (level == 1)
      {
         do
            {
              	row = Util.random(0,7);
              	column = Util.random(0,7);
            }
            while (!grid.getCell(row,column).isValidMove());
      }
      
      else if (level == 2)
      {
         if (grid.getCell(0,0).isValidMove())
         {
            row = 0;
            column = 0;
            corner = true;
         }
         else if (grid.getCell(0,7).isValidMove())
         {
            row = 0;
            column = 7;
            corner = true;
         }
         else if (grid.getCell(7,0).isValidMove())
         {
            row = 7;
            column = 0;
            corner = true;
         }
         else if (grid.getCell(7,7).isValidMove())
         {
            row = 7;
            column = 7;
            corner = true;
         }
         else
         {
            do
            {
               row = Util.random(0,7);
               column = Util.random(0,7);
            }
            while (!grid.getCell(row,column).isValidMove());
         }
      }
      
      else if (level == 3)
      {
         if (grid.getCell(0,0).isValidMove())
         {
            row = 0;
            column = 0;
            corner = true;
         }
         else if (grid.getCell(0,7).isValidMove())
         {
            row = 0;
            column = 7;
            corner = true;
         }
         else if (grid.getCell(7,0).isValidMove())
         {
            row = 7;
            column = 0;
            corner = true;
         }
         else if (grid.getCell(7,7).isValidMove())
         {
            row = 7;
            column = 7;
            corner = true;
         }
         else
         {
            do
            {
               row = Util.random(0,7);
               column = Util.random(0,7);
            }
            while (!grid.getCell(row,column).isValidMove());
         }
         
         if(!corner)
         {
            for (int r = 2; r <=5; r++)
            {
               if (grid.getCell(r,0).isValidMove())
               {
                  row = r;
                  column = 0;
               }
               else if (grid.getCell(r,7).isValidMove())
               {
                  row = r;
                  column = 7;
               }
            }
            
            for (int c = 2; c <=5; c++)
            {
               if (grid.getCell(0,c).isValidMove())
               {
                  row = 0;
                  column = c;
               }
               else if (grid.getCell(7,c).isValidMove())
               {
                  row = 7;
                  column = c;
               }
            }
         }
      }
      
      else if (level == 4)
      {
         if (grid.getCell(0,0).isValidMove())
         {
            row = 0;
            column = 0;
         }
         else if (grid.getCell(0,7).isValidMove())
         {
            row = 0;
            column = 7;
         }
         else if (grid.getCell(7,0).isValidMove())
         {
            row = 7;
            column = 0;
         }
         else if (grid.getCell(7,7).isValidMove())
         {
            row = 7;
            column = 7;
         }
         else
         {
            do
            {
              	row = Util.random(0,7);
              	column = Util.random(0,7);
            }
            while (!grid.getCell(row,column).isValidMove());
            
            int whiteP = 0;
            grid.countBlackAndWhite();
            int thingy = grid.getNumWhite();
            
            for (int r = 0; r <= 7; r++)
            {
               for (int c = 0; c <= 7; c++)
               {
                  if (grid.getCell(r,c).isValidMove())
                  {
                     Grid grid2 = new Grid(grid);
                     grid2.flipPieces(blackTurn,r,c);
   		            grid2.getCell(r,c).placeWhite();
                     grid2.countBlackAndWhite();
                     int whiteP2 = grid2.getNumWhite() - thingy;
                     
                     if (whiteP2 > whiteP)
                     {
                        whiteP = whiteP2;
                        row = r;
                        column = c;
                     }
                  }
               }
            }
         }
      }
      
		grid.flipPieces(blackTurn,row,column);
		grid.getCell(row,column).placeWhite();
		grid.makeAllMovesInvalid();
		blackTurn = true;
		repaint();
	}

	public void getMoveBlack()
	{
		int row = 0, column = 0;
      Util.delay(1500);
      boolean corner = false;
      
      if (level == 1)
      {
         do
            {
              	row = Util.random(0,7);
              	column = Util.random(0,7);
            }
            while (!grid.getCell(row,column).isValidMove());
      }
      
      else if (level == 2)
      {
         if (grid.getCell(0,0).isValidMove())
         {
            row = 0;
            column = 0;
            corner = true;
         }
         else if (grid.getCell(0,7).isValidMove())
         {
            row = 0;
            column = 7;
            corner = true;
         }
         else if (grid.getCell(7,0).isValidMove())
         {
            row = 7;
            column = 0;
            corner = true;
         }
         else if (grid.getCell(7,7).isValidMove())
         {
            row = 7;
            column = 7;
            corner = true;
         }
         else
         {
            do
            {
               row = Util.random(0,7);
               column = Util.random(0,7);
            }
            while (!grid.getCell(row,column).isValidMove());
         }
      }
      
      else if (level == 3)
      {
         if (grid.getCell(0,0).isValidMove())
         {
            row = 0;
            column = 0;
            corner = true;
         }
         else if (grid.getCell(0,7).isValidMove())
         {
            row = 0;
            column = 7;
            corner = true;
         }
         else if (grid.getCell(7,0).isValidMove())
         {
            row = 7;
            column = 0;
            corner = true;
         }
         else if (grid.getCell(7,7).isValidMove())
         {
            row = 7;
            column = 7;
            corner = true;
         }
         else
         {
            do
            {
               row = Util.random(0,7);
               column = Util.random(0,7);
            }
            while (!grid.getCell(row,column).isValidMove());
         }
         
         if(!corner)
         {
            for (int r = 2; r <=5; r++)
            {
               if (grid.getCell(r,0).isValidMove())
               {
                  row = r;
                  column = 0;
               }
               else if (grid.getCell(r,7).isValidMove())
               {
                  row = r;
                  column = 7;
               }
            }
            
            for (int c = 2; c <=5; c++)
            {
               if (grid.getCell(0,c).isValidMove())
               {
                  row = 0;
                  column = c;
               }
               else if (grid.getCell(7,c).isValidMove())
               {
                  row = 7;
                  column = c;
               }
            }
         }
      }
      
      else if (level == 4)
      {
         if (grid.getCell(0,0).isValidMove())
         {
            row = 0;
            column = 0;
         }
         else if (grid.getCell(0,7).isValidMove())
         {
            row = 0;
            column = 7;
         }
         else if (grid.getCell(7,0).isValidMove())
         {
            row = 7;
            column = 0;
         }
         else if (grid.getCell(7,7).isValidMove())
         {
            row = 7;
            column = 7;
         }
         else
         {
            do
            {
              	row = Util.random(0,7);
              	column = Util.random(0,7);
            }
            while (!grid.getCell(row,column).isValidMove());
            
            int blackP = 0;
            grid.countBlackAndWhite();
            int thingy = grid.getNumBlack();
   
            for (int r = 7; r >= 0; r--)
            {
               for (int c = 0; c <= 7; c++)
               {
                  if (grid.getCell(r,c).isValidMove())
                  {
                     Grid grid2 = new Grid(grid);
                     grid2.flipPieces(blackTurn,r,c);
   		            grid2.getCell(r,c).placeBlack();
                     grid2.countBlackAndWhite();
                     int blackP2 = grid2.getNumBlack() - thingy;
                     if (blackP2 > blackP)
                     {
                        blackP = blackP2;
                        row = r;
                        column = c;
                     }
                  }
               }
            }
         }
      }
      
		grid.flipPieces(blackTurn,row,column);
		grid.getCell(row,column).placeBlack();
		grid.makeAllMovesInvalid();
		blackTurn = false;
		repaint();
	}
}
