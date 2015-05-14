# Takes two command line inputs of the form [1,2,3] or 1,2,3
# and creates a new array by alternately taking an element from each argument.
# It then returns this as a string.
# Note: the output string represents an array of one character strings regardless of the inputs being ints, string, chars etc.

list1 = ARGV[0].delete('][').split(",")
list2 = ARGV[1].delete('][').split(",")
list3 = []

max_length = [list1.length, list2.length].max
i = 0

while i < max_length do
	if i < list1.length
		list3 << list1[i]
	end
	if i < list2.length
		list3 << list2[i]
	end
	i += 1
end

puts list3.to_s
