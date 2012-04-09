package co.touchlab.android.superbusexample.servicebus;

import android.content.Context;
import android.util.Log;
import co.touchlab.android.superbus.Command;
import co.touchlab.android.superbus.PermanentException;
import co.touchlab.android.superbus.TransientException;
import co.touchlab.android.superbus.localfile.JsonFileCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: kgalligan
 * Date: 4/8/12
 * Time: 8:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageUploadCommand extends JsonFileCommand implements Serializable
{
    public static final String IMAGE_PATH = "imagePath";
    private String imageFile;

    public ImageUploadCommand(String imageFile)
    {
        this.imageFile = imageFile;
    }

    public ImageUploadCommand()
    {
    }

    public String getImageFile()
    {
        return imageFile;
    }

    @Override
    public void writeToStorage(JSONObject jsonObject) throws JSONException
    {
        jsonObject.put(IMAGE_PATH, imageFile);
    }

    @Override
    public void readFromStorage(JSONObject jsonObject)throws JSONException
    {
        imageFile = jsonObject.getString(IMAGE_PATH);
    }

    @Override
    public String logSummary()
    {
        return imageFile;
    }

    @Override
    public boolean same(Command command)
    {
        if(command instanceof ImageUploadCommand)
        {
            ImageUploadCommand that = (ImageUploadCommand) command;
            return that.getImageFile().equals(imageFile);
        }
        return false;
    }

    @Override
    public void callCommand(Context context) throws TransientException, PermanentException
    {
        if(new Random().nextInt(10) < 6)
            throw new TransientException("Just checking...");

        Log.i(getClass().getSimpleName(), "Hey, pretend to upload: "+ imageFile);
        new File(imageFile).delete();
    }
}
