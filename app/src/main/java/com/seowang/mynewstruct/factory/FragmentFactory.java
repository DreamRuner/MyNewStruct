package com.seowang.mynewstruct.factory;

import com.seowang.mynewstruct.base.BaseFragment;
import com.seowang.mynewstruct.fragment.home.HomeFragment;
import com.seowang.mynewstruct.fragment.more.MoreFragment;
import com.seowang.mynewstruct.fragment.product.ProductFragment;
import com.seowang.mynewstruct.fragment.user.UserFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个类是关于创建Fragment的工厂类，用来简化创建过程
 * 并使用Map集合对创建的Fragment进行缓存，提高效率
 * @author hm
 *
 */
public class FragmentFactory {
	
	
	//=============================================================
	/**
	 * 关于首页的RadioGroup位置的RadioButton的标示定义
	 */
	private static final int MAIN_RADIOBUT_HOME=0;
	private static final int MAIN_RADIOBUT_PRODUCT=1;
	private static final int MAIN_RADIOBUT_USER=2;
	private static final int MAIN_RADIOBUT_MORE=3;
	/**
	 * 定义一个Map用来完成Fragment的缓存操作，防止多次创建同一个Fragment产生内存的浪费
	 */
	private static Map<Integer, BaseFragment> mMainActivityFragmentMap = new HashMap<Integer, BaseFragment>();
	/**
	 * 这个方法表示：对主页位置的Fragment进行创建操作，当用户点击底部的RadioGroup的时候
	 * 根据用户的点击创建不同的Fragment对象
	 * @param checkedId
	 * @return
	 */
	public static BaseFragment createMainActiviryFragment(int checkedId){
		
		BaseFragment mMainActiviryFragment=mMainActivityFragmentMap.get(checkedId);
		if(null==mMainActiviryFragment){
			System.out.println("BaseFragment为空！！！！！！！！！！！");
			switch(checkedId){
			
			case MAIN_RADIOBUT_HOME:
				mMainActiviryFragment=new HomeFragment();
				break;
	        case MAIN_RADIOBUT_PRODUCT:
	        	mMainActiviryFragment=new ProductFragment();
				break;
	        case MAIN_RADIOBUT_USER:
	        	mMainActiviryFragment=new UserFragment();
		        break;
	        case MAIN_RADIOBUT_MORE:
	        	mMainActiviryFragment=new MoreFragment();
		        break;
			}
			mMainActivityFragmentMap.put(checkedId, mMainActiviryFragment);
		}
		return mMainActiviryFragment;
	}
	
	//=============================================================
	/**
	 * 用来定义新的界面的Fragment的使用
	 */

}
