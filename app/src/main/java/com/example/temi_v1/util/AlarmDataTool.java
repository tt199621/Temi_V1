package com.example.temi_v1.util;

import com.example.temi_v1.app.AppApplication;
import com.example.temi_v1.bean.AlarmItem;
import com.example.temi_v1.data.MyAlarmDataBase;
import com.example.temi_v1.model.AlarmModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Shinelon-LJL on 2019/9/22
 */
public class AlarmDataTool {
    public static MyAlarmDataBase db = new MyAlarmDataBase(AppApplication.getInstance());
    public static List<AlarmItem> loadData() {

        ArrayList<AlarmItem> items = new ArrayList<>();
        List<AlarmModel> am = db.getAllAlarms();
        List<String> Titles = new ArrayList<>();
        List<String> Repeat = new ArrayList<>();
        List<String> RepeatCode = new ArrayList<>();
        List<String> Actives = new ArrayList<>();
        List<String> Time = new ArrayList<>();
        List<String> TypeStr = new ArrayList<>();
        List<Integer> IDList = new ArrayList<>();
        List<MyTimeSorter> TimeSortList = new ArrayList<>();
        List<String> WakeType = new ArrayList<>();
        List<String> Ring = new ArrayList<>();

        for (AlarmModel a : am) {

            WakeType.add(a.getWakeType());
            Titles.add(a.getTitle());
            Time.add(a.getTime());
            TypeStr.add(a.getTypeStr());
            Ring.add(a.getRing());
            Repeat.add(a.getRepeatType());
            RepeatCode.add(a.getRepeatCode());
            Actives.add(a.getActive());
            IDList.add(a.getID());
        }

        int key = 0;
        for (int k = 0; k < Titles.size(); k++) {
            TimeSortList.add(new MyTimeSorter(key, Time.get(k)));
            key++;
        }

        Collections.sort(TimeSortList, new TimeComparator());

        int k = 0;
        for (MyTimeSorter item : TimeSortList) {

            int i = item.getIndex();
            items.add(new AlarmItem(Titles.get(i), Time.get(i),TypeStr.get(i), Repeat.get(i), RepeatCode.get(i), Actives.get(i),WakeType.get(i),Ring.get(i),IDList.get(i)));
            IDmap.put(k, IDList.get(i));
            k++;
        }
        return items;
    }
    private static LinkedHashMap<Integer, Integer> IDmap = new LinkedHashMap<>();
    private static class TimeComparator implements Comparator {
        DateFormat f = new SimpleDateFormat("hh:mm");

        public int compare(Object a, Object b) {
            String o1 = ((MyTimeSorter) a).getTime();

            String o2 = ((MyTimeSorter) b).getTime();

            try {
                return f.parse(o1).compareTo(f.parse(o2));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
