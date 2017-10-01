package Util;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;

public class OutPutExcel {

    public static void writeExcel(int region,double[] oldData,double[] newData){

        File file=new File("");
        String filePath=file.getAbsolutePath();
        String fileName=null;
        if(region==1){
            fileName="/正态分布数据.xls";
        }else if (region==2){
            fileName="/均匀分布数据.xls";
        }else if (region==3){
            fileName="/齐夫分布数据.xls";
        }

        try {
            File fileExcel=new File(filePath+fileName);

            if (fileExcel.exists()){
                fileExcel.delete();
            }

            WritableWorkbook book = Workbook.createWorkbook(fileExcel);

            WritableSheet sheet = book.createSheet("第一张工作表", 0);
            Label oldLabel = new Label(0, 0, "旧数据");
            Label newLabel=new Label(1,0,"新数据");
            sheet.addCell(oldLabel);
            sheet.addCell(newLabel);

            for (int i=0;i<10000;i++){//写旧数据
                jxl.write.Number oldNumber = new jxl.write.Number(0, (i+1), oldData[i]);
                sheet.addCell(oldNumber);
            }

            for (int i=0;i<10000;i++){//写新数据
                jxl.write.Number newNumber = new jxl.write.Number(1, (i+1), newData[i]);
                sheet.addCell(newNumber);
            }

            book.write();
            book.close();
            System.out.println("写入完成Excel完成");

        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
