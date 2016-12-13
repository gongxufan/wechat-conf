package cn.com.egova.wx.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gongxufan on 2016/8/25.
 */
public class ListUtil {
    /**
     * 分割List
     *
     * @return List<<List<T>>
     */
    public static <T> List<List<T>> splitList(List<T> list, int page) {
        List<List<T>> listArray = new ArrayList<List<T>>();
        Map<Integer, List<T>> maps = new HashMap<Integer, List<T>>();
        int size = list.size();
        for (int l = 0; l < page; l++) {
            maps.put(l, new ArrayList<T>());
        }
        if( size % page == 0){
            for(int k = 0 ; k < size ; k++){
                maps.get(k%page).add(list.get(k));
            }
        }else{
            for (int i = 0; i < size; ) {
                for (int j = 0; j < page; j++) {
                    if (i + page > size) {
                        int left = size - i;
                        for (int m = 0; m < left; m++) {
                            maps.get(m).add(list.get(i));
                            i++;
                        }
                    } else {
                        maps.get(j).add(list.get(i));
                        i++;
                    }
                }
            }
        }

        Iterator<Integer> it = maps.keySet().iterator();
        while (it.hasNext()) {
            listArray.add(maps.get(it.next()));
        }
        return listArray;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);



        List l = splitList(list, 2);
        System.out.println(l);
    }
}
