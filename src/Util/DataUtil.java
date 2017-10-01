package Util;

import dataclass.Data;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    private static double lowThreshold;//低阈值
    private static double highThreshold;//高阈值

    private static Data[] newLowDataArrays;//用来存储low区改变后的数据
    private static Data[] newMidileDataArrays;//用来存储medile区改变后的数据
    private static Data[] newHighDataArrays;//用来存储high区改变后的数据

    private static List<Data> oldLowDataList = new ArrayList<Data>();//low区原始数据集合
    private static List<Data> oldMidileDataList = new ArrayList<Data>();//midile区原始数据集合
    private static List<Data> oldHighDataList = new ArrayList<Data>();//high区原始数据集合

    private static int p1Top1=0;//从p1区到p1区的迁移数量
    private static int p1Top2=0;//从p1区到p2区的迁移数量
    private static int p1Top3=0;//从p1区到p3区的迁移数量

    private static int p2toP1=0;//从p2区到p1区的迁移数量
    private static int p2Top2=0;//从p2区到p2区的迁移数量
    private static int p2Top3=0;//从p2区到p3区的迁移数量


    private static int p3Top2=0;//从p3区到p2区的迁移数量
    private static int p3Top1=0;//从p3区到p1区的迁移数量
    private static int p3Top3=0;//从p3区到p3区的迁移数量

    private static double benefit=0;
    private static double cost=0;

    private static int[][] costMatrix=new int[3][3];//收益矩阵

    /*
        得到数据对象中分布数据中的最大值
     */
    public static Data getMaxNum(Data[] disDataArrays) {
        double[] disData;

        disData = new double[disDataArrays.length];

        for (int i = 0; i < disData.length; i++) {
            disData[i] = disDataArrays[i].getDisData();
        }

        double maxNum;
        int index = 0;


        maxNum = disData[0];
        for (int j = 1; j < disData.length; j++) {//获取最大的数值
            if (maxNum > disData[j]) {
                continue;
            } else {
                maxNum = disData[j];
                index = j;
            }
        }

        return disDataArrays[index];
    }

    /*
        得到数据对象中分布数据中的最小值
     */
    public static Data getMinNum(Data[] disDataArrays) {//得到最小值

        double[] disData;

        disData = new double[disDataArrays.length];

        for (int i = 0; i < disData.length; i++) {
            disData[i] = disDataArrays[i].getDisData();
        }


        double minNum;
        int index = 0;
        minNum = disData[0];
        for (int j = 1; j < disData.length; j++) {//获取最小的数值
            if (minNum < disData[j]) {
                continue;
            } else {
                minNum = disData[j];
                index = j;
            }
        }

        return disDataArrays[index];
    }

    /*
        得到数据对象中分布数据中的平均值
     */
    public static double getAverage(Data[] disDataArrays) {//得到平均值

        double[] disData;

        disData = new double[disDataArrays.length];

        for (int i = 0; i < disData.length; i++) {
            disData[i] = disDataArrays[i].getDisData();
        }

        double averageNum;
        int index = 0;
        double sum = 0;
        for (int j = 0; j < disData.length; j++) {
            sum += disData[j];
        }
        averageNum = sum / disData.length;

        return averageNum;

    }

    /*
        得到数据对象中分布数据中的中位数
     */
    public static double getMidile(Data[] disDataArrays) {//得到中位数

        double[] disData;

        disData = new double[disDataArrays.length];

        for (int i = 0; i < disData.length; i++) {
            disData[i] = disDataArrays[i].getDisData();
        }

        double mediaNum = 0;
        for (int x = 0; x < disData.length - 1; x++) {
            for (int y = 0; y < disData.length - x - 1; y++)

            {
                if (disData[y] > disData[y + 1]) {
                    double temp = disData[y];
                    disData[y] = disData[y + 1];
                    disData[y + 1] = temp;
                }
            }
        }
        if (disData.length % 2 == 0) {
            mediaNum = (disData[(int) (Math.floor(disData.length / 2))] + disData[(int) (Math.ceil(disData.length / 2))]) / 2;
        } else {
            mediaNum = disData[disData.length / 2];
        }

        return mediaNum;

    }

    /*
        获取初始分割后low区的数据
     */

    public static List<Data> getLowDataList(Data[] disDataArrays,double lowThreshol, double highThreshol ) {
        lowThreshold = lowThreshol;
        highThreshold = highThreshol;
        oldLowDataList.clear();
        for (int i = 0; i < disDataArrays.length; i++) {
            if (disDataArrays[i].getDisData() <= lowThreshold) {
                oldLowDataList.add(disDataArrays[i]);
                disDataArrays[i].setOldLocalSign(Data.LOW_LOCAL);//初始分割后，分区标识设置为低区
            }

        }
        return oldLowDataList;
    }

    /*
        获取初始分割后midile区的数据
     */

    public static List<Data> getMidileDataList(Data[] disDataArrays,double lowThreshol, double highThreshol) {
        lowThreshold = lowThreshol;
        highThreshold = highThreshol;
        oldMidileDataList.clear();
        for (int i = 0; i < disDataArrays.length; i++) {
            if (lowThreshold < disDataArrays[i].getDisData() && disDataArrays[i].getDisData() <= highThreshold) {
                oldMidileDataList.add(disDataArrays[i]);
                disDataArrays[i].setOldLocalSign(Data.MIDILE_LOCAL);//初始分割后，分区标识设置为中区
            }
        }
        return oldMidileDataList;
    }

    /*
        获取初始分割后high区的数据
     */

    public static List<Data> getHighDataList(Data[] disDataArrays,double lowThreshol, double highThreshol) {
        lowThreshold = lowThreshol;
        highThreshold = highThreshol;
        oldHighDataList.clear();
        for (int i = 0; i < disDataArrays.length; i++) {
            if (disDataArrays[i].getDisData() > highThreshold) {
                oldHighDataList.add(disDataArrays[i]);
                disDataArrays[i].setOldLocalSign(Data.HIGH_LOCAL);//初始分割后，分区标识设置为高区

            }
        }
        return oldHighDataList;
    }



    public static double getCalculatorReslut(Data[] disDataArrays,double m, double n, double k) {

        benefit = 0;//收益
        cost = 0;//消费
        /*
            把旧数据集合转换成数组，方便接下来的操作

         */
        Data[] oldLowDataArrays = (Data[]) oldLowDataList.toArray(new Data[oldLowDataList.size()]);
        Data[] oldMidileDataArrays = (Data[]) oldMidileDataList.toArray(new Data[oldMidileDataList.size()]);
        Data[] oldHighDataArrays = (Data[]) oldHighDataList.toArray(new Data[oldHighDataList.size()]);
        /*
            初始化存储新数据的3个区数组
         */
        newLowDataArrays =new Data[oldLowDataArrays.length];
        newMidileDataArrays =new Data[oldMidileDataArrays.length];
        newHighDataArrays =new Data[oldHighDataArrays.length];

        double mediaNum = getMidile(disDataArrays);//获取数组中位数
        /*
            判断原来的low区数据，并且将改变后的数据存储在newLowDataArrays数组中，如果有大于中位数的，在原来数据的基础上提高m%，如果没有则按照原来的数据存储
         */
        for (int i = 0; i < oldLowDataArrays.length; i++) {
            if (oldLowDataArrays[i].getDisData()< mediaNum) {
                newLowDataArrays[i]=new Data(oldLowDataArrays[i].getDataType());
                newLowDataArrays[i].setDisData((oldLowDataArrays[i].getDisData())* (1 + m));
                newLowDataArrays[i].setOldLocalSign(Data.LOW_LOCAL);//设置新的低区数组 旧分区标识为低区
                /*
                    如果low区的数变化后大于高阈值。benefit增加2，就是low到了high
                    如果low区的数变化小于高阈值大于低阈值，benefit增加1，就是low到了midile
                 */
                if (newLowDataArrays[i].getDisData() > highThreshold) {
                    oldLowDataArrays[i].setNewLocalSign(Data.HIGH_LOCAL);//设置旧的低区数组 新分区标识为高区
                    newLowDataArrays[i].setNewLocalSign(Data.HIGH_LOCAL);//设置新的低区数组 新分区标识为高区
                    benefit+=2;
                    p1Top3++;

                }else if (newLowDataArrays[i].getDisData()>lowThreshold){
                    oldLowDataArrays[i].setNewLocalSign(Data.MIDILE_LOCAL);//设置旧的低区数组，新分区标识为低区
                    benefit++;
                    p1Top2=p1Top2+1;

                }

            } else {
                newLowDataArrays[i]=new Data(oldLowDataArrays[i].getDataType());
                newLowDataArrays[i].setDisData(oldLowDataArrays[i].getDisData());
                oldLowDataArrays[i].setNewLocalSign(Data.LOW_LOCAL);//设置旧的低区数组 新分区标识为低区
                newLowDataArrays[i].setNewLocalSign(Data.LOW_LOCAL);//设置新的低区数组 新分区标识为低区
            }
        }

        /*
            判断原来的midile区数据，并且将改变后的数据存储在newMidileData数组中，如果有大于中位数的，在原来数据的基础上提高n%，如果没有则按照原来的数据存储
         */
        for (int j = 0; j < oldMidileDataArrays.length; j++) {
            if (oldMidileDataArrays[j].getDisData()< mediaNum) {
                newMidileDataArrays[j]=new Data(oldMidileDataArrays[j].getDataType());
                newMidileDataArrays[j].setOldLocalSign(Data.MIDILE_LOCAL);//设置新的中区数组 旧分区标识为低区
                newMidileDataArrays[j].setDisData((oldMidileDataArrays[j].getDisData())* (1 + n));
                /*
                    如果midile区的数变化后大于高阈值，benefit增加1，就是midile到了high
                 */
                if (newMidileDataArrays[j].getDisData()> highThreshold) {
                    oldMidileDataArrays[j].setNewLocalSign(Data.HIGH_LOCAL);//设置旧的中区数组 新分区标识为高区
                    newMidileDataArrays[j].setNewLocalSign(Data.HIGH_LOCAL);//设置新的中区数组 新分区标识为高区
                    benefit++;
                    p2Top3++;
                }
            } else {
                newMidileDataArrays[j]=new Data(oldMidileDataArrays[j].getDataType());
                newMidileDataArrays[j].setDisData(oldMidileDataArrays[j].getDisData());
                oldMidileDataArrays[j].setNewLocalSign(Data.MIDILE_LOCAL);//设置旧的中区数组 新分区标识为中区
                newMidileDataArrays[j].setNewLocalSign(Data.MIDILE_LOCAL);//设置新的中区数组 新分区标识为中区
            }
        }

        /*
            判断原来的high区数据，并且将改变后的数据存储在newHighData数组中，如果有大于中位数的，在原来数据的基础上减少k%，如果没有则按照原来的数据存储
         */
        for (int t = 0; t < oldHighDataArrays.length; t++) {
            if (oldHighDataArrays[t].getDisData() > mediaNum) {
                newHighDataArrays[t]=new Data(oldHighDataArrays[t].getDataType());
                newHighDataArrays[t].setOldLocalSign(Data.HIGH_LOCAL);//设置新的高区数组 旧分区标识为低区
                newHighDataArrays[t].setDisData( (oldHighDataArrays[t].getDisData())* (1 - k));

                /*
                    如果high区的数变化后小于了低阈值，则cost加2，即high到了low
                    如果high区的数变化后小于了高阈值但是高于低阈值，则cost加1，即high到了midile
                 */
                if (newHighDataArrays[t].getDisData() < lowThreshold) {
                    oldHighDataArrays[t].setNewLocalSign(Data.LOW_LOCAL);//设置旧的高区数组 新分区标识为低区
                    newHighDataArrays[t].setNewLocalSign(Data.LOW_LOCAL);//设置新的高区数组 新分区标识为低区
                    cost-=2;
                    p3Top1++;

                }else if (newHighDataArrays[t].getDisData()<highThreshold){
                    oldHighDataArrays[t].setNewLocalSign(Data.MIDILE_LOCAL);//设置旧的高区数组 新分区标识为中区
                    newHighDataArrays[t].setNewLocalSign(Data.MIDILE_LOCAL);//设置新的高区数组 新分区标识为中区
                    cost--;
                    p3Top2++;
                }

            } else {
                newHighDataArrays[t]=new Data(oldHighDataArrays[t].getDataType());
                newHighDataArrays[t].setDisData(oldHighDataArrays[t].getDisData());
                oldHighDataArrays[t].setNewLocalSign(Data.HIGH_LOCAL);//设置旧的高区数组 新分区标识为高区
                newHighDataArrays[t].setNewLocalSign(Data.HIGH_LOCAL);//设置新的高区数组 新分区标识为高区
            }
        }


        return benefit + cost;//返回最后的收益，即为benefit+cost,应为cost是负的

    }
    /*
        获取新低区数组

     */
    public static Data[] getNewLowDataArrays() {
        return newLowDataArrays;
    }

    /*
        获取新中区数组

     */
    public static Data[] getNewMidileDataArrays() {
        return newMidileDataArrays;
    }
    /*
            获取新高区数组

         */
    public static Data[] getNewHighDataArrays() {
        return newHighDataArrays;
    }
    /*
            获取旧低区集合

         */
    public static List<Data> getOldLowDataList() {
        return oldLowDataList;
    }

    /*
            获取旧中区集合

         */
    public static List<Data> getOldMidileDataList() {
        return oldMidileDataList;
    }

    /*
            获取旧高区集合

         */
    public static List<Data> getOldHighDataList() {
        return oldHighDataList;
    }

    /*
        获取收益benefit

     */

    public static double getBenefit() {
        return benefit;
    }

    /*
        获取大家cost

     */
    public static double getCost() {
        return cost;
    }

    /*
            获取代价矩阵
         */
    public static int[][] getCostMatrix(){

        System.out.println("    代价矩阵");

        costMatrix[0][0]=p1Top1;
        costMatrix[0][1]=p1Top2;
        costMatrix[0][2]=p1Top3;
        costMatrix[1][0]=p2toP1;
        costMatrix[1][1]=p2Top2;
        costMatrix[1][2]=p2Top3;
        costMatrix[2][0]=p3Top1;
        costMatrix[2][1]=p3Top2;
        costMatrix[2][2]=p3Top3;

        return costMatrix;
    }
}
