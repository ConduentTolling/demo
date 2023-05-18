package com.conduent.tpms.iag.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.conduent.tpms.iag.dao.TransactionDao;
import com.conduent.tpms.iag.exception.BucketFullException;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.DeviceFileWrapper;
import com.conduent.tpms.iag.pub.MessagePublisher;
import com.conduent.tpms.iag.validation.FileUtil;
import com.conduent.tpms.iag.validation.GenericICLPFileParserImpl;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.GetBucketRequest;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetBucketResponse;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;
import com.oracle.bmc.objectstorage.transfer.UploadConfiguration;
import com.oracle.bmc.objectstorage.transfer.UploadManager;
import com.oracle.bmc.objectstorage.transfer.UploadManager.UploadRequest;
import com.oracle.bmc.objectstorage.transfer.UploadManager.UploadResponse;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class TransactionServiceImpl implements TransactionDao, Runnable{
	
	private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

	
	
	@Autowired
	protected ConfigVariable configVariable;

	@Autowired
	MessagePublisher messagePublisher;
	
	@Autowired
	GenericICLPFileParserImpl genericICLPFileParserImpl;
	
	private FileUtil fileUtil = new FileUtil();
	
	static boolean[] threadArr = new boolean[5];
	static int  indexCount;
	
    private int threadCount; 
	
    private File newFile;
    private File oldFile;
	public File getNewFile() {
		return newFile;
	}

	public void setNewFile(File newFile) {
		this.newFile = newFile;
	}

	public File getOldFile() {
		return oldFile;
	}

	public void setOldFile(File oldFile) {
		this.oldFile = oldFile;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	private synchronized int getAvailableBucket() throws BucketFullException {
		log.info("Index count {}",indexCount);
		log.info("Array Posotion {}",threadArr);
		if(indexCount == threadArr.length) {
			throw new BucketFullException("Queue is full");
		}
		
		for(int i = 1 ; i < threadArr.length; i++ ) {
			if(threadArr[i] == false ) {
				threadArr[i] = true;
				indexCount++;
				return i;
			}
			log.info("Index Value {}",i);
		}
		log.info("Array Posotion {}",threadArr);
		log.info("Index count {}",indexCount);
		return 0;
			
	}
	
	private synchronized void rleaseIndexPosition(int threadIndex) {
		log.info("Index count in release {}",indexCount);
		log.info("Index value in release {}",threadIndex);
		threadArr[threadIndex] = false;
		indexCount--;	
		log.info("Index count in release {}",indexCount);
		log.info("Index value in release {}",threadIndex);
		log.info("Array Posotion in release {}",threadArr);
		

	}
     
	
	public void preparFileAndUploadToExtern1(File newFile , File oldFile) throws Exception {
		log.info("Inside preparFileAndUploadToExtern1 method");
		String location = configVariable.getAwaTagLocation().concat("//");
		log.info("Lic file location: {}",location);
			try {	
				
				/*
				 * Lic_FILE_NEW upload in external table
				 */				
				DeviceFileWrapper deviceFileWrapperNewObj = new DeviceFileWrapper();
				String newLicBucket = configVariable.getBucketNameNew();
				String newLicObject = configVariable.getObjectNameNew();
				
				DeviceFileWrapper deviceFileWrapperOldObj = new DeviceFileWrapper();
				String oldLicBucket = configVariable.getBucketNameOld();
				String oldLicObject = configVariable.getObjectNameOld();
				int index = getAvailableBucket();
				// track threads
				
						newLicBucket = newLicBucket + "_" + index;
						oldLicBucket = oldLicBucket + "_" + index;
				
						location = location + "_"+index +"\\";
						
						log.info("After Appending Lic file location: {}",location);
				
				deviceFileWrapperNewObj.setBucketName(newLicBucket);
				deviceFileWrapperNewObj.setObjectName(newLicObject);
				log.info("New Lic Bucket name: {}, Object name: {}, Thread {}",newLicBucket,newLicObject,
						newFile.getAbsolutePath());
		
				File Licnew = new File(location.concat("licplate.new")); 
				log.info("New Lic File name: {}",Licnew.getAbsolutePath());
				
				fileUtil.copyFileUsingApacheCommonsIO(newFile,Licnew);
				
				deviceFileWrapperNewObj.setFileName(Licnew.getName());
				deviceFileWrapperNewObj.setFile(Licnew);
				log.info("Upload is starting for new File**** Thread {}",newFile.getAbsolutePath());
				uploadProcess(deviceFileWrapperNewObj);
				log.info("Uploaded new File successfully thread {}",newFile.getAbsolutePath());
				/*
				 * Lic_FILE_OLD upload in external table
				 */			
				
				deviceFileWrapperOldObj.setBucketName(oldLicBucket);
				deviceFileWrapperOldObj.setObjectName(oldLicObject);
				log.info("Old Lic Bucket name: {}, Object name: {}, Thread {}",oldLicBucket,oldLicObject,newFile.getAbsolutePath());

				File Licold = new File(location.concat("licplate.old")); 
				log.info("Old Lic File name: {} Thread {}",Licold.getAbsolutePath(),newFile.getAbsolutePath());
				
				fileUtil.copyFileUsingApacheCommonsIO(oldFile,Licold);
				
				deviceFileWrapperOldObj.setFileName(Licold.getName());
				deviceFileWrapperOldObj.setFile(Licold);
				
				log.info("Upload is starting for new File*** Thread {}",newFile.getAbsolutePath());
				uploadProcess(deviceFileWrapperOldObj);
				log.info("Uploaded new File successfully thread {}",newFile.getAbsolutePath());
				rleaseIndexPosition(index);
				//genericICLPFileParserImpl.updateDeviceTagDetails_Iclp(newLicBucket, oldLicBucket);
				genericICLPFileParserImpl.updateDeviceTagDetails(newLicBucket, oldLicBucket);
			} catch (IOException e1) {
				log.info("Error occured {}",e1.getMessage());
				e1.printStackTrace();
			}finally {
				
			}
			
	}
	
	@Override
	public void preparFileAndUploadToExtern(File newFile , File oldFile , int agencySequence) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
		
		String location = configVariable.getAwaTagLocation().concat("//");
		log.info("Lic file location: {}",location);
			try {
				
				/*
				 * Lic_FILE_NEW upload in external table
				 */
				
				DeviceFileWrapper deviceFileWrapperNewObj = new DeviceFileWrapper();
				String newLicBucket = configVariable.getBucketNameNew();
				String newLicObject = configVariable.getObjectNameNew();
				newLicBucket = newLicBucket + "_" + agencySequence;
				
				deviceFileWrapperNewObj.setBucketName(newLicBucket);
				deviceFileWrapperNewObj.setObjectName(newLicObject);
				log.info("New Lic Bucket name: {}, Object name: {}",newLicBucket,newLicObject);
		
				File Licnew = new File(location.concat("licplate.new")); 
				log.info("New Lic File name: {}",Licnew.getAbsolutePath());
				
				fileUtil.copyFileUsingApacheCommonsIO(newFile,Licnew);
				
				deviceFileWrapperNewObj.setFileName(Licnew.getName());
				deviceFileWrapperNewObj.setFile(Licnew);
				
				uploadProcess(deviceFileWrapperNewObj);
				
				/*
				 * Lic_FILE_OLD upload in external table
				 */
				
				DeviceFileWrapper deviceFileWrapperOldObj = new DeviceFileWrapper();
				String oldLicBucket = configVariable.getBucketNameOld();
				String oldLicObject = configVariable.getObjectNameOld();
				oldLicBucket = oldLicBucket + "_" + agencySequence;
				
				deviceFileWrapperOldObj.setBucketName(oldLicBucket);
				deviceFileWrapperOldObj.setObjectName(oldLicObject);
				log.info("Old Lic Bucket name: {}, Object name: {}",oldLicBucket,oldLicObject);

				File Licold = new File(location.concat("licplate.old")); 
				log.info("Old Lic File name: {}",Licold.getAbsolutePath());
				
				fileUtil.copyFileUsingApacheCommonsIO(oldFile,Licold);
				
				deviceFileWrapperOldObj.setFileName(Licold.getName());
				deviceFileWrapperOldObj.setFile(Licold);
				
				uploadProcess(deviceFileWrapperOldObj);
				
				genericICLPFileParserImpl.updateDeviceTagDetails(newLicBucket, oldLicBucket);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	}
	
	 
	@Override
	public void uploadProcess(DeviceFileWrapper deviceFileWrapper) throws FileNotFoundException,IOException {

		try {
			String bucketName = deviceFileWrapper.getBucketName();
			String objectName = deviceFileWrapper.getObjectName();
				
			Map<String, String> metadata = null;
			String contentType = "text/plain";
			String contentLanguage = "en-US";

			File file = ResourceUtils.getFile(configVariable.getConfigfilepath()); 

			final String configurationFilePath = file.toString();

			final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath);

			final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
			log.info("FINGERPRINT    " + provider.getFingerprint());
			ObjectStorage client = new ObjectStorageClient(provider);
			client.setRegion(Region.US_ASHBURN_1);

			GetNamespaceResponse namespaceResponse = client.getNamespace(GetNamespaceRequest.builder().build());
			String namespaceName = namespaceResponse.getValue(); 
			log.info("Using namespace: " + namespaceName);

			UploadConfiguration uploadConfiguration = UploadConfiguration.builder().allowMultipartUploads(true)
					.allowParallelUploads(true).build(); 

			UploadManager uploadManager = new UploadManager(client, uploadConfiguration);

			Path sourcePath = Paths.get(deviceFileWrapper.getFile().getPath());
			InputStream inputStream = Files.newInputStream(sourcePath);
			log.info("inputStream:"+inputStream);
			
			File body = sourcePath.toFile();
			PutObjectRequest request = PutObjectRequest.builder().bucketName(bucketName).namespaceName(namespaceName)
					.objectName(objectName).contentType(contentType).build();
			log.info("Namespace " + request.getNamespaceName());
			
			UploadRequest uploadDetails = UploadRequest.builder(body).build(request);

			UploadResponse response = uploadManager.upload(uploadDetails);

			log.info("Upload manager response data: {}",response);

			GetBucketRequest bucketRequest = GetBucketRequest.builder().namespaceName(namespaceName).bucketName(bucketName)
					.build();

			log.info("Fetching bucket details");
			GetBucketResponse bucketResponse = client.getBucket(bucketRequest);

			log.info("bucketResponse: " + bucketResponse.get__httpStatusCode__());
			log.info("bucketResponse: " + bucketResponse.getBucket());
		  } catch (FileNotFoundException e) {
			log.info("Exception in upload process",e);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void printContent(File file) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			try {
				log.info("awaytagnew file: {}",file.getAbsolutePath());
				while ((line = br.readLine()) != null) {
					log.info(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
