package my_android.e.mad_practicle;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class music_player extends AppCompatActivity {
    ImageButton music_action;
    TextView music_name,c_time,e_time;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;


    public void onCreate(Bundle savedInstanceState) {
        mediaPlayer = MediaPlayer.create(this, R.raw.song);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        music_name= findViewById(R.id.tv_music_name);

        music_action= findViewById((R.id.btn_music_action));
        seekBar= findViewById((R.id.seekBar));
        c_time=findViewById(R.id.tv_current_time);
        //String s= Integer.toString(mediaPlayer.getDuration());
        e_time=findViewById(R.id.tv_end_time);

        //e_time.setText(Integer.toString(mediaPlayer.getDuration()));
        int end_time = mediaPlayer.getDuration();
        e_time.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) end_time),
                TimeUnit.MILLISECONDS.toSeconds((long) end_time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                end_time)))
        );

        music_name.setText(mediaPlayer.toString());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());

            }
        });

    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser)
            {
                mediaPlayer.seekTo(progress);
                seekBar.setProgress(progress);
                int current_time = mediaPlayer.getCurrentPosition();
                c_time.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) current_time),
                        TimeUnit.MILLISECONDS.toSeconds((long) current_time) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        current_time)))
                );

                //c_time.setText(Integer.toString(mediaPlayer.getCurrentPosition()));

            }
        }

        public void run() {

            int currentPosition = mediaPlayer.getCurrentPosition();
            int total = mediaPlayer.getDuration();


            while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPosition < total) {
                try {
                    Thread.sleep(1000);
                    currentPosition = mediaPlayer.getCurrentPosition();
                } catch (InterruptedException e) {
                    return;
                } catch (Exception e) {
                    return;
                }

                seekBar.setProgress(currentPosition);

            }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    });
    }


    public void music_actions(View v)
    {
        try {
            if (mediaPlayer.isPlaying()) {
                music_action.setImageResource(R.drawable.pause);
                mediaPlayer.pause();
                Toast.makeText(this, "Music is Pause", Toast.LENGTH_SHORT).show();

            } else {
                music_action.setImageResource(R.drawable.play2);
                mediaPlayer.start();

                Toast.makeText(this, "Music is playing", Toast.LENGTH_SHORT).show();

            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }

}
