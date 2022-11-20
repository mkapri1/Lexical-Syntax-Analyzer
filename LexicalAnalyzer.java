
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class LexicalAnalyzer {
    
    /*
     * Global declarations
     * Variables
     */
    static int charClass; //charClass for keeping track of characters
    static char[] lexeme = new char[100]; //lexeme
    static char nextChar; //next character 
    static int lexLen; //length of lexeme
    static int token; 
    static int nextToken;
    static ArrayList<char[]> lexemes = new ArrayList<>();
    static ArrayList<char[]> tokens = new ArrayList<>();

    static FileReader fr = null; //file reader for reading the input from files
    

    /* 
    * Character classes 
    */
    final static int LETTER = 0;
    final static int DIGIT = 1;
    final static int UNKNOWN = 99;

    /* 
    * Token codes
    */
    final static int INT_LIT = 10;
    final static int INT_LONG = 11;
    final static int INT_SHORT = 12;
    final static int INT_WORD = 13;

    final static int IDENT=  14;

    final static int ASSIGN_OP = 15;
    final static int ADD_OP = 16;
    final static int SUB_OP=  17;
    final static int MULT_OP= 18;
    final static int DIV_OP = 19;
    final static int LEFT_PAREN =20;
    final static int RIGHT_PAREN= 21;
    final static int MODULUS = 22;

    final static int EOF = -1;
    final static int LESS_THAN = 23;
    final static int GREATER_THAN = 24;
    final static int LESS_THAN_EQUALTO = 25;
    final static int GREATER_THAN_EQUALTO = 26;
    final static int EQUAL_TO = 27;
    final static int NOT_EQUAL_TO = 28;
   
    final static int BEGIN = 29;
    final static int END = 30;
    final static int END_OF_STATEMENT = 31;
  
    
    final static int WHILE = 32;
    final static int IF = 33;
    final static int ELSE = 34;

    /*
     *  lookup:a function to look up operators and
     *  parentheses and return the token for it
     */
    public static int lookup(char ch){
        switch(ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            case '<':
                addChar();
                nextToken = LESS_THAN;
            case '>':
                addChar();
                nextToken = GREATER_THAN;
            case '^':
                addChar();
                nextToken = LESS_THAN_EQUALTO;
            case '~':
                addChar();
                nextToken = GREATER_THAN_EQUALTO;
            case '%':
                addChar();
                nextToken = MODULUS;
            case '=':
                addChar();;
                nextToken = ASSIGN_OP;
            case ';':
                addChar();;
                nextToken = END_OF_STATEMENT;
            case '#':
                addChar();
                nextToken = BEGIN;
            case '@':
                addChar();
                nextToken = END;
                
            default:
                System.out.println("Error, Unknown Symbol Found");
                System.exit(0);
        }
        return nextToken;


    }

    public static void addChar(){
        if(lexLen<=98){
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = '\0';
        }
        else{
            System.out.printf("Error-Lexeme is too long \n");
            System.exit(0);
        }

    }
    //checking digits
    public static void getChar() throws IOException{
        System.out.println();

        int i;
        if((i = fr.read()) != -1){
            if(Character.isLetter((char)i) || (char)i == '_'){
                charClass = LETTER;
            }
            else if(Character.isDigit((char)i)){
                charClass = DIGIT;
            }
            else{
                charClass = UNKNOWN;
            }
            nextChar = (char)i;
        }
        else{
            charClass = EOF;
        }



    }
    public static void getNonBlank() throws IOException{
        while(Character.isWhitespace(nextChar)){
            getChar();
        }
    }
    public static int getLexLength(char[] charArray){
        int length =0;
        for(char c : charArray){
            if( c == '\0'){
                break;
            }
        length ++;

        }
        return length;
    }

    

    public static int lex() throws IOException{
        lexLen = 0;
	 getNonBlank();
     int x;
	 switch (charClass) {
		/* Identifiers */
		 case LETTER:
			 addChar();
			 getChar();
			 while (charClass == LETTER) {
				 addChar();
				 getChar();
			 }
             int lexLength = getLexLength(lexeme);

             if(lexLength < 6){
                System.out.println("Error- Lexeme too short");
                System.exit(0);
             }
             else if (lexLength >8){
                System.out.println("Error-Lexeme too long");
                System.exit(0);
             }
			 nextToken = IDENT;
			 break;
		/* Integer literals */
		 case DIGIT:
			 addChar();
			 getChar();
			 while (charClass == DIGIT) {
				 addChar();
				 getChar();
             }
			 if(charClass == LETTER)
             { 
             if(nextChar == 'S'){
                nextToken = INT_SHORT; }
             else if(nextChar == 'B'){
                nextToken = INT_LIT;
             }
             else if(nextChar == 'L'){
                nextToken = INT_LONG;
             }
             else if(nextChar == 'W'){
                nextToken = INT_WORD;
             }
              addChar();
             getChar(); }
		 	break;
        
        
		/* Parentheses and operators */
		case UNKNOWN:
             
			 lookup(nextChar);
			 getChar();
			 break;
			
        /* EOF */
        case EOF:
			 nextToken = EOF;
			 lexeme[0] = 'E';
			 lexeme[1] = 'O';
			 lexeme[2] = 'F';
			 lexeme[3] = '\0';
		 	break;

    }
    System.out.printf("Next token is %d",nextToken);
    return nextToken;

}
    public static void main(String[] args) throws Exception{
        File file = new File("file.txt");
        fr = new FileReader(file);
        try (FileReader fr = new FileReader(file)) {
            if((fr.read()) == -1){
                System.out.printf("Error: Cannot open file");
            }
            else{
                getChar();
                do{
                    lex();
                }
                while(nextToken != EOF);
            }
            
        }

        }

        
    }



