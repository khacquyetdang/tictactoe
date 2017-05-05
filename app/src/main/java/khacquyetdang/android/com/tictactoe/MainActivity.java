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
import android.widget.Toast;

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
    private int checkWinner()
    {
        for (int i = 0;  i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                String colIJ = "" + i + "" + j;
                String colIJP1 = "" + i + "" + (j + 1);
                String colIJP2 = "" + i + "" + (j + 2);
                if (boardGames.get(colIJ) != null &&
                        boardGames.get(colIJP1) != null &&
                        boardGames.get(colIJP2) != null &&
                        boardGames.get(colIJ) == boardGames.get(colIJP1) &&
                        boardGames.get(colIJP1) == boardGames.get(colIJP2))
                {
                    return boardGames.get(colIJ);
                }
                String colIP1J = "" + (i  + 1) + "" + j;
                String colIP2J = "" + (i + 2) + "" + (j);
                if (boardGames.get(colIJ) != null &&
                        boardGames.get(colIP2J) != null &&
                        boardGames.get(colIP1J) != null &&
                        boardGames.get(colIJ) == boardGames.get(colIP1J) &&
                        boardGames.get(colIP1J) == boardGames.get(colIP2J))
                {
                    return boardGames.get(colIJ);
                }


                String colIP1JP1 = "" + (i  + 1) + "" + (j + 1);
                String colIP2JP2 = "" + (i + 2) + "" + (j + 2);
                if (boardGames.get(colIJ) != null &&
                        boardGames.get(colIP1JP1) != null &&
                        boardGames.get(colIP2JP2) != null &&
                        boardGames.get(colIJ) == boardGames.get(colIP1JP1) &&
                        boardGames.get(colIP1JP1) == boardGames.get(colIP2JP2))
                {
                    return boardGames.get(colIJ);
                }

                String colIP1JM1 = "" + (i  + 1) + "" + (j - 1);
                String colIP2JM2 = "" + (i + 2) + "" + (j - 2);
                if (boardGames.get(colIJ) != null &&
                        boardGames.get(colIP1JM1) != null &&
                        boardGames.get(colIP2JM2) != null &&
                        boardGames.get(colIJ) == boardGames.get(colIP1JM1) &&
                        boardGames.get(colIP1JM1) == boardGames.get(colIP2JM2))
                {
                    return boardGames.get(colIJ);
                }
            }
        }

        return 2;
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
        int winner = checkWinner();
        if (winner == 1 || winner == 0)
        {

            CharSequence text = "Player "  + winner + " wins the games !";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
    }
}
