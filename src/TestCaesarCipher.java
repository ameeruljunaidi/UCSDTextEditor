import edu.duke.FileResource;

public class TestCaesarCipher {
    /**
     * Test to break an encrypted message
     * Figure out which key was used to encrypt this message
     *
     * @param input in the encrypted message to break
     * @return the decrypted message
     */
    public String breakCaesarCipher(String input) {
        int keyFound = CaesarBreaker.getKey(input);

        CaesarCipher cc = new CaesarCipher(keyFound);

        return cc.decrypt(input);
    }

    public void simpleTests() {
        String message = "Can you imagine life WITHOUT the internet AND computers in your pocket?";

        CaesarCipher cc = new CaesarCipher(15);
        String encryptedMessage = cc.encrypt(message);
        String decryptedMessage = breakCaesarCipher(encryptedMessage);

        System.out.println(encryptedMessage);
        System.out.println(decryptedMessage);
    }

    public static void main(String[] args) {
        TestCaesarCipher tcc = new TestCaesarCipher();
        tcc.simpleTests();
    }
}
