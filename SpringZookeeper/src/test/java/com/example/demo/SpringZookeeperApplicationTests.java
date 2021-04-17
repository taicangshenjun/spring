package com.example.demo;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringZookeeperApplicationTests {
	
	@SuppressWarnings("resource")
	@Test
	void contextLoads() throws Exception {
		String ZK_ADDRESS = "192.168.124.10:2181";
		int SESSION_TIMEOUT = 150000;
		ZooKeeper zooKeeper;
		String ZK_NODE="/wnode";
		
		
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		zooKeeper = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				if(event.getState() == Watcher.Event.KeeperState.SyncConnected &&
						event.getType() == Watcher.Event.EventType.None) {
					countDownLatch.countDown();
					System.out.println("连接成功！");
				}
			}
			
		});
		
		System.out.println("开始连接！");
		countDownLatch.await();
	}

}
