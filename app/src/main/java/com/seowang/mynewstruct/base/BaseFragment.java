package com.seowang.mynewstruct.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 这里是关于Fragment的基础封装类
 * @author hm
 *
 */
public abstract class BaseFragment extends Fragment{
	
	//定义一个View用来保存Fragment创建的时候使用打气筒工具进行的布局获取对象的存储
	protected View view;
	/**
	 * 当Fragment进行创建的时候执行的方法
	 * 在这里发送网络请求
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initFragmentRequestNet();
		System.out.println("执行了：BaseFragment---onCreate");
	}
	
	/**
	 * 这个方法是关于Fragment完成创建的过程中，进行界面填充的方法,该方法返回的是一个view对象
	 * 在这个对象中封装的就是Fragment对应的布局
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view=initFragmentView(inflater);
		System.out.println("执行了：BaseFragment---onCreateView");
		return view;
	}
	/**
	 * 这个方法当onCreateView方法中的view创建完成之后，执行
	 * 在inflate完成view的创建之后，可以将对应view中的各个控件进行查找findViewById
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		System.out.println("执行了：BaseFragment---onViewCreated");
		initFragmentChildView(view);
	}

	/**
	 * 这个方法是在Fragment完成创建操作之后，进行数据填充操作的时候执行的方法
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		System.out.println("执行了：BaseFragment---onActivityCreated");
		super.onActivityCreated(savedInstanceState);
		initFragmentData(savedInstanceState);
	}
	
	

	//下面是关于抽象方法的定义，需要子类必须进行实现
	protected abstract void initFragmentRequestNet();
	protected abstract View initFragmentView(LayoutInflater inflater);
	protected abstract void initFragmentChildView(View view);
	protected abstract void initFragmentData(Bundle savedInstanceState);
	
	
	
	

}
