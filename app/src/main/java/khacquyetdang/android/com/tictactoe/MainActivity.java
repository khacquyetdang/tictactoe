package khacquyetdang.android.com.tictactoe;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private int activePlayer = 0;

    private int[] buttonsId = {R.id.imageButton1,
            R.id.imageButton2,
            R.id.imageButton3,
            R.id.imageButton4,
            R.id.imageButton5,
            R.id.imageButton6,
            R.id.imageButton7,
            R.id.imageButton8,
            R.id.imageButton9};
    private Map<String, Integer> boardGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardGames = new HashMap<>();
        for (int btnId : buttonsId) {
            View btnView = findViewById(btnId);
            setView(btnView, R.color.colorGrayClear);
            setViewSize(btnView);
            btnView.setOnClickListener(this);
        }
    }

    public void setViewSize(View view)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        view.getLayoutParams().width = width / 4 ;
        view.getLayoutParams().height = width / 4;
    }
    public void setView(View view, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setBackgroundColor(getResources().getColor(color,
                    getTheme()));
        }
        else {
            view.setBackgroundColor(getResources().getColor(color));
        }
    }
    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        if (boardGames.get(tag) != null)
        {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            view.startAnimation(shake);
            return ;
        }
        boardGames.put(tag, activePlayer);
        if (view instanceof Button) {
            if (activePlayer == 0) {
                activePlayer = 1;
                setView(view, R.color.colorPlayer0);
            } else {
                activePlayer = 0;
                setView(view, R.color.colorPlayer1);
            }
            view.setTranslationY(-1000f);
            //view.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorBlue));
            view.animate().translationYBy(1000f).rotation(720).setDuration(500);
        }
    }
}
