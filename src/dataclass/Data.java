package dataclass;

import java.io.Serializable;
import java.util.Random;

public class Data implements Serializable{

    public static final int GAUS_DIS=1;//正态分布标识
    public static final int UNIF_DIS=2;//均匀分布标识
    public static final int ZIPF_DIS=3;//齐夫分布标识

    public static final int LOW_LOCAL=1;
    public static final int MIDILE_LOCAL=2;
    public static final int HIGH_LOCAL=3;

    private double N;
    private double S;


    private double disData;//数据存储

    private int dataType;

    private int oldLocalSign;//变换前分区标识
    private int newLocalSign;//变换后分区标识

    public Data(int sign) {
        if (sign == GAUS_DIS) {
            initGausDisData();
        } else if (sign == UNIF_DIS) {
            initUnifDisData();
        }
    }

    public Data(int sign,double n,double s,double k) {
       if (sign == ZIPF_DIS) {
            initZipfDisData(n,s,k);
        }
    }


    private void initGausDisData(){//a是均值，b是浮动范围
            Random random = new Random();
            //double temp = (int) (Math.sqrt(b) * random.nextGaussian() + a);
            double temp = random.nextGaussian();
            disData=temp;
            dataType=GAUS_DIS;
    }

    private void initUnifDisData() {//得到一个均分分布
        Random random = new Random();
        double temp = random.nextDouble();
        disData=temp;
        dataType=UNIF_DIS;

    }

    private void initZipfDisData(double n,double s,double k) {
        this.N=n;
        this.S=s;
        double temp=( 1 / Math.pow(k, this.S) ) / H(this.N, this.S);
        disData=temp;
        dataType=ZIPF_DIS;
    }

    public double getDisData() {
        return disData;
    }

    public void setDisData(double disData) {
        this.disData = disData;
    }

    public int getOldLocalSign() {
        return oldLocalSign;
    }

    public void setOldLocalSign(int oldLocalSign) {
        this.oldLocalSign = oldLocalSign;
    }

    public int getNewLocalSign() {
        return newLocalSign;
    }

    public void setNewLocalSign(int newLocalSign) {
        this.newLocalSign = newLocalSign;
    }

    public double H(double n, double s) { // Harmonic number
        if(n == 1) {
            return 1.0 / Math.pow(n,s);
        } else {
            return ( 1.0 / Math.pow( n, s ) ) + H( n - 1, s );
        }
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
