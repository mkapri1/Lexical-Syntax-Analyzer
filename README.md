# Lexical and Syntax Analyzer 



**Rules for Recognizing all lexemes as their proper tokens**

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
|int| 10
|ROF| 32|
|$| 33|
|&| 34|
|Start| 29|
|Finish| 30|

|Others|Token Codes|
|------|-----------|
|Identifiers| 11|
|EOF| -1|
|Left Paren| 20|
|Right Paren| 21|
|End of Statement (;)| 31|

B) **EBNF Rules for my language**:

````
<program> --> `#`<stmt_list>`@`
<stmt_list> --> <stmt> `;` {<stmt>`;`}
<stmt> --> <if_stmt> 
<stmt> --><while_loop> 
<stmt> --> <assignment>
<stmt> --> <block>
<stmt> --> <declare> 
<int_dec> --> `I`|`S`|`B`|`L`
<declare> --> <int_dec> `id` 
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

````


C) **LL Grammar Test**

In order to conform with the norms of LL Grammar, it should pass the pairwise disjoint test and no LHR.

The grammar for my language is pairwise disjoint. It checks for every possible terminal symbol for each rule a
nonterminal has and makes sure they are all unique.Also, every first token in our grammar is unique which passes the pairwise disjoint test.

My grammar does not have any rules that cause left hand recursion, in which a nonterminal calls itself as the first character. No state here is calling itself, which proves there is no left hand recursion. In my grammar, there does not exist a non terminal that has multiple rules for one terminal. 

D) **Ambiguity Check** 

Generally, ambiguous grammar has multiple places where they are generating the same non-terminal in different positions. My grammar does not have any such non terminals which are in different positions. My grammar also passes the LR Parser Test. A grammar is LR(1) if the following two conditions are satisfied for each configurating set: 1. For any item in the set [A –> u•xv, a] with x a terminal, there is no item in the set of the form [B –> v•, x]. In the action table, this translates no shiftreduce conflict for any state. Also, there is no right hand or left hand recursion in this grammar.

E) **Code in files** 

F) **Code in files**. 

G) **Test Files in repository**. 

H) **LR Parse Table**:
!![Untitled 4](https://user-images.githubusercontent.com/79378418/202880551-e21cd001-1605-4c57-b5fc-c071e2b2e4ec.jpg)
![Untitled 5](https://user-images.githubusercontent.com/79378418/202880562-55688789-f2b4-4cc8-8283-3b87a4f1194e.jpg)

![LR3](https://user-images.githubusercontent.com/79378418/202880534-bf40a4c6-698a-4399-bcad-acfd03703587.jpg)
