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