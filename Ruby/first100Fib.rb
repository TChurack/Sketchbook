# Prints the first 100 Fibonacci numbers

nums = [0,1]

while nums.length < 100
	nums << nums[-1] + nums[-2]
end

for num in nums
	puts num
end
