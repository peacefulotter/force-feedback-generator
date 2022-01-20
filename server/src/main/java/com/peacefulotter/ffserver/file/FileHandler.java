package com.peacefulotter.ffserver.file;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Queue;

public class FileHandler
{
    public static void writeToFile( Queue<Float> angles, String fileName )
    {
        System.out.println(angles);
        int size = angles.size();
        System.out.println("[FileHandler] (writeToFile) size: " + size);

        try ( DataOutputStream dos = new DataOutputStream( new FileOutputStream( "res/" + fileName ) )  )
        {
            dos.write( size );

            for ( int i = 0; i < size; i++ )
            {
                Float angle = angles.poll();
                dos.writeFloat( angle );
            }
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
