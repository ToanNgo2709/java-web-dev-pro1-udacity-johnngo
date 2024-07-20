package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @PostMapping()
    public String saveOrUpdateCredentials(Authentication authentication, Credential credential) {
        User user = (User) authentication.getPrincipal();
        if (credential.getCredentialid() > 0) {
            credentialService.updateCredential(credential);
        }
        else {
            credentialService.addCredential(credential, user.getUserid());
        }
        return "redirect:/result?success";
    }

    @DeleteMapping("/delete")
    public String deleteCredential(
            @RequestParam("id") int credentialid
    ) {
        if (credentialid > 0) {
            credentialService.deleteCredential(credentialid);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}
