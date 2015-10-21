package border;

import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * Created by matpe on 21/10/2015.
 */
public class JRoundedCornerBorder extends AbstractBorder {

    private int thickness;

    public JRoundedCornerBorder(){
        super();
        this.thickness = 2;
    }

    public JRoundedCornerBorder(int thickness){
        super();
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(c.hasFocus()){
            g2.setColor(Color.BLUE);
        }
        else g2.setColor(Color.BLACK);

        g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawRoundRect(thickness, thickness, width - thickness - 2, height - thickness - 2,20,20);

        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness,thickness,thickness,thickness);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.bottom = 2;
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }


}
