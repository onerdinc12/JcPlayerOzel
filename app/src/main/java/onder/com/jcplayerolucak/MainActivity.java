package onder.com.jcplayerolucak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.jean.jcplayer.JcAudio;
import com.example.jean.jcplayer.JcPlayerService;
import com.example.jean.jcplayer.JcPlayerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements JcPlayerService.OnInvalidPathListener {
private LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
private JcPlayerView player;
private RecyclerView recyclerView;
private AudioAdapter audioAdapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        player = (JcPlayerView) findViewById(R.id.jcplayer);

        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromURL("url audio","http://formaal.net/sarki.mp3"));

        player.initPlaylist(jcAudios);




        player.registerInvalidPathListener(this);
        adapterSetup();
        }


public void playAudio(JcAudio jcAudio){
        player.playAudio(jcAudio);
        Toast.makeText(this, player.getCurrentAudio().getOrigin().toString(), Toast.LENGTH_SHORT).show();
        }

protected void adapterSetup() {
        audioAdapter = new AudioAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(audioAdapter);
        audioAdapter.setupItems(player.getMyPlaylist());
        }

@Override
public void onPause(){
        super.onPause();
        player.createNotification();
        }

@Override
protected void onDestroy() {
        super.onDestroy();
        player.kill();
        }

@Override
public void onPathError(JcAudio jcAudio) {
        Toast.makeText(this, jcAudio.getPath() + " with problems", Toast.LENGTH_LONG).show();

        }
        }