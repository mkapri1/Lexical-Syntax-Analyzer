# Lexical and Syntax Analyzer 



## A)Rules for Recognizing all lexemes as their proper tokens:

Tokens are a group of characters formingbasic, atomic chunk of syntax a "word"

Lexeme: group of characters that form a token
Token: class of lexemes that match a pattern

In this language, the type of tokens can be divided into few classes: keywords, identifiers, operators and punctuation, and delimiters.

|Operators | Token Codes |
| ---------|-------------|
| Addition |  16
| Subtraction| 17        |
| Multiplication| 18      |
| Division | 19 |
| Less than | 23 |
| Greater than | 24 |
| Less than equal to | 25 |
| Greater than equal to | 26 |
| Equal | 27 |
| Not Equal to |28 |
| Assignment | 15 |
| Modulus | 22 |

|Keywords| Token Codes|
|--------|------------|
|varie| 45
|ROF| 32|
|$| 33|
|&| 34|
|#| 29|
|$| 30|

|Others|Token Codes|
|------|-----------|
|Identifiers| 11|
|EOF| -1|
|Left Paren| 20|
|Right Paren| 21|
|End of Statement (;)| 31|

**Regular Expressions:**

|Tokens| Regex|
|------|------|
|Addition| + |
|Subraction| -|
|Multiplication| *|
|Division| /|
|Modulus| %|
|Left Paren| ( |
|Right Paren| )|
|WHILE| ROF|
|IF| $|
|ELSE| &|
|BEGIN| #|
|END| @|
|End of Statement| ;|
|Greater than| >|
|Less than| < |
|Greater than equal to| ~|
|Less than equal to| ^ |
|Equal| ==|
|Not Equal| != |
|Assignment| = |

**Variable Names**

`[_a-zA-Z][_a-zA-Z][_a-zA-Z][_a-zA-Z][_a-zA-Z][_a-zA-Z][_a-zA-Z]?[_a-zA-Z]?`

**Different integer sizes**

| Size | Regex |
|------|-------|
|1 BYTE| [0-9]+B|
|2 BYTES| [0-9]+S|  
|4 BYTES| [0-9]+I|
|8 BYTES| [0-9]+L|


## B)EBNF Rules for my language:

````
<program> --> `#`<stmt_list>`@`
<stmt_list> --> { <stmt>`;`}
<stmt> --> <if_stmt> | <while_loop> | <assignment> | <block> |  <declare> 
<if_stmt> --> `$` `(`<bool_expr> `)` <stmt> [`&` <stmt>]
<while_loop> --> `ROF` `(` <bool_expr> `)` <stmt>
<assignment> --> `id` `=` <expr> `;`
<block> --> `{`<stmt>`}`
<declare> --> `varie` `id` `;`
<expr> --> <term> {(`*`|`/`|`%`)} <term>
<term> -->  <factor> {(`+`|`-`)} <factor>
<factor> --> `id`| `int_lit`| `(`<expr>`)`

<bool_expr> --> <rel> {(`!=`|`==`)} <rel>
<rel> --> <bex> {(`<`|`>`|`~`|`^`)} <bex>
<bex> --> <bterm> {(`*`|`/`|`%`)} <bterm>
<bterm> --> <bfactor> {(`+`|`-`)} <bfactor>
<bfactor> --> `id`|`int_lit`|`bool_lit`|`(`<bex>`)`

````


## C)LL Grammar Test

In order to conform with the norms of LL Grammar, it should pass the pairwise disjoint test and no LHR.

The grammar for my language is pairwise disjoint. It checks for every possible terminal symbol for each rule a nonterminal has and makes sure they are all unique. Also, every first token in my grammar is unique which passes the pairwise disjoint test.

Pairwise Disjoint Test
````
        FIRST(<program>) -> { # }
        FIRST(<stmt_list>) -> { ROF, $, {, id, varie }	
        FIRST(<stmt>) -> { ROF, $, {, id, varie }	
        FIRST(<declare>) -> { varie }
        FIRST(<assign>) -> { id }
        FIRST(<while>) -> { ROF }
        FIRST(<if_stmt>) -> { $ }
        FIRST(<block>) -> { { }
        FIRST(<expr>) -> { id, int_lit, (expr)}
        FIRST(<term>) -> { id, int_lit, (expr)}
        FIRST(factor) -> { id, int_lit, (expr)}
        FIRST(bool_expr) -> (id, int_lit, bool_lit, (bex))
        FIRST(rel) -> {id,int_lit,bool_lit,(bex)}
        FIRST(bex) -> {id,int_lit,bool_lit,(bex)}
        FIRST(bterm) -> {id,int_lit,bool_lit,(bex)}
        FIRST(bfactor) -> {id,int_lit,bool_lit,(bex)} 

````

My grammar does not have any rules that cause left hand recursion, in which a nonterminal calls itself as the first character. No state here is calling itself, which proves there is no left hand recursion. In my grammar, there does not exist a non terminal that has multiple rules for one terminal. 



## D)Ambiguity Check 

Generally, ambiguous grammar has multiple places where they are generating the same non-terminal in different positions. My grammar does not have any such non terminals which are in different positions. My grammar also passes the LR Parser Test. A grammar is LR(1) if the following two conditions are satisfied for each configurating set: 1. For any item in the set [A –> u•xv, a] with x a terminal, there is no item in the set of the form [B –> v•, x]. In the action table, this translates no shiftreduce conflict for any state. Also, there is no right hand or left hand recursion in this grammar.

## Test Files

### Test1.txt (No Lexical Errors)

````
# 
    varie xOne;
    xTwo = 3S;
    varie xTwo;
    xTwo = 4L;
    ROF(xTwo > xOne){
        xOne = xOne + One;
        xTwo = xTwo + xOne;
    }

    varie xThree;
    xThree = (xOne + xTwo);

@
````
### Test2.txt (Lexical Errors)

````
##
    varie var188;
    var188 = 5 + ( 2 * 8);

    varie varOne;
    varOne = (4 * 8) + 1;

    $(varOne ~^ 1){
        varOne = 20;
    } 
    &&{
        varOne = 30;
    }

    varie varTwo;
    varTwo = varOn + 19;

@
````
1) var188 is not the correct declaration of the variable as it contains digits.
2) ~^ should have been just ~ 
3) && is wrong, as else is jusr &
4) varOn should have been varOne
5) Only one # should have been there in the start 

### Test3.txt (No Syntax Errors)
````
#
    varie banana;
    banana =0;

    $(banana < 0){
        banana = banana *10;
    } & {
        banana = banana *7/6 + 100;
    }

    varie basket;
    basket = banana * 2;
@
````
### Test4.txt (Syntaxical Errors)
````
#
    varie apples;
    apple = 1 * 2 -3

    $(apples >> 4 {
        apples = 0;
    } &{
        apples = apples + 10;

    varie bananas
    bananas = (1+1 * 2;
@
````
1) There is no semicolon after 3, which is needed to end an statement
2) It should have been just > between apples and 4
3) A closing parenthesis ) is missing after 4
4) A closing } is missing after else statement
5) No semicolon after bananas,  which is needed to end an statement
6) Closing parenthesis ) is missing after 2


## **LR Parse Table**:

