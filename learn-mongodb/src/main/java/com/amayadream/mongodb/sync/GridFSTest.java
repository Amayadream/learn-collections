package com.amayadream.mongodb.sync;

import com.mongodb.Block;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * mongodb gridfs操作
 * @author :  Amayadream
 * @date :  2016.11.14 21:25
 */
public class GridFSTest extends AbstractTest {

    /**
     * 上传文件
     * @throws FileNotFoundException
     */
    @Test
    public void test() throws FileNotFoundException {
        GridFSBucket bucket = GridFSBuckets.create(db, "gridfs");
        InputStream is = new FileInputStream(new File("D:\\Driver\\phantomjs.exe"));
        GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(1024)
                .metadata(new Document("type", "presentation"));
        ObjectId fileId = bucket.uploadFromStream("mongodb-tutorial", is, options);
        System.out.println(fileId);
    }

    @Test
    public void tests(){
        GridFSBucket bucket = GridFSBuckets.create(db, "gridfs");
        GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(1024)
                .metadata(new Document("type", "presentation"));
        byte[] data = "Data to update".getBytes(StandardCharsets.UTF_8);
        GridFSUploadStream us = bucket.openUploadStream("simpleData", options);
        us.write(data);
        us.close();
        System.out.println("The fileId of the uploaded file is: " + us.getFileId().toHexString());
    }

    /**
     * 搜索文件
     */
    @Test
    public void searchFile(){
        GridFSBucket bucket = GridFSBuckets.create(db, "gridfs");
        bucket.find(Filters.eq("filename", "simpleData")).forEach(new Block<GridFSFile>() {
            @Override
            public void apply(GridFSFile gridFSFile) {
                System.out.println(gridFSFile.getFilename());
            }
        });
//        bucket.find().forEach((Block<? super GridFSFile>) item -> System.out.println(item.getFilename()));
    }

    /**
     * 重命名
     */
    @Test
    public void rename(){
        GridFSBucket bucket = GridFSBuckets.create(db, "gridfs");
        bucket.rename(new ObjectId("5829dc05a64752589c42be0d"), "sss");
    }

    /**
     * 下载
     * @throws IOException
     */
    @Test
    public void download() throws IOException {
        GridFSBucket bucket = GridFSBuckets.create(db, "gridfs");
        FileOutputStream fos = new FileOutputStream("d:/phantomjs.exe");
        bucket.downloadToStream(new ObjectId("5829dc05a64752589c42be0d"), fos);
        fos.close();
    }

    /**
     * 删除
     */
    @Test
    public void delete(){
        GridFSBucket bucket = GridFSBuckets.create(db, "gridfs");
        bucket.delete(new ObjectId("5829dc05a64752589c42be0d"));
    }

}
