package com.example;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.auth.Credentials;
import com.google.cloud.NoCredentials;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
@Alternative
public class GoogleCredentialsMockProducer {

    @Produces
    public Credentials googleCredential() {
        return NoCredentials.getInstance();
    }

  @Produces
  public CredentialsProvider credentialsProvider() {
    return NoCredentialsProvider.create();
  }
}