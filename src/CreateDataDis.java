import dataclass.*;
/*
    此类用来生成数据对象，方便以后添加其他数据分布


 */

public class CreateDataDis {


    public static final int GAUSDIS_DIS = 1;//生成正态分布的标识
    public static final int UNIFDIS_DIS = 2;//生成均匀分布的标识
    public static final int ZIPFDIS_DIS = 3;//生成齐方分布的标识

    private static Data disData;//用来存储创建的数据对象


    /*
        生成数据对象，通过判断region的值来选择生成对应的数据对象
     */
    public static Data generateDistribution(int region) {

        if (region==GAUSDIS_DIS) {
            disData = new Data(Data.GAUS_DIS);

        }else if (region==UNIFDIS_DIS){
            disData = new Data(Data.UNIF_DIS);
        }

        return disData;

    }

    public static Data generateDistribution(int region,int n,int s,int k){
        if (region==ZIPFDIS_DIS){
            disData=new Data(Data.ZIPF_DIS,n,s,k);
        }
        return disData;
    }
}
