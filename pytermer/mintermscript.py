#!/usr/bin/python
import sys
import math
import cgi
import cgitb
cgitb.enable()

VERI_AND = "&"
VERI_OR = "|"
VERI_NOT = "~"
SYMB_AND = "*"
SYMB_OR = "+"
SYMB_NOT = "!"
LB = "["
RB = "]"
IN = "In"
SEP = " "
LBr="("
RBr=")"
LRET="\n"
input_symbol=IN

def main():
	if (len(sys.argv)>2):
		input_symbol = str(sys.argv[2])
	else:
		input_symbol = IN


	output = "Output Vector:<br>" + str(outvector) + "<br><br>Minterms in Verilog Code:<br>" + convert_to_verilog(outvector)
	toHTML(output)


# Input: The number of outputs the boolean circuit has.
# Output: An integer representing 
# 	the number of bits needed to represent
# 	the input num in binary.
def bits_in_binary(num):
	if (num<1):
		return 0
	else: 
		return(int(math.ceil(math.log(num, 2))))

# Input: Number of vars in boolean expression.
# Output: A list of strings of binary from 0 to num.
def binary_count_list(num):
	bits = bits_in_binary(num)
	return [int_to_bin(x, bits) for x in range(num)]

# Input: Two decimal integers. Number to convert and bits
# needed to represent num in binary.
# Output: A list of integers.
# Converts decimal integer to binary list using bits
# as the number of digits required.
def int_to_bin(num, bits):
	if (bits <= 0):
		return []
	else:		
		l = int_to_bin((num / 2), (bits - 1))
		l.append(num % 2)
		return l

# Input: A list containing lists of binary bits.
# Output: A String. 
# Makes a verilog-syntax boolean expression for this minterm.
def convert_to_veriminterm(term_list):
	places=range(len(term_list))
	l=[convert_to_veriterm(term_list[place], (len(places) - place - 1)) for place in places]
	t=[]
	for i in range(len(l)-1):
		t.append(l[i]) 
		t.append(SEP + VERI_AND + SEP)
	t.append(l[-1])
	return ''.join(t)
		

# Input: A single bit.
# Output: A string. The verilog form of this input.
def convert_to_veriterm(bit, place):
	if (bit==0): nope = 1
	else: nope = 0
	return VERI_NOT * nope + input_symbol + LB + str(place) + RB

def find_minterms(input_list, terms_list):
	return [terms_list[i] for i in range(0,len(input_list)) if input_list[i]]


# Input: A list of truth values for the output of the boolean eq.
# Output: Verilog code representing the minterms for the boolean eq.
def convert_to_verilog(output_list):
	terms_list = binary_count_list(len(output_list))
	#print output_list
	#print terms_list

	minterms_list = find_minterms(output_list, terms_list)
	#print minterms_list
	terms_out = []
	for i in range(len(minterms_list)-1):
		terms_out.append(LBr)
		terms_out.append(convert_to_veriminterm(minterms_list[i]))
		terms_out.append(RBr)
		terms_out.append(SEP + VERI_OR + LRET) 
	terms_out.append(LBr)
	terms_out.append(convert_to_veriminterm(minterms_list[-1]))
	terms_out.append(RBr)

	return(''.join(terms_out))


print "Content-type: text/html"
print "<HTML>"
print "<h1>Verilog Code</h1>"

form = cgi.FieldStorage()
if "invector" not in form:
	print "<P><b>"
	print "Please enter an output vector of the form:<BR>"
	print "10001100011..."
	print "</b></P>"
	print "</HTML>"
	sys.exit(1)

outputlist=list( form.getvalue("invector","") )
outvector= [int(x) for x in outputlist]
output = "Output Vector:<br>" + str(outvector) + "<br><br>Minterms in Verilog Code:<br>" + convert_to_verilog(outvector)


print "<P>"	
print  output
print "</P>"
print "</HTML>"
	

