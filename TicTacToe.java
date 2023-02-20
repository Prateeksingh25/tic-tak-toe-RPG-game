package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
	
	static ArrayList<Integer> playerPositions = new ArrayList<Integer>();
	static ArrayList<Integer> aiPositions = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		ticTacToe();
	}
	
	public static void ticTacToe() {
		
		char[][] gameBoard = {{' ','|',' ','|',' '}, 
				{'-','+','-','+','-'}, 
				{' ','|',' ','|',' '}, 
				{'-','+','-','+','-'}, 
				{' ','|',' ','|',' '}};	
		
		System.out.println("Play against CPU or Human? \n(0 for CPU and 1 for Human)");
		Scanner scanOpp = new Scanner(System.in);
		
		oppTypeCheck:{
			try{
				int opponent = scanOpp.nextInt();

				printGameBoard(gameBoard);

				boolean currentPlayer = Math.random() <= 0.5;	

				while(true) {
					if(currentPlayer) {
						
						Scanner scan = new Scanner(System.in);	//here, scanner created
						System.out.println("Player 'X' enter your position (1-9): ");

						typeCheck1:
							
							try {
								int playerPos = scan.nextInt();		
								while(playerPos>9 || playerPos<1) {
									System.out.println("Invalid position! Enter valid position: ");
									playerPos = scan.nextInt();
								}
								while(playerPositions.contains(playerPos) || aiPositions.contains(playerPos)) {
									System.out.println("Position taken! Enter valid position: ");
									playerPos = scan.nextInt();
								}

								placePiece(gameBoard, playerPos, "player");
								playerPositions.add(playerPos);
								printGameBoard(gameBoard);
								checkWinner();
								currentPlayer = !currentPlayer;
							}
							catch(InputMismatchException err) {
								
								System.out.println("You've entered a bad input, please try again!");
								break typeCheck1;
							}
					}
					else{				
						if(opponent == 0) {
							System.out.println("CPU's turn!");
							Random rand = new Random();
							int aiPos = rand.nextInt(9) + 1;
							while(playerPositions.contains(aiPos) || aiPositions.contains(aiPos))
								aiPos = rand.nextInt(9) + 1;	
							placePiece(gameBoard, aiPos, "ai");		
							aiPositions.add(aiPos);
							printGameBoard(gameBoard);
							checkWinner();
							currentPlayer = !currentPlayer;
						}				
						else {	
							
							Scanner scan = new Scanner(System.in);	
							System.out.println("Player 'O', enter your position (1-9): ");

							typeCheck2: 
								try {
									int aiPos = scan.nextInt();		
									while(aiPos>9 || aiPos<1) {
										System.out.println("Invalid position! Enter valid position: ");
										aiPos = scan.nextInt();
									}
									while(playerPositions.contains(aiPos) || aiPositions.contains(aiPos)) {
										System.out.println("Position taken! Enter valid position: ");
										aiPos = scan.nextInt();
									}

									placePiece(gameBoard, aiPos, "ai");
									aiPositions.add(aiPos);
									printGameBoard(gameBoard);
									checkWinner();
									currentPlayer = !currentPlayer;
								}
								catch(InputMismatchException err) {
									System.out.println("You've entered a bad input, please try again!");
									break typeCheck2;
								}
						}
					}
				}
			}
			catch(InputMismatchException err) {
				System.out.println("You've entered a bad input, please try again!");
				break oppTypeCheck;
			}
		}
	}
	
	
	public static void printGameBoard(char[][] gameBoard) {	
		for(char[] row : gameBoard) {		
			System.out.print("\t");
			for(char col : row) {		
				System.out.print(col);
				
			}
			System.out.println();
			
		}
		
		System.out.println("\n--------------------");
		
	}
	
	
	public static void placePiece(char[][] gameBoard, int pos, String user) {
		char turn = ' ';
		if(user.equals("player"))
			turn = 'X';
		else if(user.equals("ai"))
			turn = 'O';
		
		switch(pos) {
			case 1: 
					gameBoard[0][0] = turn;
				break;
			case 2: 
					gameBoard[0][2] = turn;
				break;
			case 3:  
					gameBoard[0][4] = turn;
				break;
			case 4:  
					gameBoard[2][0] = turn;
				break;
			case 5:  
					gameBoard[2][2] = turn;
				break;
			case 6:  
					gameBoard[2][4] = turn;
				break;
			case 7:   
					gameBoard[4][0] = turn;
				break;
			case 8:   
					gameBoard[4][2] = turn;
				break;
			case 9:  
					gameBoard[4][4] = turn;
				break;
		}
	}
	
	
	public static void checkWinner() {		
		
		List<Integer> topRow = Arrays.asList(1,2,3);
		List<Integer> midRow = Arrays.asList(4,5,6);
		List<Integer> botRow = Arrays.asList(7,8,9);
		List<Integer> leftCol = Arrays.asList(1,4,7);
		List<Integer> midCol = Arrays.asList(2,5,8);
		List<Integer> rightCol = Arrays.asList(3,6,9);
		List<Integer> cross1 = Arrays.asList(1,5,9);
		List<Integer> cross2 = Arrays.asList(7,5,3);
		
		
		List<List<Integer>> winConditions = Arrays.asList(topRow, midRow, botRow, leftCol, midCol, rightCol, cross1, cross2);
		
		for(List<Integer> list : winConditions) {
			if(playerPositions.containsAll(list)) {
				System.out.println("Congratulations, you win!!");
				playAgain();				
			}
			else if(aiPositions.containsAll(list)) {
				System.out.println("CPU wins, better luck next time :(");
				playAgain();
			}
		}
		
		if(playerPositions.size() + aiPositions.size() == 9) {
			System.out.println("It's a TIE!");
			playAgain();
		}
		
	}
	

	public static void playAgain() {
		System.out.println("Play again? (y/n)");
		Scanner ans = new Scanner(System.in);
		String answer = ans.next();
		if(answer.contains("y") || answer.contains("Y")) {
			aiPositions.clear();
			playerPositions.clear();
			ticTacToe();
		}
		else {
			System.exit(0);
		}
	}
	
}

