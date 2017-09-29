import Util.DataUtil;
import Util.ObjectSerialization;
import dataclass.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/*
    主类：用来获取各种需要顾客指定的数值，例如高低阈值和增长减少的值，并且最后输出三个区分割之后的收益

 */

public class StartCalculator {

    public static double lowThreshold;//低阈值
    private static double highThreshold;//高阈值
    private static double m = 0;//low区要提升的值
    private static double n = 0;//midle区要提升的值
    private static double k = 0;//high区要降低的值
    private static int  region=0;//用来选择分布的变量

    //private static CreateDataDis createDataDis;//用来创建分布数据对象的对象；

    private static Data[] dataDisList =new Data[10000];//用来接收存贮从CreateDataDis返回的数据对象,以后操作的都是这个数据对象数组

    private static double[] newLowDataArrays;
    private static double[] newMidileDataArrays;
    private static double[] newHighDataArrays;

    public static void main(String[] args) {
        //System.out.println("请输入要生成的分布对应的数字：1为正态分布，2为均匀分布，3为齐方分布");
        Scanner inPutScanner = new Scanner(System.in);

        while (true) {//输入下阈值
            System.out.println("请输入要设置的下阈值,  要求：大于0小于1");
            double temp = inPutScanner.nextDouble();
            if (temp > 0 && temp < 1) {
                lowThreshold = temp;
                break;
            } else {
                System.out.println("输入不符合要求，请重新输入");
                continue;
            }
        }


        while (true) {//输入上阈值
            System.out.println("请输入要设置的上阈值,  要求：大于0小于1");
            double temp = inPutScanner.nextDouble();
            if (temp > 0 && temp < 1) {
                highThreshold = temp;
                break;
            } else {
                System.out.println("输入不符合要求，请重新输入");
                continue;
            }
        }


        while (true) {//输入m
            System.out.println("请输入要low区要提高的m,  要求：大于0小于1");
            double temp = inPutScanner.nextDouble();
            if (temp > 0 && temp < 1) {
                m = temp;
                break;
            } else {
                System.out.println("输入不符合要求，请重新输入");
                continue;
            }
        }


        while (true) {//输入n
            System.out.println("请输入要midle区要提高的n,  要求：大于0小于1");
            double temp = inPutScanner.nextDouble();
            if (temp > 0 && temp < 1) {
                n = temp;
                break;
            } else {
                System.out.println("输入不符合要求，请重新输入");
                continue;
            }
        }


        while (true) {//输入k
            System.out.println("请输入要high区要减少的k,  要求：大于0小于1");
            double temp = inPutScanner.nextDouble();
            if (temp > 0 && temp < 1) {
                k = temp;
                break;
            } else {
                System.out.println("输入不符合要求，请重新输入");
                continue;
            }
        }

        System.out.println("请输入齐方分布的N");
        int N = inPutScanner.nextInt();
        System.out.println("请输入齐方分布的S");
        int S = inPutScanner.nextInt();
        System.out.println("请输入齐方分布的K");
        int K = inPutScanner.nextInt();


        for (int s = 1; s <= 3; s++) {//循环生成三个分布，打印数据

            //region = inPutScanner.nextInt();//从控制台获取要生成的分布的对应值
            region=s;

            if (region == 3) {//根据标识判断生成的分布


                for (int i = 0; i < 10000; i++) {
                    dataDisList[i] = CreateDataDis.generateDistribution(region, N, S, K);//创建齐夫分布对象数组
                }

            } else {
                for (int i = 0; i < 10000; i++) {
                    dataDisList[i] = CreateDataDis.generateDistribution(region);//创建正态分布和均匀分布数组
                }
            }


            ObjectSerialization.objectSerialization(dataDisList, region);//序列化对象，把对象存储到文件中去





            List<Data> lowList = DataUtil.getLowDataList(dataDisList, lowThreshold, highThreshold);//分割数组，获取低区数组
            List<Data> midileList = DataUtil.getMidileDataList(dataDisList, lowThreshold, highThreshold);//分割数组，获取中区数组
            List<Data> highList = DataUtil.getHighDataList(dataDisList, lowThreshold, highThreshold);//分割数组，获取高区数组

            newLowDataArrays = new double[lowList.size()];//将新的低区集合转化成数组，方便输出，已经屏蔽了输出
            for (int i = 0; i < newLowDataArrays.length; i++) {
                newLowDataArrays[i] = lowList.get(i).getDisData();
            }

            newMidileDataArrays = new double[midileList.size()];//将新的中区集合转化成数组，方便输出，已经屏蔽了输出
            for (int i = 0; i < newMidileDataArrays.length; i++) {
                newMidileDataArrays[i] = midileList.get(i).getDisData();
            }
            newHighDataArrays = new double[highList.size()];//将新的高区集合转化成数组，方便输出，已经屏蔽了输出
            for (int i = 0; i < newHighDataArrays.length; i++) {
                newHighDataArrays[i] = highList.get(i).getDisData();
            }

        /*
        判断一下是什么数据对象，然后调用outPutMessage输出对象数据的信息
         */
            if (region == 1) {
                outPutMessage("正态分布");
            } else if (region == 2) {
                outPutMessage("均匀分布");
            } else {
                outPutMessage("齐方分布");
            }

        /*
        //输出三个区变化后的收益，getCalculatorResult()方法就是进行收益计算的方法
         */


        }
    }
    /*
        用来输出数据对象个最大值，最小值，中位数，平均值
     */
    private static void outPutMessage(String name){
        double[] dataArrays=new double[100000];
        for (int i=0;i<10000;i++){
            dataArrays[i]=dataDisList[i].getDisData();
        }
        System.out.println();
        System.out.println("*****************************************");
        //System.out.println(name+"的原始数据为："+ Arrays.toString(dataArrays));
        //System.out.println(name+"的最大值为：" + DataUtil.getMaxNum(dataDisList).getDisData());
        //System.out.println(name+"的最小值为：" + DataUtil.getMinNum(dataDisList).getDisData());
        //System.out.println(name+"的中位数为：" + DataUtil.getMidile(dataDisList));
        //System.out.println(name+"的平均值为：" + DataUtil.getAverage(dataDisList));
        //System.out.println(name+"分割后的low区数据为"+Arrays.toString(newLowDataArrays));
        //System.out.println(name+"分割后的midile区数据为"+Arrays.toString(newMidileDataArrays));
        //System.out.println(name+"分割后的high区数据为"+Arrays.toString(newHighDataArrays));

        System.out.println(name+"收益为：" +DataUtil.getCalculatorReslut(dataDisList,m,n,k));
        System.out.println(name+"的Benefit为："+DataUtil.getBenefit());
        System.out.println(name+"的Cost为："+DataUtil.getCost());

        int[][] costMatrix = DataUtil.getCostMatrix();
        System.out.println(" \tp1 p2 p3");
        System.out.println("------------------");

        System.out.print("p1");
        System.out.print("\t"+costMatrix[0][0]);
        System.out.print("  "+costMatrix[0][1]);
        System.out.println("  "+costMatrix[0][2]);

        System.out.print("p2");
        System.out.print("\t"+costMatrix[1][0]);
        System.out.print("  "+costMatrix[1][1]);
        System.out.println("  "+costMatrix[1][2]);

        System.out.print("p3");
        System.out.print("\t"+costMatrix[2][0]);
        System.out.print("  "+costMatrix[2][1]);
        System.out.println("  "+costMatrix[2][2]);
        System.out.println("*****************************************");

    }
}
