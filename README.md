# BLives
## 目前处于开发期，成员招募中..

### 分页的逻辑
    * 首次加载　首次加载getPro,获取最初的数据
    * 加载更多  加载更多需要知道分页的情况，如当前页数，每页数据量，或者当前数据的最后一条的ｉｄ和每页数据量，getMore
    * 注意维护一个list，刷新的时候调用getPro，清空list数据，加载更多的时候，list.addAll添加数据，adapter.notifyDataSetChanged()即可。