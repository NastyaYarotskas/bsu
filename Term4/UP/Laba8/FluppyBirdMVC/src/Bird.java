import javax.sound.sampled.*;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class Bird extends BaseObject implements Observer{
    public boolean dead;

    public double yvel;
    public double gravity;

    private int jumpDelay;
    private double rotation;

    private Image image;

    public Bird() {
        super(100, 150, 45, 32);
        yvel = 0;
        gravity = 0.5;
        jumpDelay = 0;
        rotation = 0.0;
        dead = false;

    }

    public void update(Boolean f) {
        yvel += gravity;

        if (jumpDelay > 0)
            jumpDelay--;

        if (!dead && f && jumpDelay <= 0) {
            yvel = -10;
            jumpDelay = 10;
            File audioFile = new File("lib/wing.wav");
            AudioInputStream audioStream = null;
            try {
                audioStream = AudioSystem.getAudioInputStream(audioFile);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = null;
            try {
                audioClip = (Clip) AudioSystem.getLine(info);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                audioClip.open(audioStream);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            audioClip.start();
        }

        y += (int)yvel;
    }

    public Render getRender() {
        Render r = new Render();
        r.x = x;
        r.y = y;

        if (image == null) {
            image = Util.loadImage("lib/bird.png");     
        }
        r.image = image;

        rotation = (90 * (yvel + 20) / 20) - 90;
        rotation = rotation * Math.PI / 180;

        if (rotation > Math.PI / 2)
            rotation = Math.PI / 2;

        r.transform = new AffineTransform();
        r.transform.translate(x + width / 2, y + height / 2);
        r.transform.rotate(rotation);
        r.transform.translate(-width / 2, -height / 2);

        return r;
    }

    @Override
    public void update(Object object){
        update((Boolean) object);
    }
}
