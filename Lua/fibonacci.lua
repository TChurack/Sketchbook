-- A short program to print a specified number (passed in as a command line argument)
-- of values in the Fibonacci sequence.

numVals = arg[1]

val1 = 0
val2 = 1

for i=1, numVals do
	io.write(val1, " ")
	temp = val1
	val1 = val2
	val2 = temp + val2
end

io.write("\n")
