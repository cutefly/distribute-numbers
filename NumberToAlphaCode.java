import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NumberToAlphaCode {

    // Base52 문자 집합 (대문자 A-Z, 소문자 a-z)
    private static final String BASE52 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String SECRET_KEY = "MySecretKey1234"; // 예측 방지를 위한 시크릿 키
    
    // 숫자를 SHA-256 해시 후 Base52로 인코딩하는 함수
    public static String numberToAlphaCode(int sequenceNumber) {
        try {
            // 1. SHA-256 해시 생성 (비밀키와 연번을 조합)
            String input = SECRET_KEY + sequenceNumber;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            
            // 2. 해시 결과를 16진수 문자열로 변환
            BigInteger decimalValue = new BigInteger(1, hash);
            
            // 3. 10진수 값을 Base52로 인코딩
            StringBuilder base52Str = new StringBuilder();
            while (decimalValue.compareTo(BigInteger.ZERO) > 0) {
                base52Str.insert(0, BASE52.charAt(decimalValue.mod(BigInteger.valueOf(52)).intValue()));
                decimalValue = decimalValue.divide(BigInteger.valueOf(52));
            }
            
            // 4. 길이가 8자리가 될 때까지 앞에 'A' 추가 또는 자르기
            while (base52Str.length() < 8) {
                base52Str.insert(0, 'A'); // 자리수 부족 시 앞쪽에 'A' 추가
            }
            return base52Str.substring(0, 8);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 Algorithm not found", e);
        }
    }
    
    // 테스트 함수
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i + " -> " + numberToAlphaCode(i));
        }
    }
}

