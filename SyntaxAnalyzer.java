import java.io.IOException;

public class SyntaxAnalyzer extends LexicalAnalyzer{

   //<program> --> `#`<stmt_list>`@`
    public static void program() throws IOException{
        if(nextToken == BEGIN){
            lex();
        }
        stmt_list();
        if(nextToken == END){
            lex();
        }
    }

    //<stmt_list> --> { <stmt>`;`}
    public static void stmt_list() throws IOException{
        stmt();
        if(nextToken == END_OF_STATEMENT){
            lex();
            stmt();
        }
    }

    //<stmt> --> <if_stmt> | <while_loop> | <assignment> | <block> | <declare>
    public static void stmt() throws IOException{

    
       if(nextToken == IF){
            lex();
            if_stmt();
       }
       else if(nextToken == WHILE){
            lex();
            while_loop();
       }
       else if(nextToken == IDENT){
            lex();
            assignment();
       }
       else if( nextToken == LEFT_CURLY){
            lex();
            block();
       }
       else if(nextToken == VAR){
            lex();
            declare();
       }
       else{
            error();
       }
    }

    //<if_stmt> --> `$` `(`<bool_expr> `)` <stmt> [`&` <stmt>]
    public static void if_stmt() throws IOException{
        if(nextToken != IF){
            error(); }
        else{
            lex();
            if(nextToken != LEFT_PAREN){
                error();
            }
            else{
                lex();
                bool_expr();
                if(nextToken != RIGHT_PAREN){
                    error();
                }
                else{
                    lex();
                    stmt();
                    if(nextToken == ELSE){
                        lex();
                        stmt();
                    }
                }
            }

        }
    }

    //<while_loop> --> `ROF` `(` <bool_expr> `)` <stmt>
    public static void while_loop() throws IOException{
        if(nextToken != WHILE){
            error();
        }
        else{
            lex();
            if(nextToken != LEFT_PAREN){
                error();
            }
            else{
                lex();
                bool_expr();
                if(nextToken != RIGHT_PAREN){
                    error();
                }
                else{
                    lex();
                    stmt();
                }
            }
        }
    }

    //<assignment> --> `id` `=` <expr> `;`
    public static void assignment() throws IOException{
        if(nextToken!= IDENT){
            error();
        }
        else{
            lex();
            if(nextToken!= ASSIGN_OP){
                error();
            }
            else{
                lex();
                expr();
                if(nextToken!= END_OF_STATEMENT){
                    error();
                }
                else{
                    lex();
                }
            }
        }
    }

    //<block> --> `{`<stmt_list>`}`
    public static void block() throws IOException{
        if(nextToken != LEFT_CURLY){
            error();
        }
        else{
            lex();
            stmt();
            if(nextToken != RIGHT_CURLY){
                error();
            }
            else{
                lex();
            }
        }
    }

   //<declare> --> `varie` `id` 
    public static void declare() throws IOException{
        if(nextToken!= VAR){
            error();
        }
        else{
            lex();
            if(nextToken != IDENT){
                error();
            }
            else{
                lex();
            }
        }
    }

    //<expr> --> <term> {(`*`|`/`|`%`)} <term>
    public static void expr() throws IOException{
        term();
        while(nextToken == MULT_OP || nextToken == DIV_OP || nextToken == MODULUS){
            lex();
            term();
        }
    }

    //<term> -->  <factor> {(`+`|`-`)} <factor>
    public static void term() throws IOException{
        factor();
        while(nextToken == ADD_OP || nextToken == SUB_OP){
            lex();
            factor();
        }
    }

    //<factor> --> `id`| `int_lit`| `(`<expr>`)`
    public static void factor() throws IOException{
        if(nextToken == IDENT || nextToken == INT || nextToken == LONG || nextToken == SHORT || nextToken == BYTE){
            lex();
        }
        else{
            if(nextToken == LEFT_PAREN){
                lex();
                expr();
                if(nextToken == RIGHT_PAREN){
                    lex();
                }
                else{
                    error();
                }
            }
            else{
                error();
            }
        }
    }
    
    //<bool_expr> --> <rel> {(`!=`|`==`)} <rel>
    public static void bool_expr() throws IOException{
        rel();
        while(nextToken == NOT_EQUAL_TO || nextToken == EQUAL_TO){
            lex();
            rel();
        }
    }
    
    //<rel> --> <bex> {(`<`|`>`|`~`|`^`)} <bex>
    public static void rel() throws IOException{
        bex();
        while(nextToken == GREATER_THAN || nextToken == LESS_THAN || nextToken == GREATER_THAN_EQUALTO || nextToken == LESS_THAN_EQUALTO){
            lex();
            bex();
        }
    }
    
    //<bex> --> <bterm> {(`*`|`/`|`%`)} <bterm>
    public static void bex() throws IOException{
        bterm();
        while(nextToken == MULT_OP || nextToken == DIV_OP || nextToken == MODULUS){
            lex();
            bterm();
        }

    }

    //<bterm> --> <bfactor> {(`+`|`-`)} <bfactor>
    public static void bterm() throws IOException{
        bfactor();
        while(nextChar == ADD_OP || nextToken == SUB_OP){
            lex();
            bfactor();
        }
    }

    //<bfactor> --> `id`|`int_lit`|`bool_lit`|`(`<bex>`)`
    public static void bfactor() throws IOException{
        if(nextToken == IDENT || nextToken == INT || nextToken ==LONG || nextToken == SHORT || nextToken == BYTE || nextToken == BOOL_LIT){
            lex();
        }
        else{
            if(nextToken == LEFT_PAREN){
                lex();
                bex();
                if(nextToken == RIGHT_PAREN){
                    lex();
                }
                else{
                    error();
                }
            }
            else{
                error();
            }

        }
    }
   
   //Error message for syntax error
    public static void error(){
        System.out.print("Exit- A syntaxical error has been found in your file!");
    }

    //Main driver method
    public static void main(String[] args) throws IOException{
        
        if(nextToken == BEGIN){
            lex();
            stmt_list();
            if(nextToken == END){
                System.out.print("No errors found");
            }
            else{
                error();
            }
        }
        else{
            error();
        }
    }
 }
 
