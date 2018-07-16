package com.example.fivechess.gamemode.fight_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fivechess.R;
import com.example.fivechess.chessgame.Game;
import com.example.fivechess.chessgame.GameAI;
import com.example.fivechess.chessview.ChessView;

public class FightGameActivity extends AppCompatActivity {

    private ChessView chessView;
    private Game game;
    private Button rollbackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_game);
        chessView = (ChessView)findViewById(R.id.chess_view);
        rollbackBtn = (Button) findViewById(R.id.btn_rollback);

        game = new Game();
        chessView.setGame(game);
    }

}
