package gus.game5.core.mouse;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class Mouse {
	
	public class Button {
		
		/*
		 * PRESSED
		 */
		
		private boolean pressed = false;
		
		public boolean isPressed() {
			return pressed;
		}
		
		/*
		 * JUST
		 */
		
		private Point0 justPressedPoint;
		private Point0 justReleasedPoint;
		private Point0 justClickedPoint;
		private int justClickedCount = 0;
		
		public Point0 justPressedPoint() {
			return justPressedPoint;
		}
		public Point0 justReleasedPoint() {
			return justReleasedPoint;
		}
		public Point0 justClickedPoint() {
			return justClickedPoint;
		}
		public int justClickedCount() {
			return justClickedCount;
		}
		
		public boolean justPressed() {
			return justPressedPoint!=null;
		}
		public boolean justReleased() {
			return justReleasedPoint!=null;
		}
		public boolean justClicked() {
			return justClickedPoint!=null;
		}
		
		public void clear() {
			justPressedPoint = null;
			justReleasedPoint = null;
			justClickedPoint = null;
			justClickedCount = 0;
		}
		
		public void press(Point0 p) {
			justPressedPoint = p;
			pressed = true;
		}
		public void release(Point0 p) {
			justReleasedPoint = p;
			pressed = false;
		}
		public void click(Point0 p, int count) {
			justClickedPoint = p;
			justClickedCount = count;
		}
	}
	
	
	
	
	private Button button1 = new Button();
	private Button button2 = new Button();
	private Button button3 = new Button();
	
	private Point0 pointEntered = null;
	private Point0 pointExited = null;
	private Point0 pointCurrent = null;
	
	private double wheelRotation = 0;
	
	

	
	private Point1 p(Point0 p) {
		return p!=null ? p.p1() : null;
	}

	private Point1 evtToPoint(MouseEvent e) {
		return new Point1(e.getX(), e.getY());
	}
	
	private Button evtToButton(MouseEvent e) {
		switch(e.getButton()) {
			case MouseEvent.BUTTON1:return button1;
			case MouseEvent.BUTTON2:return button2;
			case MouseEvent.BUTTON3:return button3;
			default:return null;
		}
	}
	
	
	
	public Mouse(Component comp) {
		super();
		
		comp.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				pointCurrent = evtToPoint(e);
				int count = e.getClickCount();
				evtToButton(e).click(pointCurrent, count);
			}
			public void mousePressed(MouseEvent e) {
				pointCurrent = evtToPoint(e);
				evtToButton(e).press(pointCurrent);
			}
			public void mouseReleased(MouseEvent e) {
				pointCurrent = evtToPoint(e);
				evtToButton(e).release(pointCurrent);
			}
			public void mouseEntered(MouseEvent e) {
				pointEntered = evtToPoint(e);
				pointCurrent = pointEntered;
			}
			public void mouseExited(MouseEvent e) {
				pointExited = evtToPoint(e);
				pointCurrent = null;
			}
		});
		
		comp.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				pointCurrent = evtToPoint(e);
			}
			public void mouseMoved(MouseEvent e) {
				pointCurrent = evtToPoint(e);
			}
		});
		
		comp.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				wheelRotation = e.getPreciseWheelRotation();
			}
		});
	}
	
	
	
	
	public Point0 pointCurrent() {return p(pointCurrent);}
	public Point0 pointEntered() {return p(pointEntered);}
	public Point0 pointExited() {return p(pointExited);}

	public Point0 point() {
		if(pointCurrent!=null) return p(pointCurrent);
		if(pointExited!=null) return p(pointExited);
		return new Point1(0,0);
	}
	
	public Button button1() {return button1;}
	public Button button2() {return button2;}
	public Button button3() {return button3;}
	
	public double wheelRotation() {return wheelRotation;}
	
	public void reset() {
		button1.clear();
		button2.clear();
		button3.clear();
		wheelRotation = 0;
	}
}
