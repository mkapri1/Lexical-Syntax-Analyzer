
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
    final static int INT = 10;
    final static int SHORT = 11;
    final static int BYTE = 12;
    final static int LONG = 13;

    final static int IDENT=  14;

    final static int ASSIGN_OP = 15;
    final static int ADD_OP = 16;
    final static int SUB_OP=  17;
    final static int MULT_OP= 18;
    final static int DIV_OP = 19;
    final static int LEFT_PAREN =20;
    final static int RIGHT_PAREN= 21;
    final static int MODULUS = 22;


    final static int LESS_THAN = 23;
    final static int GREATER_THAN = 24;
    final static int LESS_THAN_EQUALTO = 25;
    final static int GREATER_THAN_EQUALTO = 26;
    final static int EQUAL_TO = 27;
    final static int NOT_EQUAL_TO = 28;
   
    final static int BEGIN = 29;
    final static int END = 30;
    final static int END_OF_STATEMENT = 31;
    final static int EOF = -1;
  
    
    final static int WHILE = 32;
    final static int IF = 33;
    final static int ELSE = 34;

    final static int LEFT_CURLY = 40;
    final static int RIGHT_CURLY = 41;

    final static int BOOL_LIT = 43;
    final static int UNDERSCORE = 44;
    final static int VAR = 45;

    /*
     *  lookup: A function to look up operators and
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
                if(nextChar == '!'){
                    nextToken = NOT_EQUAL_TO;
                }
                if(nextChar == '='){
                    nextToken = EQUAL_TO;
                }
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
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
    }

    //Method to add characters of a given lexeme
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
   
    //Checking digits and letters to determine char class
    public static void getChar() throws IOException{
        System.out.println();
        int i;
        if((i = fr.read()) != -1){
            if(Character.isLetter((char)i) || (char)i == '_' || (char)i == '#' || (char)i == '$' || (char)i == '&' || (char)i == '@'{
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

    //To remove white spaces
    public static void getNonBlank() throws IOException{
        while(Character.isWhitespace(nextChar)){
            getChar();
        }
    }
    
    //This method returns the length of a lexeme
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

    //Method to determine the type of token
    public static int lex() throws IOException{
        lexLen = getLexLength(lexeme);
        String lexemeString = new String(lexeme);
        getNonBlank();
        switch (charClass) {
            
            /* Identifiers */
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER || charClass == UNDERSCORE && lexLen >=6 || lexLen <=8 ) {
                    addChar();
                    getChar();
                }

                if(lexemeString.equals("ROF")){
                    nextToken = WHILE;
                }
                else if(lexemeString.equals("$")){
                    nextToken = IF;
                }
                else if(lexemeString.equals("&")){
                    nextToken = ELSE;
                }
                else if(lexemeString.equals("#")){
                    nextToken = BEGIN;
                }
                else if(lexemeString.equals("@")){
                    nextToken = END;
                }
                else if(lexemeString.equals("varie")){
                    nextChar = VAR;
                }
                else if(lexLen >= 6 && lexLen <=8){
                    if(lexLen < 6){
                        System.out.println("Error- Lexeme too short");
                        System.exit(0);
                    }
                    else if (lexLen >8){
                        System.out.println("Error-Lexeme too long");
                        System.exit(0);
                    }
                    nextToken = IDENT;
                }
                else{
                    nextToken = UNKNOWN;
                }
                break;

            /* Integer literals */
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT || charClass == LETTER) {
                    addChar();
                    getChar();
                }
                if(lexeme[(lexeme.length) -1] == 'I'){
                    nextToken = INT;
                }
                else if(lexeme[(lexeme.length) -1] == 'S'){
                    nextToken = SHORT;
                }
                else if(lexeme[(lexeme.length) -1] == 'B'){
                    nextToken = BYTE;
                }
                else if(lexeme[(lexeme.length) -1] == 'L'){
                    nextToken = LONG;
                }
                else if(charClass == UNKNOWN){
                    nextToken = UNKNOWN;
                }
                break;
        
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
        
        if(nextToken == UNKNOWN || nextToken == EOF){
            System.out.println("Unknown symbol entered");
        }
        else{
            System.out.printf("Next token is %d, Next Lexeme is %s/n",nextToken,lexeme);
        }
        return nextToken;
    }
   

    //main driver method
    public static void main(String[] args) throws Exception{
        //Input the file you want to run
        File file = new File("test1.txt");
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



