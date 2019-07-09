package cn.fastDemo;

import org.csource.fastdfs.*;

public class test {

    public static void main(String[] args) throws Exception {

        ClientGlobal.init("F:\\Demo4\\pinyougou-parent\\fastDemo\\src\\main\\resources\\fdfs_client.conf");

        TrackerClient trackerClient = new TrackerClient();//管理集群客户端
        TrackerServer trackerServer = trackerClient.getConnection();//管理集群服务器端

        StorageServer storageServer=null;//保存文件客户端
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);//文件客户端


        String[] jpgs = storageClient.upload_file("e:/psb.jpg", "jpg", null);

        for (String jpg : jpgs) {
            System.out.println(jpg);
        }

    }
}
