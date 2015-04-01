/*
 *@author Paradox
 * Audio Capture using Java Media Framework
 */
package audiocapture.store;

import java.net.MalformedURLException;
import java.net.URL;
import javax.media.CaptureDeviceInfo;
import javax.media.*;
import java.util.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.FileTypeDescriptor;

public class AudioCaptureStore {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Vector devices = CaptureDeviceManager.getDeviceList ( new AudioFormat(AudioFormat.LINEAR) );
        CaptureDeviceInfo di = null;
        
        System.out.println(devices.size());
        
        if (devices.size() > 0)
         di = (CaptureDeviceInfo)devices.firstElement();
        else
        System.exit(-1); 
        
    MediaLocator file = null;
    URL url = null;
        try {
            url = new URL("file", null, "clap.wav");
        } catch (MalformedURLException ex) {
            Logger.getLogger(AudioCaptureStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            file = new MediaLocator(url);
        } catch(Exception ex){
        }
    
    DataSink ds = null;
    Processor pp = null;
    Format[] formats = new Format[1];
    formats[0] = new AudioFormat(AudioFormat.LINEAR);
    
        try {
            pp = Manager.createRealizedProcessor(new ProcessorModel(Manager.createDataSource(url), formats, new ContentDescriptor(FileTypeDescriptor.WAVE)));
            ds = Manager.createDataSink(pp.getDataOutput(), file);
            System.out.println("hello");
            pp.start();
            ds.open();
            ds.start();
        } catch (Exception e) {
            Logger.getLogger(AudioCaptureStore.class.getName()).log(Level.SEVERE, null, e);
        }

}}
