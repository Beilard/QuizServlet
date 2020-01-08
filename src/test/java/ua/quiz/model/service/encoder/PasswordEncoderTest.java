package ua.quiz.model.service.encoder;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordEncoderTest {

    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

    private static final String PASSWORD_NORMAL = "Password123#";
    private static final String PASSWORD_ENCODED = "f8hmhW/JTex1u5+a5ONDDQ==";

    @Test
    public void encryptNormalBehaviour() {
        final String actual = passwordEncoder.encrypt(PASSWORD_NORMAL);
        assertEquals(PASSWORD_ENCODED, actual);
    }

    @Test
    public void decryptNormalBehaviour() {
        final String actual = passwordEncoder.decrypt(PASSWORD_ENCODED);
        assertEquals(PASSWORD_NORMAL, actual);
    }
}