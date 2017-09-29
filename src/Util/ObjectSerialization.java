package Util;

import dataclass.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
/*
    将对象进行序列化，并且存储


 */
public class ObjectSerialization {//对象序列化存储到文件
    private static Data[] dataList;

    public static void objectSerialization(Data[] superData , int region) {
        dataList = superData;
        if (region==1) {
            outPutFile("D:\\GausDis.txt");
        } else if (region==2) {
            outPutFile("D:\\UnifDis.txt");
        } else if(region==3){
            outPutFile("D:\\ZipfDis.txt");
        }


    }

    private static void outPutFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (int i=0;i<1000;i++){
                oos.writeObject(dataList[i]);
            }

            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
