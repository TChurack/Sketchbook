/**
 * Prints a left-leaning depiction of Pascal's Triangle to 
 * the console, up to a specified number of rows.
 */
object PascalsTriangle {
    
    def fac(in:Long):Long = { (1L to in).foldLeft(1L)(_*_) }
    
    def p(row:Int, col:Int):Long = {
        fac(row)/(fac(col)*fac(row-col))
    }

    def main(args: Array[String]) {
		println("Left-Leaning Pascal's Triangle!")
		println()
		println("Input the number of rows to print and press enter:...")
        val k = scala.io.StdIn.readInt()
		println()
		if (k < 21) {
			for (n <- 0 to k-1) {
				for (r <- 0 to n) {
					print(p(n, r) + " ")
				}
				println()
			}
		} else {
			println("Input value too large! Would cause Long overflow!")
			println("Try a smaller value next time")
		}
    }
}