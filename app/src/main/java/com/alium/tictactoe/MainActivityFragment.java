package com.alium.tictactoe;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private ImageView mImageView0_0, mImageView0_1, mImageView0_2, mImageView1_0, mImageView1_1, mImageView1_2, mImageView2_0, mImageView2_1, mImageView2_2;
    private int mActivePlayer = 0; // 0 f0r red , 1 for yellow
    private int[] mGameStates = {2, 2, 2, 2, 2, 2, 2, 2, 2}; //Games states for each tile with 2 meaning untouched 0 and 1 for each individual player
    private int[][] mWinningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {2, 4, 6}, {0, 4, 8}};
    private LinearLayout mPlayAgainLay;
    private TextView mWinnerMessgae, mGameNumberTV, mBlueSocreTV, mRedScoreTV, mTurnTV;
    private GridLayout mBoardGridLayout;
    private Button mPlayagainButton;
    private boolean mActive = true;
    private int mBlueSocre, mRedScore, mGameNumber;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        mImageView0_0 = (ImageView) view.findViewById(R.id.image_0_0);
        mImageView0_1 = (ImageView) view.findViewById(R.id.image_0_1);
        mImageView0_2 = (ImageView) view.findViewById(R.id.image_0_2);
        mImageView1_0 = (ImageView) view.findViewById(R.id.image_1_0);
        mImageView1_1 = (ImageView) view.findViewById(R.id.image_1_1);
        mImageView1_2 = (ImageView) view.findViewById(R.id.image_1_2);
        mImageView2_0 = (ImageView) view.findViewById(R.id.image_2_0);
        mImageView2_1 = (ImageView) view.findViewById(R.id.image_2_1);
        mImageView2_2 = (ImageView) view.findViewById(R.id.image_2_2);

        mPlayAgainLay = (LinearLayout) view.findViewById(R.id.play_again_place);
        mWinnerMessgae = (TextView) view.findViewById(R.id.winner_message);
        mBoardGridLayout = (GridLayout) view.findViewById(R.id.board_grid);
        mPlayagainButton = (Button) view.findViewById(R.id.play_again_button);

        mGameNumberTV = (TextView) view.findViewById(R.id.number_of_games_tv);
        mBlueSocreTV = (TextView) view.findViewById(R.id.blue_score_tv);
        mRedScoreTV = (TextView) view.findViewById(R.id.red_score_tv);
/*
        mTurnTV = (TextView) getActivity().getSupp().;
*/


        mImageView0_0.setOnClickListener(this);
        mImageView0_1.setOnClickListener(this);
        mImageView0_2.setOnClickListener(this);
        mImageView1_0.setOnClickListener(this);
        mImageView1_1.setOnClickListener(this);
        mImageView1_2.setOnClickListener(this);
        mImageView2_0.setOnClickListener(this);
        mImageView2_1.setOnClickListener(this);
        mPlayagainButton.setOnClickListener(this);
        mImageView2_2.setOnClickListener(this);


        return view;
    }

    public void dropIn(View view) {
        ImageView pinCounter = (ImageView) view;
        int tag = Integer.parseInt(pinCounter.getTag().toString());
        if (mGameStates[tag] == 2 && mActive) {
            mGameStates[tag] = mActivePlayer;
            pinCounter.setTranslationY(-1000f);
            if (mActivePlayer == 0) {
                pinCounter.setImageDrawable(getResources().getDrawable(R.drawable.square_reddish));
                mActivePlayer = 1;
            } else {
                pinCounter.setImageDrawable(getResources().getDrawable(R.drawable.circle_blue));
                mActivePlayer = 0;
            }
            pinCounter.animate().translationYBy(1000f).setDuration(350);
        } else {
            Toast.makeText(getContext(), "You have played here", Toast.LENGTH_SHORT).show();
        }

        for (int[] winningState : mWinningPositions) {
            if (mGameStates[winningState[0]] == mGameStates[winningState[1]] && mGameStates[winningState[1]] == mGameStates[winningState[2]] && mGameStates[winningState[0]] != 2) {
                Toast.makeText(getContext(), "Player " + mGameStates[winningState[0]] + " has won!", Toast.LENGTH_LONG).show();
                String winner;
                mGameNumber += 1;
                if (mGameNumber < 10) {
                    mGameNumberTV.setText("" + mGameNumber);
                } else {
                    mGameNumberTV.setText(mGameNumber);
                }
                if (mGameStates[winningState[0]] == 0) {
                    winner = "Red";
                    mRedScore += 1;
                    if (mRedScore < 10) {
                        mRedScoreTV.setText("0" + mRedScore);
                    } else {
                        mRedScoreTV.setText("" + mRedScore);
                    }
                } else {
                    winner = "Blue";
                    mBlueSocre += 1;
                    if (mBlueSocre < 10) {
                        mBlueSocreTV.setText("0" + mBlueSocre);
                    } else {
                        mBlueSocreTV.setText("" + mBlueSocre);
                    }
                }
                mActive = false;
                mWinnerMessgae.setText(winner + " has won!");
                apperLayout(mPlayAgainLay);
            }
        }
    }

    public void playAgain() {
        removeLayout(mPlayAgainLay);
        for (int i = 0; i < mGameStates.length; i++) {
            mGameStates[i] = 2;
        }
        mActive = true;

        for (int i = 0; i < mBoardGridLayout.getChildCount(); i++) {
            ((ImageView) mBoardGridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    public void apperLayout(LinearLayout layout) {
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);

        if (layout.getVisibility() == View.GONE) {

            layout.startAnimation(slideUp);
            layout.setVisibility(View.VISIBLE);
        }
    }

    public void removeLayout(LinearLayout layout) {
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);

        if (layout.getVisibility() == View.VISIBLE) {

            layout.startAnimation(slideDown);
            layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof ImageView) {
            dropIn(v);
        } else if (v.getId() == R.id.play_again_button) {
            removeLayout(mPlayAgainLay);
            playAgain();
        }
    }
}
