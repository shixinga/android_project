import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
	int maxCount = 3;
	AtomicInteger count =new AtomicInteger(0);// 当前开的线程数  count=0
	LinkedList<Runnable> runnables = new LinkedList<Runnable>();

	public void execute(Runnable runnable) {
		runnables.add(runnable);
		if(count.incrementAndGet()<=3){
			createThread();// 最大开三个线程
		}
	}
	private void createThread() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				while (true) {
					// 取出来一个异步任务
					if (runnables.size() > 0) {
						Runnable remove = runnables.remove(0); //在集合中移除第一个对象 返回值正好是移除的对象
						if (remove != null) {
							remove.run();
						}
					}else{
						//  等待状态   wake();
					}
				}
			}
		}.start();
	}
}
