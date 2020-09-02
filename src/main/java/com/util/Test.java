package com.util;

import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.lang.reflect.Field;
import java.util.*;

/**
 * TODO 请详细说明该类的作用
 *
 * @author lvzhixiang
 * @since 2020/4/20
 */
public class Test {
    public static void main(String[] args) {
        int a = 1;
        int b = 0;
        if (!(a==0&&b==0)){
            System.out.println("没有同时为0的");
        }

    }
    /*public static void main(String[] args) {
        //相差的毫秒数
        long difference = 1000*60*60*60 + 1000*60 + 2560;
        //取基准点
        long base = System.currentTimeMillis();
        DateTime start = new DateTime(base);
        DateTime end = new DateTime(base +  difference);
        Interval interval = new Interval(start, end);
        Period period = interval.toPeriod();
        System.out.println(String.format("相差%d小时%d分钟%d秒%d毫秒",period.getHours(),period.getMinutes(),period.getSeconds(),period.getMillis()));
    }*/

    public static final String DESC = "desc";
    public static final String ASC = "asc";


    /**
     * 用途:对一个List集合数组进行排序
     *
     * 说明:
     *         目前可以对List<java.lang.Class>、List<POJO>、List<Map>这三种类型集合进行排序
     *
     * @param list       排序操作的集合对象
     * @param property  指定集合中元素的排序字段,如果集合元素不是对象类型可以传值为null
     * @param sort        用于指定是升序还是降序     CollectionsUtil.DESC--降序      CollectionsUtil.ASC--升序
     * @date 2018.04.27 PM
     */
    public static <T> void sortOnList(List<T> list, final Object property, final String sort){
        Collections.sort(list,new Comparator<T>(){
            @Override
            public int compare(T o1, T o2) {
                Integer a1 = null;
                Integer a2 = null;
                if(o1 instanceof Integer) {  //针对List<Integer>
                    a1 = (Integer) o1;
                    a2 = (Integer) o2;
                } else if(o1 instanceof String) { //针对List<String>
                    a1 = Integer.valueOf(o1.toString());
                    a2 = Integer.valueOf(o2.toString());
                } else if(o1 instanceof Map) {   //针对List<Map<String,String>>类型
                    Map temp1 = (Map) o1;
                    Map temp2 = (Map) o2;
                    Object object = temp1.get(property);
                    if(object instanceof Integer) {
                        a1 = (Integer) object;
                        a2 = (Integer) temp2.get(property);
                    } else if(object instanceof String){ //根据Map中value来进行排序String类型需要转换
                        a1 = Integer.parseInt(object.toString());
                        a2 = Integer.parseInt(temp2.get(property).toString());
                    }
                } else  {   //针对对象类型
                    Class c1 = o1.getClass();
                    Class c2 = o2.getClass();
                    try {
                        Field declaredField1 = c1.getDeclaredField(property.toString());
                        Field declaredField2 = c2.getDeclaredField(property.toString());
                        declaredField1.setAccessible(true);
                        declaredField2.setAccessible(true);
                        a1 = Integer.parseInt(declaredField1.get(o1).toString());
                        a2 = Integer.parseInt(declaredField2.get(o2).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(sort.equals("ASC"))   //升序
                    return a1.compareTo(a2);
                else                                   //降序
                    return a2.compareTo(a1);
            }
        });
    }
}
