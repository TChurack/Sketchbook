nums = ARGV
sum = 0

def forSum(sum, nums)
	for num in nums
		sum += num.to_i
	end
	return sum
end

def whileSum(sum, nums)
	i = 0
	while i < nums.length do
		sum += nums[i].to_i
		i+=1
	end
	return sum
end

def recSum(nums)
	if nums.length == 1
		return nums[0].to_i
	else
		temp = nums.pop.to_i
		return temp + recSum(nums)
	end
end

puts "While Looped sum: " + whileSum(sum, nums).to_s
puts "For Looped sum: " + forSum(sum, nums).to_s
puts "Recursive sum: " + recSum(nums).to_s # Note: destructive, must be run last as it recursively empties num