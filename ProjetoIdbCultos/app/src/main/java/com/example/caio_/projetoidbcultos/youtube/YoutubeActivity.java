package com.example.caio_.projetoidbcultos.youtube;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caio_.projetoidbcultos.R;
import com.example.caio_.projetoidbcultos.model.Culto;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    public static final String YOUTUBE_PARAM = "youtube_param";
    public static final String NOME_YOUTUBE_PARAM = "nome_youtube_param";
    public static final String NOME_YOUTUBE_PARAM_STATE = "nome_youtube_param_state";

    @BindView(R.id.youtube_view)
    YouTubePlayerView youTubeView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textNomeOriginalYoutube)
    TextView txtNomeOriginalYoutube;

    Culto mCulto;
    YouTubePlayer youTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        ButterKnife.bind(this);
        mCulto = new Culto();
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        toolbar.setTitle(getResources().getString(R.string.transmissao_culto));
        toolbar.setTitleTextColor(Color.WHITE);

        if (savedInstanceState != null) {
            txtNomeOriginalYoutube.setText(savedInstanceState.getString(NOME_YOUTUBE_PARAM_STATE));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NOME_YOUTUBE_PARAM_STATE, txtNomeOriginalYoutube.getText().toString());
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        this.youTubePlayer = youTubePlayer;

        if (!b) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            String paramYoutube = bundle.getString(YOUTUBE_PARAM);
            String paramNomeYoutube = bundle.getString(NOME_YOUTUBE_PARAM);
            mCulto.setLinkYoutube(paramYoutube);
            mCulto.setNomeOriginalYoutube(paramNomeYoutube);
            youTubePlayer.loadVideo(mCulto.getLinkYoutube());
            txtNomeOriginalYoutube.setText(mCulto.getNomeOriginalYoutube());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = getString(R.string.player_error);
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.string.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
