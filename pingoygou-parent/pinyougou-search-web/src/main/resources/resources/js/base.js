var app=angular.module('pinyougou',[]);

// 定义一个信任组件，表示解析js或者html代码
// 前台搜索高亮显示使用到
app.filter("trustHtml",['$sce',function ($sce) {
/*    return function (date) {
        $sce.trustAsHtml(date);
    }*/
    return function(data){
        return $sce.trustAsHtml(data);
    }
} ]);