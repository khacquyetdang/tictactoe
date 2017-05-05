package khacquyetdang.android.com.tictactoe;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private int activePlayer = 0;
    private boolean gameIsActive = true;
    private LinearLayout playAgainLayout;
    private int[] buttonsId = {R.id.imageButton1,
            R.id.imageButton2,
            R.id.imageButton3,
            R.id.imageButton4,
            R.id.imageButton5,
            R.id.imageButton6,
            R.id.imageButton7,
            R.id.imageButton8,
            R.id.imageButton9};
    private Map<String, Integer> gameStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameStates = new HashMap<>();
        for (int btnId : buttonsId) {
            View btnView = findViewById(btnId);
            setView(btnView, R.color.colorGrayClear);
            setViewSize(btnView);
            btnView.setOnClickListener(this);
        }
        Button playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });
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
                if (gameStates.get(colIJ) != null &&
                        gameStates.get(colIJP1) != null &&
                        gameStates.get(colIJP2) != null &&
                        gameStates.get(colIJ) == gameStates.get(colIJP1) &&
                        gameStates.get(colIJP1) == gameStates.get(colIJP2))
                {
                    return gameStates.get(colIJ);
                }
                String colIP1J = "" + (i  + 1) + "" + j;
                String colIP2J = "" + (i + 2) + "" + (j);
                if (gameStates.get(colIJ) != null &&
                        gameStates.get(colIP2J) != null &&
                        gameStates.get(colIP1J) != null &&
                        gameStates.get(colIJ) == gameStates.get(colIP1J) &&
                        gameStates.get(colIP1J) == gameStates.get(colIP2J))
                {
                    return gameStates.get(colIJ);
                }


                String colIP1JP1 = "" + (i  + 1) + "" + (j + 1);
                String colIP2JP2 = "" + (i + 2) + "" + (j + 2);
                if (gameStates.get(colIJ) != null &&
                        gameStates.get(colIP1JP1) != null &&
                        gameStates.get(colIP2JP2) != null &&
                        gameStates.get(colIJ) == gameStates.get(colIP1JP1) &&
                        gameStates.get(colIP1JP1) == gameStates.get(colIP2JP2))
                {
                    return gameStates.get(colIJ);
                }

                String colIP1JM1 = "" + (i  + 1) + "" + (j - 1);
                String colIP2JM2 = "" + (i + 2) + "" + (j - 2);
                if (gameStates.get(colIJ) != null &&
                        gameStates.get(colIP1JM1) != null &&
                        gameStates.get(colIP2JM2) != null &&
                        gameStates.get(colIJ) == gameStates.get(colIP1JM1) &&
                        gameStates.get(colIP1JM1) == gameStates.get(colIP2JM2))
                {
                    return gameStates.get(colIJ);
                }
            }
        }

        return 2;
    }

    private void playAgain()
    {
        gameIsActive = true;
        activePlayer = 0;
        playAgainLayout.setVisibility(View.GONE);
        for (int i = 0;  i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String colIJ = "" + i + "" + j;
                gameStates.put(colIJ, null);
            }
        }
        for (int btnId : buttonsId)
        {
            View view = findViewById(btnId);
            if (view != null)
            {
                view.animate().rotation(360).setDuration(500);
                setView(view, R.color.colorGrayClear);
            }
        }

    }
    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        if (gameStates.get(tag) != null || gameIsActive == false)
        {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            view.startAnimation(shake);
            return ;
        }
        gameStates.put(tag, activePlayer);
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
            gameIsActive = false;
            Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);

            CharSequence text = "Player "  + winner + " wins the games !";
            playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
            TextView playAgainTextView = (TextView) findViewById(R.id.playAgainTextView);
            playAgainTextView.setText(text);
            playAgainLayout.setAlpha(1f);
            playAgainLayout.startAnimation(slideUp);
            playAgainLayout.setVisibility(View.VISIBLE);
        }
    }
}
