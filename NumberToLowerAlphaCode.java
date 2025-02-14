import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NumberToLowerAlphaCode {

    // Base26 문자 집합 (소문자 a-z)
    private static final String BASE26 = "abcdefghijklmnopqrstuvwxyz";
    private static final String SECRET_KEY = "SecretKey123"; // 예측 방지를 위한 시크릿 키
    
    // 숫자를 SHA-256 해시 후 Base26으로 인코딩하는 함수
    public static String numberToLowerAlphaCode(int sequenceNumber) {
        try {
            // 1. SHA-256 해시 생성 (비밀키와 연번을 조합)
            String input = SECRET_KEY + sequenceNumber;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            
            // 2. 해시 결과를 10진수로 변환
            BigInteger decimalValue = new BigInteger(1, hash);
            
            // 3. 10진수 값을 Base26으로 인코딩
            StringBuilder base26Str = new StringBuilder();
            while (decimalValue.compareTo(BigInteger.ZERO) > 0) {
                base26Str.insert(0, BASE26.charAt(decimalValue.mod(BigInteger.valueOf(26)).intValue()));
                decimalValue = decimalValue.divide(BigInteger.valueOf(26));
            }
            
            // 4. 길이가 8자리가 될 때까지 앞에 'a' 추가 또는 자르기
            while (base26Str.length() < 8) {
                base26Str.insert(0, 'a'); // 자리수 부족 시 앞쪽에 'a' 추가
            }
            return base26Str.substring(0, 8);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 Algorithm not found", e);
        }
    }
    
    // 테스트 함수
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i + " -> " + numberToLowerAlphaCode(i));
        }
    }
}

