from mintermer import *

# Minterm tester

def main():
	print "Testing Functions:"
	print "-" * 64
	print "-" * 64
	
	print "bits_in_binary:"
	test_bits_in_binary()
	print "-" * 64
	print "int_to_bin:"
	test_int_to_bin()
	print "-" * 64
	print "binary_count_list:"
	test_binary_count_list()
	print "-" * 64
	print "find_minterms:"
	test_find_minterms()
	print "-" * 64
	print "convert_to_veriterm:"
	test_convert_to_veriterm()
	print "-" * 64
	print "convert_to_veriminterm:"
	test_convert_to_veriminterm()	
	print "-" * 64
	print "convert_to_verilog:"
	test_convert_to_verilog()


# Number of bits needed to represent x outputs.
def test_bits_in_binary():
	print bits_in_binary(2)
	print bits_in_binary(3)
	print bits_in_binary(7)
	print bits_in_binary(18)

def test_binary_count_list():
	print "Arg 1:"
	print binary_count_list(1)
	print "Arg 2:"
	print binary_count_list(2)
	print "Arg 3:"
	print binary_count_list(3)
	print "Arg 7:"
	print binary_count_list(7)
	print "Arg 18:"
	print binary_count_list(18)

def test_int_to_bin():
	print int_to_bin(0, 2)
	print int_to_bin(7, 4)
	print int_to_bin(18, 8)

def test_convert_to_veriterm():
	print convert_to_veriterm(0, 0)
	print convert_to_veriterm(1, 1)
	print convert_to_veriterm(0, 2, "f")
	print convert_to_veriterm(1, 3, "f")

def test_find_minterms():
	print find_minterms([0,1],[[0],[1]])
	print find_minterms([0,1,1,0],[[0,0],[0,1],[1,0],[1,1]])
	print find_minterms([0,1,0,1,0,0,1],binary_count_list(7))

def test_convert_to_veriminterm():
	print convert_to_veriminterm([1,0])
	print convert_to_veriminterm([1,0,1,1])
	print convert_to_veriminterm([1,0,0,1,1,0])

def test_convert_to_verilog():
	print convert_to_verilog([1,0])
	print 
	print convert_to_verilog([1,1,0])
	print
	print convert_to_verilog([1,0,0,1])
	print
	print convert_to_verilog([1, 0, 0, 1, 1, 1, 0, 0, 1])
	
	

if __name__=="__main__":
	main()
	
