import java.math.BigInteger;

public class DistributedNumberGenerator {
    private static final BigInteger A = new BigInteger("48271");  // LCG Multiplier (Prime)
    // private static final BigInteger A = new BigInteger("6700417");  // LCG Multiplier (Prime)
    private static final BigInteger C = new BigInteger("52467453");  // LCG Increment
    private static final BigInteger M = new BigInteger("10000000000000000");  // 10^16 Modulus

    // Function to generate a unique, distributed 16-digit number from a sequence
    public static String generateNumber(long sequence) {
        BigInteger seqBig = BigInteger.valueOf(sequence);

        // Step 1: Apply LCG transformation
        BigInteger mixedValue = (seqBig.multiply(A).add(C)).mod(M);

        // Step 2: Apply bitwise transformation (XOR + shifts for scrambling)
        long num = mixedValue.longValue();
        num = num ^ (num << 21);
        num = num ^ (num >> 35);
        num = num ^ (num << 4);
        num = Math.abs(num % 10000000000000000L); // Keep within 16-digit limit

        // Step 3: Convert to a 16-digit string
        String number = String.valueOf(num);
        while (number.length() < 16) {
            number = "0" + number; // Ensure it's always 16 digits
        }

        return number;
    }

    public static void main(String[] args) {
        // Generate first 10 distributed numbers
        for (long i = 1; i <= 10; i++) {
            System.out.println("Seq " + i + " -> " + generateNumber(i));
        }
    }
}
