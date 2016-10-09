/* Takes a number in as an argument and prints to the command line
 * a factorial that is equal to that number - if such a number
 * exists, else it prints NONE.
 */	
object ReverseFactorial {
	def revfac(num:Float, pow:Int) {
		num match {
			case 1.0 => println(pow + "!")
			case x if x < 1.0 => println("NONE")
			case _ => revfac(num/(pow+1), pow+1)
		}
	}

	def main(args: Array[String]) {
		val inputVal = args(0).toLong
		revfac(inputVal, 1)
	}
	
}
