#-- --- .-. ... .  -.-. --- -.. .

from MorseCodeTree import Node

root = Node("", Node("E", Node("I", Node("S", Node("H", Node("5", None, None), Node("4", None, None)), Node("V", None, Node("3", None, None))), Node("U", Node("F", None, None), Node("", None, "2"))), Node("A", Node("R", Node("L", None, None), Node("", Node("+",None, None), None)), Node("W", Node("P", None, None), Node("J", None, Node("1", None, None))))), Node("T", Node("N", Node("D", Node("B" ,Node("6", None, None), Node("=", None, None)), Node("X", Node("/", None, None), None)), Node("K", Node("C", None, None), Node("Y", None, None))), Node("M", Node("G", Node("Z", Node("7", None, None), None), Node("Q", None, None)), Node("O", Node("", Node("8", None, None), None), Node("", Node("9", None, None), Node("0", None, None))))))
bad_char_flag = False
not_dot_dash_flag = False


print "Enter Code: "
code = raw_input()
ans = ""
words = code.split("  ")
for word in words:
    letters = word.split(" ")
    for letter in letters:
        current = root
        for c in letter:
            if current is None:
                bad_char_flag = True
                break
            elif c == ".": 
                current = current._left_child
            elif c == "-":
                current = current._right_child
            else:
                not_dot_dash_flag = True
        ans += "#" if current is None else current._data
    ans += " "        
print ans
if bad_char_flag:
    print "NB: '#' represents a combination of '.'s and/or '-'s that don't represent a valid Morse Code character."
if not_dot_dash_flag:
    print "Invalid Morse Code characters were ignored."