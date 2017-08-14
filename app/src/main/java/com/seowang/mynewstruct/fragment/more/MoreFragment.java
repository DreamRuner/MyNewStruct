package com.seowang.mynewstruct.fragment.more;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.seowang.mynewstruct.R;
import com.seowang.mynewstruct.base.BaseFragment;
import com.seowang.mynewstruct.domain.LessonList;
import com.seowang.mynewstruct.net.async.AsyncHandler;
import com.seowang.mynewstruct.net.async.AsyncUrlConnection;
import com.seowang.mynewstruct.net.base.BaseParser;
import com.seowang.mynewstruct.net.domain.RequestType;
import com.seowang.mynewstruct.net.domain.RequestVo;
import com.seowang.mynewstruct.parser.LessonParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 这里是关于更多模块的Fragment
 * @author hm
 *
 */
public class MoreFragment extends BaseFragment {

	private ListView lv_list;
	private LessonList lessonList=null;
	//上下文
	private Context mContext=getActivity();

	private ArrayList<LessonList.LessonDetail> arrayList=null;
	private MyAdapter myAdapter=null;

	/**
	 * 完成网络请求操作，将请求后的数据生成对应的JavaBean
	 * 使用自定义的网络框架完成网络请求
	 */
	@Override
	protected void initFragmentRequestNet() {

	}
	/**
	 * 完成Layout布局的inflate填充操作
	 */
	@Override
	public View initFragmentView(LayoutInflater inflater) {
		System.out.println("BaseFragment========MoreFragment-----initFragmentView");
//		int i=1/0;
		view=inflater.inflate(R.layout.fragment_more, null);
		return view;
	}
	/**
	 * 对inflate之后的view中的子控件记性查找
	 */
	@Override
	protected void initFragmentChildView(View view) {
		lv_list= (ListView) view.findViewById(R.id.lv_list);
	}
	/**
	 * 将网络的数据展示到当前的界面中
	 */
	@Override
	public void initFragmentData(Bundle savedInstanceState) {
		//接口请求的类型
		int requestType= RequestType.HTTP_GET;
		//接口的基础链接
		//这个BaseUrl 在一个Config文件中进行统一管理
//        String baseUrl="http://api.lovek12.com/v190/index.php?r=resource/get-resource-by-gradex";
		String baseUrl="http://api.lovek12.com/v190/index.php?r=resource/get-resource-by-gradex";
//        http://api.lovek12.cn/v190/index.php?r=resource/get-resource-by-gradex&device_type=ad1&grade_id=0
//       &course_type=0&page=1&size=8
		//字符串形式的参数
		HashMap<String,String> paramStringHashMap=new HashMap<>();
		paramStringHashMap.put("device_type", "ad1");
		paramStringHashMap.put("grade_id","0");
		paramStringHashMap.put("course_type","0");
		paramStringHashMap.put("page","1");
		paramStringHashMap.put("size","20");
		//对应的上传文件的File对象的参数
		HashMap<String,File> paramFileHashMap=new HashMap<>();
		paramFileHashMap.put("aaa",new File("ddfdfgdfsfdasfds"));


		//对应JavaBean的解析Parser
		BaseParser<LessonList> jsonParser=new LessonParser();
		//上下文
//        Context mContext=SplashActivity.this;
		//网络环境状态的提示。仅限于Wifi与流量之间的提示
		boolean isShowWifi=true;
		//数据是否进行缓存的设置
		boolean isSaveDataWithNet=false;
		//数据缓存的时间长度，毫秒级  1minute==60000ms，
		// 如果上面选择为true，但是没有给这个时间，框架中默认进行5minute的缓存
		long dataSaveTimeNoNet=200000000L;
		//RequestVo类的对象创建
		RequestVo vo=new RequestVo(requestType,baseUrl,paramStringHashMap,paramFileHashMap,jsonParser,
				getActivity(),isShowWifi,isSaveDataWithNet,dataSaveTimeNoNet);
		AsyncUrlConnection.getObject(vo, new AsyncHandler<LessonList>() {
			@Override
			public void onSuccess(LessonList result) {


				if (null != result) {
					arrayList = result.getData();
					setData();
				} else {
					//异常的提示
				}

				System.out.println("=============================");
				System.out.println("课程首页的JavaBean" + result.toString());
				System.out.println("=============================");


			}

			@Override
			public void onFaile(String str) {

			}
		});
	}

	private void setData() {

		myAdapter=new MyAdapter();
		lv_list.setAdapter(myAdapter);

	}
	class MyViewHolder{
		ImageView iv_image;
		TextView tv_titile;
	}
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return arrayList.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view;
			final MyViewHolder holder;//存储ListView中item的各个控件
			if(convertView==null){
				view=View.inflate(getActivity(), R.layout.item, null);
				holder=new MyViewHolder();
				holder.iv_image= (ImageView) view.findViewById(R.id.iv_image);
				holder.tv_titile= (TextView) view.findViewById(R.id.tv_titile);
				view.setTag(holder);
			}else{
				view=convertView;
				holder=(MyViewHolder) view.getTag();
			}
			LessonList.LessonDetail lessonDetail=arrayList.get(position);
			AsyncUrlConnection.getBitmap(100000000L, "http://test.lovek12.com" + lessonDetail.getImg_url(), new AsyncHandler<Bitmap>() {
				@Override
				public void onSuccess(Bitmap result) {
					if (null != result) {

						System.out.println("ListActivity图片为NULL");

						holder.iv_image.setImageBitmap(result);
					} else {
						System.out.println("ListActivity图片为默认图片");
						holder.iv_image.setImageResource(R.mipmap.ic_launcher);
//                        holder.iv_image.setImageBitmap(R.mipmap.ic_launcher);
					}
				}

				@Override
				public void onFaile(String str) {

				}
			});
			if(null!=lessonDetail.getTitle()){
				holder.tv_titile.setText(lessonDetail.getTitle());
			}else{
				holder.tv_titile.setText("哈哈");
			}

			return view;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}
}
