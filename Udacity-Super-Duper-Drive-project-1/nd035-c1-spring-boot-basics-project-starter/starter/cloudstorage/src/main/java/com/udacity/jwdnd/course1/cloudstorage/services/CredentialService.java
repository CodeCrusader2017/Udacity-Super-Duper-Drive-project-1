package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Numutility;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating CredentialService bean");
    }

    public List<Credential> getCredentials(Integer userId){
        return credentialMapper.getUserCredentials(userId);
    }

    public int createCredential(Credential credential) throws SQLException
    {
        System.out.println("credential.getCredentialsId() = " + credential.getCredentialsId());
        //Despite following https://knowledge.udacity.com/questions/495060, unable to get auto_increment on database
        //column credentialid (on table CREDENTIALS) to work on insert, so created Numutility table as a workaround
        //to store key values to use in CREDENTIALS on insert
        if (credentialMapper.getNumid() == null) {
           credentialMapper.insertUtil(new Numutility(1, 0));
        }

        String encodedKey = getEncryptKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        return credentialMapper.insert(new Credential(credentialMapper.getNumid(), credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserId()));
    }

    public int updateCredential(Credential credential) throws SQLException {

        String updatedEncodedKey = getEncryptKey();
        String updatedEncryptedPassword = encryptionService.encryptValue(credential.getPassword(), updatedEncodedKey);

        return credentialMapper.updateCredential(new Credential(credential.getCredentialsId(), credential.getUrl(), credential.getUsername(), updatedEncodedKey, updatedEncryptedPassword, credential.getUserId()));
    }

    public int deleteCredential(int credentialsId) throws SQLException {
        return credentialMapper.deleteCredential(credentialsId);
    }

    //private helper method to generate encrypt key
    private String getEncryptKey() {
        SecureRandom random = new SecureRandom();
        byte[] encryptKey = new byte[16];
        random.nextBytes(encryptKey);
        String generatedEncodedKey = Base64.getEncoder().encodeToString(encryptKey);
        return generatedEncodedKey;
    }

}

