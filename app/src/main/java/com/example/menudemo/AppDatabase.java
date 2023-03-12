package com.example.menudemo;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {PaintingEntity.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "painting_gallery.db").setJournalMode(JournalMode.TRUNCATE).allowMainThreadQueries().build();
            PaintingDao paintingDao = INSTANCE.getPaintingDao();
            List<PaintingEntity> list = paintingDao.getPaintings();
            if (list.size() <= 0) {
                getListOfPainting().forEach(paintingDao::insert);
            }
        }
        return INSTANCE;
    }
    private static List<PaintingEntity> getListOfPainting() {
        List<PaintingEntity> list = new ArrayList<>();
        list.add(new PaintingEntity(1L, new Painting("The Starry Night", "Vicent van Gogh", "June 1889", R.mipmap.starry_night)));
        list.add(new PaintingEntity(2L, new Painting("Impression, Sunrise", "Claude Monet", "1872", R.mipmap.impression_sunrise)));
        list.add(new PaintingEntity(3L, new Painting("The Magpie", "Claude Monet", "1869", R.mipmap.the_magpie)));
        list.add(new PaintingEntity(4L, new Painting("Woman with a Parasol – Madame Monet and Her Son", "Claude Monet", "1975", R.mipmap.woman_with_parasol)));
        list.add(new PaintingEntity(5L, new Painting("The Great Wave off Kanagawa", "Hokusai", "1831", R.mipmap.tsunami)));
        list.add(new PaintingEntity(6L, new Painting("Liberty Leading the People", "Eugène Delacroix", "1830", R.mipmap.delacroix)));
        return list;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract PaintingDao getPaintingDao();
}
