package com.papaolabs.client;

import com.clevertap.apns.ApnsClient;
import com.clevertap.apns.Notification;
import com.clevertap.apns.NotificationResponse;
import com.clevertap.apns.NotificationResponseListener;
import com.clevertap.apns.clients.ApnsClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Component
public class PushClient {
    @Value(value = "")
    private Resource cert;
    private ApnsClient client;

    public void init() {
        try {
            client = new ApnsClientBuilder()
                .withProductionGateway()
                .inSynchronousMode()
                .withCertificate(cert.getInputStream())
                .withPassword("")
                .withDefaultTopic("papao")
                .build();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void send(String deviceId, String message) {
        Notification n = new Notification.Builder(deviceId)
            .alertBody(message)
            .build();
        client.push(n, new NotificationResponseListener() {
            @Override
            public void onSuccess(Notification notification) {
                System.out.println("success!");
            }

            @Override
            public void onFailure(Notification notification, NotificationResponse nr) {
                System.out.println("failure: " + nr);
            }
        });
    }
}
