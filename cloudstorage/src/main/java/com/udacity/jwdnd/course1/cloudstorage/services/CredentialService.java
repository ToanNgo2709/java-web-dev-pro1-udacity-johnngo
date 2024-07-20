package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private EncryptionService encryptionService;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int RANDOM_STRING_LENGTH = 16;
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    private Credential encryptPassword(Credential credential) {
        String key = generateRandomString(RANDOM_STRING_LENGTH);
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));
        return credential;
    }

    public Credential decryptPassword(Credential credential) {
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

    public List<Credential> getCredentialsByUser(int userid) throws Exception {
        List<Credential> credentials = credentialMapper.findByUserId(userid);
        if(credentials.isEmpty()) {
            return new ArrayList<>();
        }
        return credentials.stream().map(this::decryptPassword).collect(Collectors.toList());
    }

    public void addCredential(Credential credential, int userid) {
        credentialMapper.insert(encryptPassword(credential), userid);
    }

    public void updateCredential(Credential credential) {
        credentialMapper.update(encryptPassword(credential));
    }

    public void deleteCredential(int credentialid) {
        credentialMapper.delete(credentialid);
    }
}
