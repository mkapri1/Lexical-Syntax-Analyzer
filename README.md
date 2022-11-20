Rules for Recognizing all lexemes as their proper tokens

Tokens are a group of characters formingbasic, atomic chunk of syntax a "word"

Lexeme: group of characters that form a token
Token: class of lexemes that match a pattern

In this language, the type of tokens can be divided into few classes: keywords, identifiers, operators and punctuation, and delimiters.



Keywords        Token Code           Uses

int  
long                  Integer Literal
ROF             11(WHILE)                  To allow for loops
$               12(IF)                Selection statements (if)
&               13(ELSE)                Selection statements (else)
#               14(BEGIN)
@               15(BEGIN)

Operators       Token Code          Uses
+               21(ADD_OP)          
-               22(SUB_OP)
*               23(MUL_OP)
/               24(DIV_OP)
%               25(MOD_OP)
<               26(LESS_THAN)
>               27(GREATER_THAN)
^               28(LESS_THAN_EQUAL)
~               29(GREATER_THAN_EQUAL)
==              30(EQUAL)
!=              31(NOT_EQUAL)
=               32(ASSIGN)

Punctutation    Token Code          Uses
;               33(END_STMT)        Way to separate multiple statements

Delimiters      Token Code          Uses
(               34(LEFT_PAREN)      Parenthesis
)               35(RIGHT_PAREN)     Parenthesis

                Token Code          Uses
Identifiers     11(IDENT)           Sequence of one or more letters of digits
EOF             -1(EOF)             End of file

Regular Expressions:

INT_LIT: int
ADD_OP: +
SUB_OP: -
WHILE: "ROF"
IF: "$"
ELSE: "&"
BEGIN: "#"
END: "@"
OPEN_PAREN: "("
CLOSE_PAREN: ")"

LETTER = a|b|c|...|z|A|B...|Z
DIGIT = 0|1|2..|9


Punctuation: ";"

Variable type: var
Variable names: [_a-zA-Z][_a-zA-Z][_a-zA-Z][_a-zA-Z][_a-zA-Z][_a-zA-Z][_a-zA-Z]?[_a-zA-Z]?

Different sizes:
1 BYTE: [0-9]+_B
2 BYTES: [0-9]+_S
4 BYTES: [0-9]+_INT
8 BYTES: [0-9]+_L


EBNF Rules for my language:

<program> --> `#`<stmt_list>`@`
<stmt_list> --> <stmt> `;` {<stmt>`;`}
<stmt> --> <if_stmt> 
<stmt> --><while_loop> 
<stmt> --> <assignment>
<stmt> --> <block>
<stmt> --> <declare> 
<int_dec> --> `I`|`S`|`B`|`L`
<declare> --> `id` <int_dec> 
<block> --> `{`<stmt>`}`
<if_stmt> --> `$` `(`<bool_expr> `)` <stmt> `&` <stmt>
<while_loop> --> `ROF` `(` <bool_expr> `)` <stmt>
<assignment> --> `id` `=` <expr> `;`
<expr> --> <term> {(`*`|`/`|`%`)} <term>
<term> -->  <factor> {(`+`|`-`)} <factor>
<factor> --> `id`| `int_lit`| `(`<expr>`)`

<bool_expr> --> <rel> {(`!=`|`==`)} <rel>
<rel> --> <bex> {(`<`|`>`|`~`|`^`)} <bex>
<bex> --> <bterm> {(`*`|`/`|`%`)} <bterm>
<bterm> --> <bfactor> {(`+`|`-`)} <bfactor>
<bfactor> --> `id`|`int_lit`|`bool_lit`|`(`<bex>`)`

C. LL Grammar Test
In order to conform with the norms of LL Grammar, it should pass the pairwise disjoint test and no LHR.

The grammar for my language is pairwise disjoint. It checks for every possible terminal symbol for each rule a
nonterminal has and makes sure they are all unique.Also, every first token in our grammar is unique which passes the pairwise disjoint test.

My grammar does not have any rules that cause left hand recursion, in which a nonterminal calls itself as the first character. No state here is calling itself, which proves there is no left hand recursion. In our grammar, there does not exist a non terminal that has multiple rules for one terminal. 

D. Ambiguity Check
Generally, ambiguous grammar has multiple places where they are generating the same non-terminal in different positions. Our grammar does not have any such non terminals which are in different positions. My grammar also passes the LR Parser Test. A grammar is LR(1) if the following two conditions are satisfied for each configurating set: 1. For any item in the set [A –> u•xv, a] with x a terminal, there is no item in the set of the form [B –> v•, x]. In the action table, this translates no shiftreduce conflict for any state. Also, there is no right hand or left hand recursion in this grammar.











