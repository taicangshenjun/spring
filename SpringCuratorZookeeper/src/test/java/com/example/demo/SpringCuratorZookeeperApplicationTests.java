package com.example.demo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringCuratorZookeeperApplicationTests {

	@Test
	void contextLoads() throws Exception {
//		//1，创建zookeeper连接
//		CuratorFramework client1 = CuratorFrameworkFactory.builder()
//				.connectString("192.168.124.10:2181")//连接信息
//				.sessionTimeoutMs(15000)//会话超时时间
//				.retryPolicy(new ExponentialBackoffRetry(1000,5))//重试策略，间隔为一秒，重试不超过5次
//				.namespace("app1")//命名空间
//				.build();
//		//2，关闭zookeeper连接
////		CloseableUtils.closeQuietly(client1);
//		//3,查询节点下的子节点
//		List<String> strings = client1.getChildren().forPath("/");
//		//4,判断节点是否存在
//		client1.checkExists().forPath("/abc");
//		//5，创建节点添加数据
//		client1.create()
//				.creatingParentsIfNeeded()
//				.withMode(CreateMode.EPHEMERAL_SEQUENTIAL)//节点类型（EPHEMERAL_SEQUENTIAL：临时序列节点；PERSISTENT_SEQUENTIAL：持久序列节点）
//				.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)//节点权限
//				.forPath("/abc","sadad".getBytes());//节点名称和数据
//		//6，查询节点数据
//		String str = new String(client1.getData().storingStatIn(new Stat()).forPath("/abc"));
//		//7,修改节点数据
//		client1.setData().forPath("/abc","qwertyui".getBytes());
//		//8,删除节点
//		client1.delete()
//				.guaranteed()//保障机制，若未删除成功，会话有效期内一直尝试删除
//				.deletingChildrenIfNeeded()
//				.withVersion(-1)
//				.forPath("/abc");
//		//9,watch，给节点注册一次监听,可以监听到节点的增删改
//		client1.getChildren().usingWatcher(new CuratorWatcher() {
//			@Override
//			public void process(WatchedEvent watchedEvent) throws Exception {
//				System.out.println("节点变化。。。"+watchedEvent.getPath()+watchedEvent.getType());
//			}
//		}).forPath("/abc");
//		//10，NodeCache，一次性给节点注册多个监听，可以监听到当前节点的增删改
//		NodeCache nodeCache = new NodeCache(client1,"/abc");
//		nodeCache.getListenable().addListener(new NodeCacheListener() {
//			@Override
//			public void nodeChanged() throws Exception {
//				if (nodeCache.getCurrentData() == null){
//					System.out.println("节点被删除。。。"+nodeCache.getPath()+"数据为："+new String(nodeCache.getCurrentData().getData()));
//				}
//			}
//		});
//		//11,PathChildrenCache,监听父节点下的所有子节点（可监听多次）
//		PathChildrenCache pathChildrenCache = new PathChildrenCache(client1,"/",true);
//		pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
//			@Override
//			public void childEvent(CuratorFramework client2, PathChildrenCacheEvent event) throws Exception {
//				if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)){
//					//CHILD_ADDED:类型为增加；INITIALIZED:类型为初始化；CHILD_REMOVED:类型为删除；CHILD_UPDATED：类型为修改
//					System.out.println("节点增加了。。。路径为："+event.getData().getPath()+"数据为"+new String(event.getData().getData()));
//				}
//			}
//		});

		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
//									.authorization("digest", "root:920925".getBytes())	//密码
									.connectString("192.168.124.10:2181")
									.sessionTimeoutMs(150000) // 会话超时时间
									.connectionTimeoutMs(150000) // 连接超时时间
									.retryPolicy(retryPolicy)
//									.namespace("") // 包含隔离名称
									.build();
		client.start();
		//
		client.setData().forPath("/wnode","cm".getBytes());
		String path = client.create().withMode(CreateMode.PERSISTENT).forPath("/pnode");
		System.out.println(path);
		System.out.println("success");
	}

}
