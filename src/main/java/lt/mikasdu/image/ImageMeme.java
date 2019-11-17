package lt.mikasdu.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;

public class ImageMeme {

    private BufferedImage image;

    private String filename;

    public ImageMeme(InputStream inputStream, String filename, String text) {
        setFilename(filename);
        try {
            this.image = ImageIO.read(inputStream);
            draw(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFilename(String filename) {
        this.filename = filename.substring(0, filename.lastIndexOf('.'));
    }

    private void draw(String text) {
        int w = image.getWidth();
        int h = image.getHeight();
        int border = 5;
        BufferedImage framedImage = new BufferedImage(w + 2*border, h + 100, image.getType());
        Graphics2D graph = framedImage.createGraphics();
        graph.setColor(Color.BLACK);
        graph.drawRect(0, 0, w + 2*border, h + 2*border);
        graph.fill(new Rectangle(0, 0, w + 2*border, h + 100));
        graph.drawImage(image, border, border, null);
        graph.dispose();
        image = framedImage;
        Graphics g = image.getGraphics();
        Font font = g.getFont().deriveFont(30f);
        FontMetrics metrics = g.getFontMetrics(font);
        g.setFont(font);
        int x =  (image.getWidth() - metrics.stringWidth(text)) / 2;
        int y =  image.getHeight()-40;
        g.drawString(text, x, y);
        g.dispose();
    }


    public void saveTo(Path rootLocation) throws IOException {
        ImageIO.write(image, "png", new File(rootLocation + "/" + filename + ".png"));
    }
}
