package cz.uhk.fim.avatar;

public class Robot extends Avatar implements Runnable {
	private static final long serialVersionUID = 3086965283695076010L;

	int speed;
	
	static int counter = 0;
	public Robot() {
		super();
		
		name = "Robo" + (++counter);
		RGBA[3] = 0;
		size = (int) (Math.random() * 10);
		speed = 1+(int) (Math.random() * 3);
		id = 1 + counter;
		x =(int) (Math.random() *  World.getInstance().worldSizeX);
		y =(int) (Math.random() *  World.getInstance().worldSizeY);
	}
	
	public void move(Direction direction) {
		switch (direction) {
		case DOWN: if (getY() < World.getInstance().worldSizeY - 1) setY(getY() +1); break;
		case LEFT: if (getX() > 0) 	setX(getX() - 1);  break;
		case RIGHT: if (getX() < World.getInstance().worldSizeX - 1) setX(getX() + 1);  break;
		case UP: if (getY() > 0 )  setY(getY() - 1);  break;
		default:
			break;
		}
	}

	boolean running;
	
	@Override
	public void run() {
		
		running = true;
		
		while (running) {
			int dir = (int) (Math.random() * 4);
			switch (dir) {
			case 0: move(Direction.DOWN); break;
			case 1: move(Direction.UP); break;
			case 2: move(Direction.RIGHT); break;
			case 3: move(Direction.LEFT); break;
			default: break;
			}
			
			World.getInstance().refresh(this);
			
			try {
				Thread.sleep(500* speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName(name);
		t.start();
	}
	
	public void stop() {
		running = false;
	}

	@Override
	public void action() {
	}

	@Override
	public void onDraw(Object canvas) {
	}
	
}

