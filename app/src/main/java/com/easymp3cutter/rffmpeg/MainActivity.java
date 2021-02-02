package com.easymp3cutter.rffmpeg;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.inputmethodservice.ExtractEditText;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.google.android.exoplayer2.C;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.io.File;
import java.security.Permission;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class MainActivity extends AppCompatActivity {

    private ImageButton CropVideo,FadeInFadeOut,RotateVideo90,RotateVideo180,VintageFilter,BlackWhite,VFlipVideo,HFlipVideo;
    private Button selectVideo,selectAudio;
    private TextView tvLeft,tvRight;
    private ProgressDialog progressDialog;
    private int duration;
    private String video_url;
    private String image_url;
    private VideoView videoView;
    private ImageView imageView;
    private static final int SELECT_AUDIO = 1;

    private Runnable r;
    private RangeSeekBar rangeSeekBar;
    private static final String root= Environment.getExternalStorageDirectory().toString();
    private static final String app_folder=root+"/DCIM/";

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this,"On_create_Method_Called",Toast.LENGTH_SHORT).show();

        rangeSeekBar = (RangeSeekBar) findViewById(R.id.rangeSeekBar);
        tvLeft = (TextView) findViewById(R.id.textleft);
        tvRight = (TextView) findViewById(R.id.textright);

        selectVideo = (Button) findViewById(R.id.select);


        CropVideo = (ImageButton) findViewById(R.id.cropVideo);
        FadeInFadeOut = (ImageButton) findViewById(R.id.fadeinout);
        RotateVideo90 = (ImageButton) findViewById(R.id.rotate);
        RotateVideo180 = (ImageButton) findViewById(R.id.rotatett);
        VintageFilter = (ImageButton) findViewById(R.id.vintage);
        BlackWhite = (ImageButton) findViewById(R.id.blackwhite);
        VFlipVideo = (ImageButton) findViewById(R.id.vflip);
        HFlipVideo = (ImageButton) findViewById(R.id.hflip);
        videoView=(VideoView) findViewById(R.id.layout_movie_wrapper);
        imageView=(ImageView) findViewById(R.id.overlayimage);

        Toast.makeText(MainActivity.this,"DialogBox_Called",Toast.LENGTH_SHORT).show();

        //creating the progress dialog
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        //set up the onClickListeners
        selectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an intent to retrieve the video file from the device storage
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                intent.setType("video/*");
                startActivityForResult(intent, 123);
            }
        });


        CropVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_url != null)
                {
                    try {
                        CropVideo(rangeSeekBar.getSelectedMinValue().intValue() * 1000,rangeSeekBar.getSelectedMaxValue().intValue() * 1000);

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT);
                    }


                }else
                    Toast.makeText(MainActivity.this,"Please_Upload_Video",Toast.LENGTH_SHORT).show();
            }
        });

        FadeInFadeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_url != null)
                {
                    try {


                        FadeInFadeOut(rangeSeekBar.getSelectedMinValue().intValue() * 1000, rangeSeekBar.getSelectedMaxValue().intValue() * 1000);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT);
                    }
                }else
                    Toast.makeText(MainActivity.this, "Please_Upload_Video", Toast.LENGTH_SHORT).show();
            }
        });

        RotateVideo90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_url != null)
                {
                    try {
                        RotateVideo90(rangeSeekBar.getSelectedMinValue().intValue() * 1000,rangeSeekBar.getSelectedMaxValue().intValue() * 1000);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_SHORT);
                    }

                }else
                    Toast.makeText(MainActivity.this,"Please_Upload_Video",Toast.LENGTH_SHORT).show();
            }
        });

        RotateVideo180.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_url != null)
                {
                    try {
                        RotateVideo180(rangeSeekBar.getSelectedMinValue().intValue() * 1000,rangeSeekBar.getSelectedMaxValue().intValue() * 1000);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,e.toString(), Toast.LENGTH_SHORT);
                    }
                }else
                    Toast.makeText(MainActivity.this,"Please_Upload_Video", Toast.LENGTH_SHORT).show();
            }
        });

        VintageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_url != null)
                {
                    try {
                        VintageFilter(rangeSeekBar.getSelectedMinValue().intValue() * 1000,rangeSeekBar.getSelectedMaxValue().intValue() * 1000);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT);
                    }
                }else
                    Toast.makeText(MainActivity.this,"Please_Upload_Video",Toast.LENGTH_SHORT).show();
            }
        });

        BlackWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_url != null)
                {
                    try {
                        BlackWhite(rangeSeekBar.getSelectedMinValue().intValue() * 1000,rangeSeekBar.getSelectedMaxValue().intValue() * 1000);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT);
                    }
                }else
                    Toast.makeText(MainActivity.this, "Please_Uplaod_Video",Toast.LENGTH_SHORT).show();
            }
        });

        VFlipVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_url != null)
                {
                    try {
                        VFlipVideo(rangeSeekBar.getSelectedMinValue().intValue() * 1000,rangeSeekBar.getSelectedMaxValue().intValue() * 1000);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT);
                    }

                }else
                    Toast.makeText(MainActivity.this,"Please_Upload_Video",Toast.LENGTH_SHORT).show();
            }
        });

        HFlipVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_url != null)
                {
                    try {
                        HFlipVideo(rangeSeekBar.getSelectedMinValue().intValue() * 1000,rangeSeekBar.getSelectedMaxValue().intValue() * 1000);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT);
                    }
                }else
                    Toast.makeText(MainActivity.this, "Please_Upload_Video", Toast.LENGTH_SHORT).show();
            }
        });


        /*
            set up the VideoView.
            We will be using VideoView to view our video.
         */
        //     Toast.makeText(MainActivity.this,"Video_view_Method_Called",Toast.LENGTH_SHORT).show();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {

                duration = mp.getDuration() / 1000;

                tvLeft.setText("00:00:00");

                tvRight.setText(getTime(mp.getDuration() / 1000));


                mp.setLooping(true);

                //set up the initial values of rangeSeekbar
                rangeSeekBar.setRangeValues(0, duration);
                rangeSeekBar.setSelectedMinValue(0);
                rangeSeekBar.setSelectedMaxValue(duration);
                rangeSeekBar.setEnabled(true);

                //         Toast.makeText(MainActivity.this,"Rangeseekbar_Called",Toast.LENGTH_SHORT).show();

                rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
                    @Override
                    public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                        videoView.seekTo((int) minValue * 1000);

                        tvLeft.setText(getTime((int) bar.getSelectedMinValue()));
                        tvRight.setText(getTime((int) bar.getSelectedMaxValue()));

                    }
                });


                Toast.makeText(MainActivity.this,"Handler_Called",Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(r = new Runnable() {
                    @Override
                    public void run() {

                        if (videoView.getCurrentPosition() >= rangeSeekBar.getSelectedMaxValue().intValue() * 1000)
                            videoView.seekTo(rangeSeekBar.getSelectedMinValue().intValue() * 1000);
                        handler.postDelayed(r, 1000);
                    }
                }, 1000);

            }
        });
    }


    private void CropVideo(int startMs, int endMs) throws Exception {

        progressDialog.show();
        final String filePath;
        String filePrefix = "CropVideo";
        String fileExtn = ".mp4";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/" + "Folder");
            contentValues.put(MediaStore.Video.Media.TITLE, filePrefix+System.currentTimeMillis());
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, filePrefix+System.currentTimeMillis() +fileExtn);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() /1000);
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
            Uri uri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);

            File file = FileUtils.getFileFromUri(this,uri);
            filePath = file.getAbsolutePath();
        }else
        {
            File dest = new File(new File(app_folder), filePrefix + fileExtn);
            int fileNo = 0;

            while (dest.exists())
            {
                fileNo++;
                dest = new File(new File(app_folder), filePrefix + fileNo + fileExtn);
            }

            filePath = dest.getAbsolutePath();
        }
        String cmd;

        cmd="-y -i " +video_url+" -vf crop=712:ih:284:0"+" -vcodec mpeg4 -crf 0 -preset superfast "+filePath;

      long executionId =  FFmpeg.executeAsync(cmd, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {

                if (returnCode == RETURN_CODE_SUCCESS) {
                    videoView.setVideoURI(Uri.parse(filePath));

                    video_url = filePath;

                    videoView.start();

                    progressDialog.dismiss();
                }else if (returnCode == RETURN_CODE_CANCEL)
                {
                    Log.i(ContentValues.TAG,"Async Command ExecutionCancel By User");
                }else
                {
                    Log.i(Config.TAG,String.format("Async Command Execution Cancel By returnCode=%d",returnCode));
                }
            }
        });

    }

    private void FadeInFadeOut(int startMs, int endMs) throws Exception {
        progressDialog.show();
        final  String filePath;
        String filePrefix = "FadeInFadeOut";
        String fileExtn = ".mp4";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/" + "Folder");
            contentValues.put(MediaStore.Video.Media.TITLE, filePrefix+System.currentTimeMillis());
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, filePrefix+System.currentTimeMillis() +fileExtn);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() /1000);
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());

            Uri uri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);

            File file = FileUtils.getFileFromUri(this,uri);
            filePath = file.getAbsolutePath();
        }else
        {
            File dest = new File(new File(app_folder), filePrefix + fileExtn);
            int fileNo = 0;

            while (dest.exists())
            {
                fileNo++;
                dest = new File(new File(app_folder), filePrefix + fileNo + fileExtn);
            }

            filePath = dest.getAbsolutePath();
        }

        String[] cmd = {"-y", "-i", video_url, "-acodec", "copy", "-vf", "fade=t=in:st=0:d=5,fade=t=out:st=" + (duration - 5) + ":d=5","-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", filePath};



        //  cmd="-y -i " +video_url+" -filter_complex[0:v]crop=400:400:300:350"+" boxblur=10[fg]"+"[0:v][fg]overlay=300:350[v]  "+"-b:v 2097k -b:a 128k -vcodec mpeg4 -crf 0 -preset superfast "+filePath;

        long executionId = FFmpeg.executeAsync(cmd, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                if (returnCode == RETURN_CODE_SUCCESS)
                {
                    videoView.setVideoURI(Uri.parse(filePath));
                    video_url = filePath;
                    videoView.start();
                    progressDialog.dismiss();
                }else if (returnCode == RETURN_CODE_CANCEL)
                {
                    Log.i(Config.TAG, "Async Command Execution Cancel By User");
                }else
                {
                    Log.i(Config.TAG,String.format("Async Command Execucution Cancel By returnCode=%d",returnCode));
                }

            }
        });
    }

    private void RotateVideo90(int startMs, int endMs) throws Exception{
        progressDialog.show();
        final String filePath;
        String filePrefix = "RotateVideo90";
        String fileExtn = ".mp4";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/" + "Folder");
            contentValues.put(MediaStore.Video.Media.TITLE, filePrefix+System.currentTimeMillis());
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, filePrefix+System.currentTimeMillis() +fileExtn);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
            Uri uri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);

            File file = FileUtils.getFileFromUri(this,uri);
            filePath = file.getAbsolutePath();
        }else
        {
            File dest = new File(new File(app_folder), filePrefix + fileExtn);
            int fileNo = 0;

            while (dest.exists())
            {
                fileNo++;
                dest = new File(new File(app_folder), filePrefix + fileNo + fileExtn);
            }
            filePath = dest.getAbsolutePath();
        }
        String cmd;

            cmd="-y -i " +video_url+" -vf transpose=1 -b:v 2097k -b:v 128k -ac 2 -ar 22050"+" -vcodec mpeg4 -crf 0 -preset superfast "+filePath;

        long execuionId = FFmpeg.executeAsync(cmd, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                if (returnCode == RETURN_CODE_SUCCESS)
                {
                    videoView.setVideoURI(Uri.parse(filePath));
                    video_url = filePath;
                    videoView.start();
                    progressDialog.dismiss();
                }else if (returnCode == RETURN_CODE_CANCEL)
                {
                    Log.i(Config.TAG,"Async Command Execution Cancel By User");
                }else
                {
                    Log.i(Config.TAG, String.format("Async Command Execution Cancel By returnCode=%d",returnCode));
                }
            }
        });


    }

    private void RotateVideo180(int startMs, int endMs) throws Exception {
        progressDialog.show();
        final String filePath;
        String filePrefix = "RotateVideo180";
        String fileExtn = ".mp4";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/" + "Folder");
            contentValues.put(MediaStore.Video.Media.TITLE, filePrefix+System.currentTimeMillis());
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, filePrefix+System.currentTimeMillis() +fileExtn);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
            Uri uri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,contentValues);

            File file = FileUtils.getFileFromUri(this,uri);
            filePath = file.getAbsolutePath();
        }else
        {
            File dest = new File(new File(app_folder), filePrefix + fileExtn);
            int fileNo = 0;

            while (dest.exists())
            {
                fileNo++;
                dest = new File(new File(app_folder), filePrefix + fileNo + fileExtn);
            }
            filePath = dest.getAbsolutePath();
        }
        String cmd;

        cmd="-y -i " +video_url+" -vf transpose=2,transpose=2 -b:v 2097k -b:a 128k -ac 2 -ar 22050"+" -vcodec mpeg4 -crf 0 -preset superfast "+filePath;

        long executionId = FFmpeg.executeAsync(cmd, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                if (returnCode == RETURN_CODE_SUCCESS)
                {
                    videoView.setVideoURI(Uri.parse(filePath));
                    video_url = filePath;
                    videoView.start();
                    progressDialog.dismiss();
                }else if (returnCode == RETURN_CODE_CANCEL)
                {
                    Log.i(Config.TAG,"Async Command Execution Cancel By User");
                }else
                {
                    Log.i(Config.TAG,String.format("Async Command Cancel By returnCode=%d",returnCode));
                }
            }
        });
    }

    private void VintageFilter(int startMs, int endMs) throws Exception {
        progressDialog.show();
        final String filePath;
        String filePrefix = "VintageVideo";
        String fileExtn = ".mp4";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/" + "Folder");
            contentValues.put(MediaStore.Video.Media.TITLE, filePrefix+System.currentTimeMillis());
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, filePrefix+System.currentTimeMillis() +fileExtn);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
            Uri uri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);

            File file = FileUtils.getFileFromUri(this,uri);
            filePath = file.getAbsolutePath();
        }else
        {
            File dest = new File(new File(app_folder), filePrefix + fileExtn);
            int fileNo = 0;

            while (dest.exists())
            {
                fileNo++;
                dest = new File(new File(app_folder), filePrefix + fileNo + fileExtn);
            }
            filePath = dest.getAbsolutePath();
        }

        String cmd;

        cmd="-y -i " +video_url+" -vf curves=vintage -b:v 2097k -b:a 128k -ac 2 -ar 22050"+" -vcodec mpeg4 -crf 0 -preset superfast "+filePath;

        long executionId = FFmpeg.executeAsync(cmd, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                if (returnCode == RETURN_CODE_SUCCESS)
                {
                    videoView.setVideoURI(Uri.parse(filePath));
                    video_url = filePath;
                    videoView.start();
                    progressDialog.dismiss();
                }else if (returnCode == RETURN_CODE_CANCEL)
                {
                    Log.i(Config.TAG,"Async Command execution Cancel By User");
                }else
                {
                    Log.i(Config.TAG,String.format("Async Command execution Cancel by returnCode=%d",returnCode));
                }
            }
        });

    }

    private void BlackWhite(int startMs, int endMs) throws Exception {
        progressDialog.show();
        final String filePath;
        String filePrefix = "BlackWhite";
        String fileExtn = ".mp4";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/" + "Folder");
            contentValues.put(MediaStore.Video.Media.TITLE, filePrefix+System.currentTimeMillis());
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, filePrefix+System.currentTimeMillis() +fileExtn);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
            Uri uri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);

            File file = FileUtils.getFileFromUri(this,uri);
            filePath = file.getAbsolutePath();
        }else
        {
            File dest = new File(new File(app_folder), filePrefix + fileExtn);
            int fileNo = 0;

            while (dest.exists())
            {
                fileNo++;
                dest = new File(new File(app_folder), filePrefix + fileNo +fileExtn);
            }
            filePath = dest.getAbsolutePath();
        }

        String cmd;

        cmd="-y -i " +video_url+" -vf format=gray -b:v 2097k -b:a 128k -ac 2 -ar 22050"+" -vcodec mpeg4 -crf 0 -preset superfast "+filePath;

        long executionId = FFmpeg.executeAsync(cmd, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                if (returnCode == RETURN_CODE_SUCCESS)
                {
                    videoView.setVideoURI(Uri.parse(filePath));
                    video_url = filePath;
                    videoView.start();
                    progressDialog.dismiss();
                }else if (returnCode == RETURN_CODE_CANCEL)
                {
                    Log.i(Config.TAG,"Async Command Execution Cancel By User");
                }else
                {
                    Log.i(Config.TAG,String.format("Async Command Execution Cancel By returnCode=%d",returnCode));
                }
            }
        });
    }

    private void VFlipVideo(int startMs, int endMs) throws Exception {
        progressDialog.show();
        final String filePath;
        String filePrefix = "VFlipVideo";
        String fileExtn = ".mp4";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/" + "Folder");
            contentValues.put(MediaStore.Video.Media.TITLE, filePrefix+System.currentTimeMillis());
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, filePrefix+System.currentTimeMillis() +fileExtn);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
            Uri uri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,contentValues);

            File file = FileUtils.getFileFromUri(this,uri);

            filePath = file.getAbsolutePath();
        }else
        {
            File dest = new File(new File(app_folder), filePrefix + fileExtn);
            int fileNo = 0;

            while (dest.exists())
            {
                fileNo++;
                dest = new File(new File(app_folder), filePrefix + fileNo + fileExtn);
            }
            filePath = dest.getAbsolutePath();
        }
        String cmd;
        cmd="-y -i " +video_url+" -vf vflip -b:v 2097k -b:a 128k -ac 2 -ar 22050 "+" -vcodec mpeg4 -crf 0 -preset superfast "+filePath;

        long executionId = FFmpeg.executeAsync(cmd, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                if (returnCode == RETURN_CODE_SUCCESS)
                {
                    videoView.setVideoURI(Uri.parse(filePath));
                    video_url = filePath;
                    videoView.start();
                    progressDialog.dismiss();
                }else if (returnCode == RETURN_CODE_CANCEL)
                {
                    Log.i(Config.TAG,"Async Command Execution Cancel By User");
                }else
                {
                    Log.i(Config.TAG,String.format("Async Command Execution Cancel By returnCode=%d",returnCode));
                }
            }
        });
    }

    private void HFlipVideo(int startMs, int endMs) throws Exception {
        progressDialog.show();
        final String filePath;
        String filePrefix = "HFlipVideo";
        String fileExtn = ".mp4";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/" + "Folder");
            contentValues.put(MediaStore.Video.Media.TITLE, filePrefix+System.currentTimeMillis());
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, filePrefix+System.currentTimeMillis() +fileExtn);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
            Uri uri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,contentValues);

            File file = FileUtils.getFileFromUri(this,uri);

            filePath = file.getAbsolutePath();
        }else
        {
            File dest = new File(new File(app_folder), filePrefix + fileExtn);
            int fileNo = 0;

            while (dest.exists())
            {
                fileNo++;
                dest = new File(new File(app_folder),filePrefix + fileNo + fileExtn);
            }
            filePath = dest.getAbsolutePath();
        }
        String cmd;

        cmd="-y -i " +video_url+" -vf hflip -b:v 2097k -b:a 128k -ac 2 -ar 22050 "+" -vcodec mpeg4 -crf 0 -preset superfast "+filePath;

        long executionId = FFmpeg.executeAsync(cmd, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                if (returnCode == RETURN_CODE_SUCCESS)
                {
                    videoView.setVideoURI(Uri.parse(filePath));
                    video_url = filePath;
                    videoView.start();
                    progressDialog.dismiss();
                }else if (returnCode == RETURN_CODE_CANCEL)
                {
                    Log.i(Config.TAG,"Async Command Execution Cancel By User");

                }else
                {
                    Log.i(Config.TAG,String.format("Async Command Execution Cancel By returnCode=%d",returnCode));
                }
            }
        });

    }

    //Overriding the method onActivityResult() to get the video Uri form intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(MainActivity.this,"On_activity_Method_Called",Toast.LENGTH_SHORT).show();

        if (resultCode == RESULT_OK) {

            if (requestCode == 123) {

                if (data != null) {
                    //get the video Uri
                    Uri uri = data.getData();
                    try {
                        //get the file from the Uri using getFileFromUri() method present in FileUtils.java
                        File video_file = FileUtils.getFileFromUri(this, uri);
                        //now set the video uri in the VideoView
                        videoView.setVideoURI(uri);
                        //after successful retrieval of the video and properly setting up the retried video uri in
                        //VideoView, Start the VideoView to play that video
                        videoView.start();
                        //get the absolute path of the video file. We will require this as an input argument in
                        //the ffmpeg command.
                        video_url=video_file.getAbsolutePath();

                    } catch (Exception e) {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }


                }
            }
        }
    }

    //This method returns the seconds in hh:mm:ss time format

    private String getTime(int seconds) {

        int hr = seconds / 3600;
        int rem = seconds % 3600;
        int mn = rem / 60;
        int sec = rem % 60;
        return String.format("%02d", hr) + ":" + String.format("%02d", mn) + ":" + String.format("%02d", sec);
    }
}