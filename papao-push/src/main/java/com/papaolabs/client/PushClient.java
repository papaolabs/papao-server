package com.papaolabs.client;

import com.clevertap.apns.ApnsClient;
import com.clevertap.apns.Notification;
import com.clevertap.apns.NotificationResponse;
import com.clevertap.apns.NotificationResponseListener;
import com.clevertap.apns.clients.ApnsClientBuilder;
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
    private ApnsClient client;

    public PushClient(Resource cert, String password, String topic) {
        try {
            client = new ApnsClientBuilder()
                .withProductionGateway()
                .inAsynchronousMode()
                .withCertificate(cert.getInputStream())
                .withPassword(password)
                .withDefaultTopic(topic)
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

    public void send(String deviceId, String message, String type, String postId) {
        Notification n = new Notification.Builder(deviceId)
            .customField("type", type)
            .customField("postId", postId)
            .badge(1)
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
