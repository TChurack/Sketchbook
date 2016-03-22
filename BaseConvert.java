import java.math.*;

public class BaseConvert {
		public static BigInteger convertBase(int num, int base) {
			int currNum = num;
			StringBuilder sb = new StringBuilder();
			
			while (currNum > 0) {
				sb.append(currNum % base != 0 ? currNum % base : 0);
				currNum /= base;
			}
			
			return new BigInteger( sb.length() > 0 ? sb.reverse().toString() : "0");
		}
		
		public static void main(String[] args) {
			System.out.println(convertBase(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
		}
}