import fi.iki.elonen.util.ServerRunner;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.Status;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;


public class GlobalKeyboardExample {



    private static boolean run = true;
    public static void main(String[] args) {

        try {
            new MiniServer();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }

        // might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();

        System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown.");
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override public void keyPressed(GlobalKeyEvent event) {
                System.out.println(event);
                if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_OEM_102) {
                    System.out.println("Hallo");
                    BufferedImage image = null;
                    try {
                        image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                        //ImageIO.write(image, "png", new File("C:\\screen\\screenshot2.png"));
                        ImageIO.write(image, "png", new File("Z:\\html\\screenshot2.png"));
                        ChromeCast cc = new ChromeCast("192.168.0.102");
                        //ChromeCast cc = new ChromeCast("192.168.0.214");
                        cc.connect();
                        Status status = cc.getStatus();
                        cc.stopApp();
                        //if (cc.isAppAvailable("CC1AD845") && !status.isAppRunning("CC1AD845")) {
                        Application app = cc.launchApp("CC1AD845");
                        //cc.load("http://10.211.55.5:8080/screen/screenshot2.png");
                        cc.load("http://192.168.0.106:8080/html/screenshot2.png");

                    } catch (AWTException e) {
                        e.printStackTrace();
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }



            }
            @Override public void keyReleased(GlobalKeyEvent event) {
                System.out.println(event); }
        });

        try {
            while(run) Thread.sleep(128);
        } catch(InterruptedException e) { /* nothing to do here */ }
        finally { keyboardHook.shutdownHook(); }
    }
}