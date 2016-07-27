package com.test.cxf.webservice.impl;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created by quzhiwen on 2016/7/26.
 */
public class Test {
    @org.junit.Test
    public void test() {
        try {
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("file1", new FileBody(new File("E:\\1.txt")));
            entity.addPart("file2", new FileBody(new File("E:\\scoc_control-0.0.3-SNAPSHOT.jar")));

            HttpPost request = new HttpPost("http://localhost:8016/cxf/uploadlist");
            request.setEntity(entity);
            HttpClient client = new DefaultHttpClient();
            client.execute(request);
            System.out.println("上传文件成功！");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
