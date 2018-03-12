## 智慧北京  第4天 ##
1、自定义下拉刷新布局

![自定义下拉刷新布局](https://i.imgur.com/fDhfIEa.png)

2、重写ListView，初始的时候调用自定义的头布局

3、重写onTouchEvent事件

4、在布局向上的时候，状态改为正在刷新，同时进行回调刷新。

4、yyyy-MM-dd HH:mm:ss  ：MM表示月份从1-12月开始，mm表示月份从0-11，HH表示24小时制
   hh表示是12小时制

## 智慧北京  第5天 ##
1、定义‘加载更多’的布局

![](https://i.imgur.com/zSIonSM.png)

2、在初始化的时候调用自定义的foot布局

3、ListView实现滑动监听，滑动状态中到最后一个item时自定调用‘加载更多’

4、将‘加载更多’的数据追加原来的listview中，然后用listview的适配器调用notifyDataSetChanged（刷新）此方法

5、在线性布局中，设置的控件占的比例：比如只有两个控件，你要设置其中一个控件占的layout_weight=1，如果父布局LinearLayout的orientation的为vertical（垂直），那么这个控件的layout_height="0dp",layout_width="marth_parent"

![](https://i.imgur.com/iZwIOW2.png)

6、标记已读：给ListView设置一个setOnItemClickListener事件，再拿到当前item的TextView设置颜色为灰色。

7、查看新闻详情：点击跳转到另一个Activity，在webview中显示网页内容。

	*对话框参数：第一个参数是对话框内容，第二个参数是设置默认选择第几个，第三个是监听事件。
![](https://i.imgur.com/8E49M8o.png)
