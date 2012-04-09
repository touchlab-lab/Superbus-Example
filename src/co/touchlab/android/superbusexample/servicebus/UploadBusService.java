package co.touchlab.android.superbusexample.servicebus;

import android.content.Context;
import android.content.Intent;
import co.touchlab.android.superbus.Command;
import co.touchlab.android.superbus.localfile.LocalFileBusService;
import co.touchlab.android.superbus.localfile.LocalFileCommand;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: kgalligan
 * Date: 4/8/12
 * Time: 8:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadBusService extends LocalFileBusService
{
    @Override
    public LocalFileCommand createCommand(File file)
    {
        LocalFileCommand localFileCommand;
        try
        {
            localFileCommand = (LocalFileCommand) Class.forName(extractCommandClassName(file)).newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return localFileCommand;
    }

    public static void startMe(Context c, Command sc)
    {
        Intent intent = new Intent(c, UploadBusService.class);
        if (sc != null)
            intent.putExtra(SERVICE_COMMAND, sc);
        c.startService(intent);
    }
}
