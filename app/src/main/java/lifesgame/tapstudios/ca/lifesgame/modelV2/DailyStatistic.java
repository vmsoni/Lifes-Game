package lifesgame.tapstudios.ca.lifesgame.modelV2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import lifesgame.tapstudios.ca.lifesgame.modelV2.converter.DateTypeConverter;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by viditsoni on 2019-03-06.
 */

@Entity
@TypeConverters(DateTypeConverter.class)
public class DailyStatistic {

    @PrimaryKey(autoGenerate = true)
    public long dailyStatisticId;

    @ForeignKey(onDelete = CASCADE, entity = DailyTodo.class, parentColumns = "DailyId" , childColumns = "DailyId")
    public long dailyId;

    public Date actionDate;

    public boolean successful;
}
