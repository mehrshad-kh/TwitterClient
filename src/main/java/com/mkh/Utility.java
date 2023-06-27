package com.mkh;

import java.util.ArrayList;
import java.util.Iterator;

public class Utility {
    public static class Collections {
        public static <T> ArrayList<T> convert(Iterator<T> iterator) {
            ArrayList<T> arrayList = new ArrayList<>();
            iterator.forEachRemaining(arrayList::add);

            return arrayList;
        }
    }
}
