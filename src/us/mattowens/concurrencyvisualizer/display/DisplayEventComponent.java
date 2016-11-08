package us.mattowens.concurrencyvisualizer.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class DisplayEventComponent extends JComponent implements MouseListener {
	private static Random random = new Random();
	private static final long serialVersionUID = 1L;
	private double  lineLength;
	private boolean lineLengthSet = false;
	private DisplayEvent event;
	
	public DisplayEventComponent(DisplayEvent event) {
		this.event = event;
		setOpaque(false);
		addNewLabel("Event Class", event.getEventClass());
		addNewLabel("Event Type", event.getEventType());
		addNewLabel("Target", event.getTargetDescription());
		addNewLabel("Timestamp", event.getTimestamp());
		addNewLabel("Thread Name", event.getThreadName());
		addNewLabel("Thread Id", event.getThreadId());
		addNewLabel("Join Point", event.getJoinPointName());
		
		for(Object pair : event.getEventMap().entrySet()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Object> mapEntry = (Map.Entry<String, Object>) pair;
			addNewLabel(mapEntry.getKey(), mapEntry.getValue());
		}

	}
	
	public void setLineLength(double lineLength) {
		this.lineLength = lineLength;
		lineLengthSet = true;
		setPreferredSize(new Dimension(200, (int)lineLength + 6));
	}
	
	private void addNewLabel(String key, Object value) {
		if(value == null) {
			System.out.println("Null pointer in addNewLabel : " + key);
		}
		else {			
			JLabel fieldLabel = new JLabel(key + ": " + value.toString());
			this.add(fieldLabel);
		}
	}
	
	private static Color getRandomColor() {
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}

	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//System.out.println("Width: " + getWidth() + " height: " + getHeight());
		Graphics2D g2D = (Graphics2D) g;
		
		//g2D.setColor(getRandomColor());
		
		if(lineLengthSet) {
			double midpoint = (double)getWidth()/2.0;
			Line2D.Double timeLine = new Line2D.Double(midpoint, 0, 
					midpoint, lineLength);
			g2D.draw(timeLine);
		}
		double startX = getWidth()/4;
		double width = getWidth()/2;
		
		g2D.draw(new Line2D.Double(startX, lineLength, startX+width, lineLength));
		g2D.draw(new Line2D.Double(startX+width, lineLength, startX+width+5, lineLength - 10));
		g2D.drawString(event.getEventType() + "@" + event.getTargetDescription(), (float)(startX+width-200), (float)(lineLength-10));
		//g2D.fill(rectangle);
		//g2D.draw(rectangle);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
