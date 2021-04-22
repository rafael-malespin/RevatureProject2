package com.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.controller.UserController;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


@Service("s3ServiceConfig")
public class S3ServiceImpl implements S3Service{

    final static Logger loggy = Logger.getLogger(UserController.class);
    static {
        loggy.setLevel(Level.ALL);
        //loggy.setLevel(Level.ERROR);
    }

    private String awsID = System.getenv("AWS_ID");
    private String awsKey = System.getenv("AWS_KEY");

    private String region = System.getenv("AWS_REGION");
    private String bucketName = System.getenv("AWS_BUCKET");

    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsID, awsKey);
    final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

    /**
     * Uses AWS S3 credentials to access a specified S3 bucket and stores a file object in that bucket.
     * @param keyName String used to represent the name of the file.
     * @param file File object from HTTP request.
     */
    @Override
    public void uploadFile(String keyName, File file) {
        try {
            s3Client.putObject(bucketName, keyName, file);

        } catch (AmazonServiceException ase) {
            loggy.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            loggy.info("Error Message:    " + ase.getMessage());
            loggy.info("HTTP Status Code: " + ase.getStatusCode());
            loggy.info("AWS Error Code:   " + ase.getErrorCode());
            loggy.info("Error Type:       " + ase.getErrorType());
            loggy.info("Request ID:       " + ase.getRequestId());
            throw ase;
        } catch (AmazonClientException ace) {
            loggy.info("Caught an AmazonClientException: ");
            loggy.info("Error Message: " + ace.getMessage());
            throw ace;
        }
    }
}
