/*
 *@author Paradox
 * Audio Capture using Java Media Framework
 */
package audiocapture.store;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.media.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.format.AudioFormat;
import javax.media.CaptureDeviceInfo;
import java.util.Vector;
import javax.media.Manager;
import javax.media.Processor;
import javax.media.protocol.FileTypeDescriptor;
import javax.media.protocol.ContentDescriptor;
import javax.media.MediaLocator;
import javax.media.DataSink;
import javax.media.ProcessorModel;
import javax.media.Format;
import javax.media.protocol.DataSource;

public class AudioCaptureStore {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Vector devices = CaptureDeviceManager.getDeviceList ( new AudioFormat(AudioFormat.LINEAR) );
        CaptureDeviceInfo di = null;
        
        if (devices.size() > 0){
            System.out.println(devices.size());
            di = (CaptureDeviceInfo)devices.firstElement();
        }
        else
        System.exit(-1); 
        
        /*******************************************************************************************/
       
        MediaLocator file = null;
        URL url = null;
        
        try {
            url = new URL("file", null, "output.wav");
        } catch (MalformedURLException ex) {
            Logger.getLogger(AudioCaptureStore.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("(output.wav)--ERROR!");
        }
        try{
            file = new MediaLocator(url);
        } catch(Exception ex){
            System.out.println("(Medilocator)--ERROR!");
        }
        
        /*******************************************************************************************/
        DataSource audioInputSource = null;
        Vector<CaptureDeviceInfo> cdi = new Vector<>();
        cdi = CaptureDeviceManager.getDeviceList(new AudioFormat(AudioFormat.LINEAR));
        
        MediaLocator mic = cdi.elementAt(0).getLocator();
        
        try {
            audioInputSource = Manager.createDataSource(mic);
        }catch(Exception e)
        {
            System.out.println("hello1");
        }
        /*******************************************************************************************/
    DataSink ds = null;
    Processor pp = null;
    Format[] formats = new Format[1];
    formats[0] = new AudioFormat(AudioFormat.LINEAR);
    
        try {
            pp = Manager.createRealizedProcessor(new ProcessorModel(audioInputSource, formats, new ContentDescriptor(FileTypeDescriptor.WAVE)));
            ds = Manager.createDataSink(pp.getDataOutput(), file);
            System.out.println("hello");
            pp.start();
            ds.open();
            ds.start();
        } catch (IOException | NoProcessorException | CannotRealizeException | NoDataSinkException | SecurityException e) {
            Logger.getLogger(AudioCaptureStore.class.getName()).log(Level.SEVERE, null, e);
        }
        /*******************************************************************************************/
       
}}
