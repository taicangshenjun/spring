package com.caomeng.framework;

import com.caomeng.framework.helper.BeanHelper;
import com.caomeng.framework.helper.ClassHelper;
import com.caomeng.framework.helper.ConfigHelper;
import com.caomeng.framework.helper.ControllerHelper;
import com.caomeng.framework.helper.IOCHelper;
import com.caomeng.framework.helper.RequestHelper;
import com.caomeng.framework.util.ClassUtil;

/**
 * ��ڳ�������������(ʵ�����Ǽ��ؾ�̬�����), ��Ȼ����û�������ڳ���, 
 * ��Щ��Ҳ�ᱻ����, ��������ֻ��Ϊ���ü��ظ��Ӽ���.
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
