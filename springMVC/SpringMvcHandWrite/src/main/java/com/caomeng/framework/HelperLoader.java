package com.caomeng.framework;

import com.caomeng.framework.helper.BeanHelper;
import com.caomeng.framework.helper.ClassHelper;
import com.caomeng.framework.helper.ConfigHelper;
import com.caomeng.framework.helper.ControllerHelper;
import com.caomeng.framework.helper.IOCHelper;
import com.caomeng.framework.helper.RequestHelper;
import com.caomeng.framework.util.ClassUtil;

/**
 * 入口程序来加载他们(实际上是加载静态代码块), 当然就算没有这个入口程序, 
 * 这些类也会被加载, 我们这里只是为了让加载更加集中.
 * @author cm
 *
 */
public final class HelperLoader {
	
	public static void init() {
		Class<?>[] classList = {
			BeanHelper.class,
			ClassHelper.class,
			ConfigHelper.class,
			ControllerHelper.class,
			RequestHelper.class,
			IOCHelper.class
		};
		for(Class<?> cls: classList) {
			ClassUtil.loadClass(cls.getName());
		}
	}

}
