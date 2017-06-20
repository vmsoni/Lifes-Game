package lifesgame.tapstudios.ca.lifesgame;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

/**
 * Created by Vidit Soni on 6/3/2017.
 */
public class GameMechanicsHelper {
    private TextView charHealth;
    private TextView charXp;
    private TextView charLevel;
    private int maxHealth;
    private int maxXp;
    private int currentXp;
    private int currentLvl;
    private int currentHealth;
    private DatabaseHelper databaseHelper;
    private RoundCornerProgressBar healthBar;
    private RoundCornerProgressBar xpBar;

    public GameMechanicsHelper(TextView charHealth, TextView charXp, TextView charLevel, DatabaseHelper databaseHelper, RoundCornerProgressBar healthBar, RoundCornerProgressBar xpBar) {
        this.charHealth = charHealth;
        this.charXp = charXp;
        this.charLevel = charLevel;
        this.databaseHelper = databaseHelper;
        this.maxHealth = 100;
        this.currentHealth = 100;
        this.currentXp = 0;
        this.currentLvl = 1;
        this.maxXp = 1000;
        this.healthBar = healthBar;
        this.xpBar = xpBar;
    }

    public void setUpGameTextViews() {
        try {
            currentHealth = Integer.parseInt(databaseHelper.getValue(databaseHelper.CHAR_HEALTH));
            maxHealth = Integer.parseInt(databaseHelper.getValue(databaseHelper.CHAR_MAX_HEALTH));
            currentXp = Integer.parseInt(databaseHelper.getValue(databaseHelper.CHAR_XP));
            maxXp = Integer.parseInt(databaseHelper.getValue(databaseHelper.CHAR_MAX_XP));
            currentLvl = Integer.parseInt(databaseHelper.getValue(databaseHelper.CHAR_LVL));
            xpBar.setMax(maxXp);
            xpBar.setProgress(currentXp);
            healthBar.setProgress(currentHealth);
            healthBar.setMax(maxHealth);
        }
        catch(Exception e) {
            databaseHelper.initiateKeys();
            Log.d("Error", "Error asserted: " + e);
        }
        charHealth.setText(Integer.toString(currentHealth) + "/" + Integer.toString(maxHealth));
        charXp.setText(Integer.toString(currentXp) + "/" + Integer.toString(maxXp));
        charLevel.setText(Integer.toString(currentLvl));
    }

    public void updateGameTextViews() {
        charHealth.setText(Integer.toString(currentHealth) + "/" + Integer.toString(maxHealth));
        charXp.setText(Integer.toString(currentXp) + "/" + Integer.toString(maxXp));
        charLevel.setText(Integer.toString(currentLvl));
    }

    public void removeHealth() {
        currentHealth -= 10;
        databaseHelper.updateValue(databaseHelper.CHAR_HEALTH, Integer.toString(currentHealth));
        charHealth.setText(Integer.toString(currentHealth) + "/" + Integer.toString(maxHealth));
        healthBar.setProgress(currentHealth);
    }

    public void addHealth() {
        currentHealth += 10;
        databaseHelper.updateValue(databaseHelper.CHAR_HEALTH, Integer.toString(currentHealth));
        charHealth.setText(Integer.toString(currentHealth) + "/" + Integer.toString(maxHealth));
        healthBar.setProgress(currentHealth);
    }

    public void addXp() {
        currentXp += 50;
        xpBar.setProgress(currentXp);
        if (currentXp == maxXp) {
            maxXp = maxXp * 2;
            currentXp = 0;
            xpBar.setMax(maxXp);
            xpBar.setProgress(currentXp);
            maxHealth += 100;
            currentHealth = maxHealth;
            healthBar.setMax(maxHealth);
            healthBar.setProgress(currentHealth);
            addLevel();
            updateGameTextViews();
            databaseHelper.updateValue(databaseHelper.CHAR_MAX_HEALTH, Integer.toString(maxHealth));
            databaseHelper.updateValue(databaseHelper.CHAR_MAX_XP, Integer.toString(maxXp));
            databaseHelper.updateValue(databaseHelper.CHAR_HEALTH, Integer.toString(currentHealth));
        }
        databaseHelper.updateValue(databaseHelper.CHAR_XP, Integer.toString(currentXp));
        charXp.setText(Integer.toString(currentXp) + "/" + Integer.toString(maxXp));
    }

    public void addLevel() {
        currentLvl += 1;
        databaseHelper.updateValue(databaseHelper.CHAR_LVL, Integer.toString(currentLvl));
        charLevel.setText(Integer.toString(currentLvl));
    }
}
