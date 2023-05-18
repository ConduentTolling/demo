package com.conduent.tpms.iag.dao.impl;

import java.io.File;
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
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.DeviceFileWrapper;
import com.conduent.tpms.iag.pub.MessagePublisher;
import com.conduent.tpms.iag.validation.FileUtil;
import com.conduent.tpms.iag.validation.GenericITagFileParserImpl;
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

@Service
public class TransactionDaoImpl implements TransactionDao{
	
	private static final Logger log = LoggerFactory.getLogger(TransactionDaoImpl.class);
	
	@Autowired
	protected ConfigVariable configVariable;

	@Autowired
	MessagePublisher messagePublisher;
	
	@Autowired
	GenericITagFileParserImpl genericITagFileParserImpl;
	
	private FileUtil fileUtil = new FileUtil();
	
	static boolean[] threadArr = new boolean[3];
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
     
	
	@Override
	public void preparFileAndUploadToExtern(File newFile , File oldFile, int agencySequence) {
		
		String location = configVariable.getAwaTagLocation().concat("//");       
		log.info("Awaytag file location: {}",location);
			try {
				/*
				 * AWAYTAG_FILE_NEW upload in external table
				 */
				
				DeviceFileWrapper deviceFileWrapperNewObj = new DeviceFileWrapper();
				String newAwaytagBucket = configVariable.getBucketNameNew();
				String newAwaytagObject = configVariable.getObjectNameNew();
				newAwaytagBucket = newAwaytagBucket + "_" + agencySequence;
				
				deviceFileWrapperNewObj.setBucketName(newAwaytagBucket);
				deviceFileWrapperNewObj.setObjectName(newAwaytagObject);
				log.info("New awaytag Bucket name: {}, Object name: {}",newAwaytagBucket,newAwaytagObject);
				
		
				File awaytagnew = new File(location.concat("awaytag.new")); 
				log.info("New awaytag File name: {}",awaytagnew.getAbsolutePath());
				
				fileUtil.copyFileUsingApacheCommonsIO(newFile,awaytagnew);
				
				deviceFileWrapperNewObj.setFileName(awaytagnew.getName());
				deviceFileWrapperNewObj.setFile(awaytagnew);
				
				uploadProcess(deviceFileWrapperNewObj);
				
				/*
				 * AWAYTAG_FILE_OLD upload in external table
				 */
				
				DeviceFileWrapper deviceFileWrapperOldObj = new DeviceFileWrapper();
				String oldAwaytagBucket = configVariable.getBucketNameOld();
				String oldAwaytagObject = configVariable.getObjectNameOld();
				
				oldAwaytagBucket = oldAwaytagBucket + "_" + agencySequence;
				
				deviceFileWrapperOldObj.setBucketName(oldAwaytagBucket);
				deviceFileWrapperOldObj.setObjectName(oldAwaytagObject);
				log.info("Old awaytag Bucket name: {}, Object name: {}",oldAwaytagBucket,oldAwaytagObject);

				File awaytagold = new File(location.concat("awaytag.old")); 
				log.info("Old awaytag File name: {}",awaytagold.getAbsolutePath());
				
				fileUtil.copyFileUsingApacheCommonsIO(oldFile,awaytagold);
				
				deviceFileWrapperOldObj.setFileName(awaytagold.getName());
				deviceFileWrapperOldObj.setFile(awaytagold);
				
				uploadProcess(deviceFileWrapperOldObj);
				
				genericITagFileParserImpl.updateDeviceTagDetails(newAwaytagBucket, oldAwaytagBucket);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	}
	
	 
	@Override
	public void uploadProcess(DeviceFileWrapper deviceFileWrapper) throws IOException {
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

		log.info("response: ",response);

		GetBucketRequest bucketRequest = GetBucketRequest.builder().namespaceName(namespaceName).bucketName(bucketName)
				.build();

		log.info("Fetching bucket details");
		GetBucketResponse bucketResponse = client.getBucket(bucketRequest);

		log.info("bucketResponse: " + bucketResponse.get__httpStatusCode__());
		log.info("bucketResponse: " + bucketResponse.getBucket());
		
	}
}

class Data{
	private File newFilePath;
	public Data(File newFilePath, File oldFilePath) {
		//super();
		this.newFilePath = newFilePath;
		this.oldFilePath = oldFilePath;
	}
	public File getNewFilePath() {
		return newFilePath;
	}
	public void setNewFilePath(File newFilePath) {
		this.newFilePath = newFilePath;
	}
	public File getOldFilePath() {
		return oldFilePath;
	}
	public void setOldFilePath(File oldFilePath) {
		this.oldFilePath = oldFilePath;
	}
	private File oldFilePath;
}
