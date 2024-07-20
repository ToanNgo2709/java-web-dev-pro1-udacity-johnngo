package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credential {

    /**
     * Credential Id
     */
    private int credentialid;

    /**
     * URL
     */
    private String url;

    /**
     * Key
     */
    private String key;

    /**
     * Username
     */
    private String username;

    /**
     * Password
     */
    private String password;
}
