package co.touchlab.android.superbusexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import co.touchlab.android.superbusexample.servicebus.ImageUploadCommand;
import co.touchlab.android.superbusexample.servicebus.UploadBusService;

import java.io.File;
import java.util.UUID;

public class MyActivity extends Activity
{

    public static final int CAMERA_REQ = 1235;
    private File picFile;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.takePic).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                takePicture();
            }
        });
    }

    private void takePicture()
    {
        Intent takePictureFromCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        picFile = new File(Environment.getExternalStorageDirectory(), "superbus_" + UUID.randomUUID().toString() + ".jpg");
        takePictureFromCameraIntent.putExtra(
                android.provider.MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(picFile));

        startActivityForResult(takePictureFromCameraIntent, CAMERA_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
        {
            Log.d(this.getClass().getSimpleName(), "onActivityResult...RESULT NOT OK FOR REQCODE:" + requestCode);
            return;
        }

        UploadBusService.startMe(this, new ImageUploadCommand(picFile.getAbsolutePath()));
    }
}
