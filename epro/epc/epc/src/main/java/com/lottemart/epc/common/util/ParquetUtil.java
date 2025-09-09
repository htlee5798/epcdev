package com.lottemart.epc.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.lottemart.epc.edi.product.controller.NEDMPROMgrCodeController;

public class ParquetUtil {
	
	private final Storage storage;
	private static final Logger logger = LoggerFactory.getLogger(ParquetUtil.class);
	
	public ParquetUtil(String credentialsPath) throws Exception {
		this.storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(Files.newInputStream(Paths.get(credentialsPath))))
                .build()
                .getService();
        //this.storage = StorageOptions.getUnauthenticatedInstance().getService();
		//ParquetUtil.gbn = "";
    }
	
	public void downloadParquet(String bucketName, String objectName, String localPath) throws Exception {
        //Storage storage = StorageOptions.getDefaultInstance().getService();
		
		File folder = new File(localPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
        Blob blob = storage.get(bucketName, objectName);
        
        if (blob == null) {
            throw new Exception("파일을 찾을 수 없습니다: " + objectName);
        }

        blob.downloadTo(Paths.get(localPath + objectName));
        System.out.println("파일 다운로드 완료: " + localPath + objectName);
    }
    
	public void convertParquetToCsv(String parquetFilePath, String csvFilePath) throws Exception {
        ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(new Path(parquetFilePath)).build();

        GenericRecord record;
        List<String> headers = new ArrayList<>();
        boolean headerWritten = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            while ((record = reader.read()) != null) {
                if (!headerWritten) {
                    for (Schema.Field field : record.getSchema().getFields()) {
                        headers.add(field.name());
                    }
                    writer.write(String.join(",", headers));
                    writer.newLine();
                    headerWritten = true;
                }

                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    Object value = record.get(header);
                    values.add(value != null ? value.toString() : "");
                }
                writer.write(String.join(",", values));
                writer.newLine();
            }
        }
        System.out.println("CSV 변환 완료: " + csvFilePath);
    }
	
	/*public void readCsvToData(String csvFilePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
        for (String line : lines) {
            System.out.println(line);
        }
    }*/
	
	public List<Map<String, Object>> readCsvToData(String csvFilePath){
		// 추후 return 할 데이터 목록
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
	    // return data key 목록
	    List<String> headerList = new ArrayList<String>();
	    
	    try{
	    	BufferedReader br = Files.newBufferedReader(Paths.get(csvFilePath));
	        String line = "";
	        
	        while((line = br.readLine()) != null){
				List<String> stringList = new ArrayList<String>();
				String stringArray[] = line.split(",");
				stringList = Arrays.asList(stringArray);
	            
				// csv 1열 데이터를 header로 인식
				if(headerList.size() == 0){
					headerList = stringList;
				} else {
	 				Map<String, Object> map = new HashMap<String, Object>();
					// header 컬럼 개수를 기준으로 데이터 set
					for(int i = 0; i < headerList.size(); i++){
						map.put(headerList.get(i), stringList.get(i));
					}
					mapList.add(map);
				}
			}
	        //System.out.println(mapList);
	    } catch (IOException e) {
	        logger.error(e.getMessage());
	    }
	    return mapList;
	}
	
	
	
	
}