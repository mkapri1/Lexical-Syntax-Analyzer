# Lexical and Syntax Analyzer 



## Rules for Recognizing all lexemes as their proper tokens:

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


## Production Rules for my language:

````
<program> --> `#`<stmt_list>`@`
<stmt_list> --> { <stmt>`;`}
<stmt> --> <if_stmt> | <while_loop> | <assignment> | <block> |  <declare> 
<if_stmt> --> `$` `(`<bool_expr> `)` <stmt> [`&` <stmt>]
<while_loop> --> `ROF` `(` <bool_expr> `)` <stmt>
<assignment> --> `id` `=` <expr> `;`
<block> --> `{`<stmt_list>`}`
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


## LL Grammar Test

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
        FIRST(<expr>) -> { id, int_lit, (}
        FIRST(<term>) -> { id, int_lit, (}
        FIRST(factor) -> { id, int_lit, (}
        FIRST(bool_expr) -> (id, int_lit, bool_lit, ()
        FIRST(rel) -> {id,int_lit,bool_lit,(}
        FIRST(bex) -> {id,int_lit,bool_lit,(}
        FIRST(bterm) -> {id,int_lit,bool_lit,(}
        FIRST(bfactor) -> {id,int_lit,bool_lit,(} 

````

My grammar does not have any rules that cause left hand recursion, in which a nonterminal calls itself as the first character. No state here is calling itself, which proves there is no left hand recursion. In my grammar, there does not exist a non terminal that has multiple rules for one terminal. 



## Ambiguity Check 

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
3) && is wrong, as else is just &
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

### **Rule Base**:
![Untitled 5](https://user-images.githubusercontent.com/79378418/205786934-c7deeb20-811b-47e3-a987-d92effb7362c.jpg)

### **LR Table**:
![11](https://user-images.githubusercontent.com/79378418/205791641-8ccc3f2a-c698-4f49-9565-cc544af628f2.jpg)

![222](https://user-images.githubusercontent.com/79378418/205791652-4b0afc2d-a6a7-414c-b9e0-6778c195f482.jpg)

![333](https://user-images.githubusercontent.com/79378418/205791662-2ab5623a-4f67-43a8-aba0-95aca15acbb3.jpg)

### **Code Samples/Input**:

**Pass**:

![Input1](https://user-images.githubusercontent.com/79378418/205790140-d96f57c3-afd9-4f8b-b722-a3894b44cd89.jpg)

![INPUT33](https://user-images.githubusercontent.com/79378418/205881303-508d075f-0184-46d0-b510-d4a35deddd9e.jpg)

![INPUT44](https://user-images.githubusercontent.com/79378418/205881338-95f1124a-3182-4801-8b0c-51f66e6c3384.jpg)

**Fail**:

![INPUT55](https://user-images.githubusercontent.com/79378418/205881355-652bdbf0-7b77-4c58-8045-6d92f0061281.jpg)

![Input2](https://user-images.githubusercontent.com/79378418/205790151-55535403-5d0b-4524-b3cb-6924e72c9af9.jpg)
