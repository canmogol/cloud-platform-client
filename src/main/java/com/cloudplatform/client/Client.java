package com.cloudplatform.client;


public class Client {

    public static void main(String[] args) {
        new Thread(new CloudPlatformResourceClient("http://localhost:8080/cloud-platform-rws/api/")).start();
    }

}
