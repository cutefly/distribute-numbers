import java.math.BigInteger;

public class UniqueNumberGenerator {
    // private static final BigInteger A = new BigInteger("48271");  // Multiplier (LCG prime)
    private static final BigInteger A = new BigInteger("6700417");  // Multiplier (LCG prime)
    // private static final BigInteger A = new BigInteger("67280421310721");  // Multiplier (LCG prime)
    private static final BigInteger C = new BigInteger("12345");  // Increment
    // private static final BigInteger C = new BigInteger("5129784857803630");  // Increment
    private static final BigInteger M = new BigInteger("10000000000000000");  // Modulus (10^16)

    // Generate a unique 16-digit number from a sequence number
    public static String generateNumber(long sequence) {
        BigInteger seqBig = BigInteger.valueOf(sequence);
        BigInteger result = (seqBig.multiply(A).add(C)).mod(M);
        
        // Ensure 16 digits with zero-padding if necessary
        String number = result.toString();
        while (number.length() < 16) {
            number = "0" + number;
        }
        return number;
    }

    public static void main(String[] args) {
        long startNumber = 12345678;
        for (long i = 1; i <= 10; i++) {  // Example: Generate first 10 numbers
            System.out.println("Seq " + i + " -> " + generateNumber(i));
            // System.out.println("Seq " + (i+startNumber) + " -> " + generateNumber(i+startNumber));
        }
    }
}

