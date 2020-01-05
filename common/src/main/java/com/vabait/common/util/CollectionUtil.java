package com.vabait.common.util;

import java.util.*;

public class CollectionUtil {
    public static List<String> keyListSortedValueAsc(Map<String, Double> map) {
        List<String> result = new ArrayList<>();

        Map<String, Double> smap = sortMapByValue(map);
        for (Map.Entry<String, Double> entry : smap.entrySet()) {
            result.add(entry.getKey());
        }

        return result;
    }

    public static List<String> keyListSortedValueDesc(Map<String, Double> map) {
        List<String> result = new ArrayList<>();

        Map<String, Double> smap = sortMapByValue(map);
        for (Map.Entry<String, Double> entry : smap.entrySet()) {
            result.add(0, entry.getKey());
        }

        return result;
    }

    /**
     * 使用 Map按value进行排序
     *
     * @return
     */
    public static Map<String, Double> sortMapByValue(Map<String, Double> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return oriMap;
        }

        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        List<Map.Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>(oriMap.entrySet());
        Collections.sort(entryList, (me1, me2) -> {
            if (me1.getValue() > me2.getValue()) {
                return 1;
            } else if (me1.getValue() < me2.getValue()) {
                return -1;
            } else {
                return 0;
            }
        });

        Iterator<Map.Entry<String, Double>> iter = entryList.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Double> tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }

        return sortedMap;
    }
}
