import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HashToBase62 {

    // Base62 문자 집합
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String SECRET_KEY = "MySecretKey1234"; // 예측 방지를 위한 시크릿 키
    private static final Random RANDOM = new Random();
    
    // SHA-256 해시 후 Base62로 인코딩하는 함수
    public static String hashToBase62(int sequenceNumber) {
        try {
            String input = SECRET_KEY + sequenceNumber;
            // 1. SHA-256 해시 생성
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(String.valueOf(input).getBytes());
            
            // 2. 해시 결과를 16진수 문자열로 변환
            BigInteger decimalValue = new BigInteger(1, hash);
            
            // 3. 10진수 값을 Base62로 인코딩
            StringBuilder base62Str = new StringBuilder();
            while (decimalValue.compareTo(BigInteger.ZERO) > 0) {
                base62Str.insert(0, BASE62.charAt(decimalValue.mod(BigInteger.valueOf(62)).intValue()));
                decimalValue = decimalValue.divide(BigInteger.valueOf(62));
            }
            
            String processingValue = base62Str.toString();
            // 4. 길이가 8자리가 되도록 랜덤 문자 추가 또는 자르기
            while (base62Str.length() < 8) {
                base62Str.append(BASE62.charAt(RANDOM.nextInt(BASE62.length())));
            }
            String finalValue = base62Str.substring(0, 8);
            // System.out.println(sequenceNumber + "\t" + processingValue + "\t" + finalValue);
            return base62Str.substring(0, 8);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 Algorithm not found", e);
        }
    }
    
    // 테스트 함수
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            hashToBase62(i);
            System.out.println(i + "\t" + hashToBase62(i));
        }
    }
}

