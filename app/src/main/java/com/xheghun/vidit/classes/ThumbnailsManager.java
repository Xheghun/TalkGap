package com.xheghun.vidit.classes;

import android.content.Context;
import android.graphics.Bitmap;

import com.xheghun.vidit.R;
import com.xheghun.vidit.models.MyFilters;

import java.util.ArrayList;
import java.util.List;

public class ThumbnailsManager {
    private static List<MyFilters> filters;
    private static List<MyFilters> processed;

    private ThumbnailsManager() {

    }

    public static List<MyFilters> processThumbs(Context context) {
        filters = new ArrayList<>(10);
        processed = new ArrayList<>(10);
        for (MyFilters filter: filters) {
            float size = context.getResources().getDimension(R.dimen.thumbnail_size);
            filter.setImage(Bitmap.createScaledBitmap(filter.getImage(),(int) size,(int) size, false ));
            filter.setImage(filter.getFilter().processFilter(filter.getImage()));

            //crop circle
            processed.add(filter);
        }
        return processed;
    }
    public static void addThumb(MyFilters filter) {
        filters.add(filter);
    }
    public static void clearThumbs() {
        filters = new ArrayList<>();
        processed = new ArrayList<>();
    }
}
