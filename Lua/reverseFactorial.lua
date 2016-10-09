-- Takes a number as an argument and returns the factorial that equals the
-- number if it exists, or NONE if it does not.

num = tonumber(arg[1])

divisor = 1

while num > 1 do
	num = num / divisor
	divisor = divisor + 1
end

if num == 1 then
	io.write(divisor-1, "!", "\n")
else
	io.write("NONE", "\n")
end
